package org.vr.app.activities.multi;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.view.ViewGroup;

import org.jetbrains.annotations.Nullable;
import org.vr.app.activities.multi.lifecycles.CollapsingRecyclerScreenComponent;
import org.vr.app.activities.multi.lifecycles.HomeScreenComponent;
import org.vr.app.activities.multi.lifecycles.PreconditionsComponent;
import org.vr.app.activities.multi.lifecycles.SplashScreenComponent;
import org.vr.app.activities.multi.router.activity.MultiActivityLifeCyclesFactory;
import org.vr.app.annotations.DrawerEnabled;
import org.vr.app.common.drawer.DefaultDrawerOwner;
import org.vr.app.common.drawer.DrawerContract;
import org.vr.app.common.drawer.DrawerOwner;
import org.vr.app.common.drawer.DrawerPresenter;
import org.vr.app.common.lifecycles.collapse_recycler.CollapseRecyclerScreenMeta;
import org.vr.app.common.lifecycles.home.HomeScreenDI;
import org.vr.app.common.lifecycles.preconditions.PreconditionsDI;
import org.vr.app.common.lifecycles.splash.SplashScreenDI;
import org.vr.app.common.routers.view.AppViewRouter;
import org.vr.app.common.routers.view.AppViewRouterMeta;
import org.vr.app.common.routers.view.AppViewRouterTransitionFactory;
import org.vr.app.common.toolbar.AppBarPresenter;
import org.vr.app.common.ui.ToolbarModule;
import org.vr.cycle.LifeCycle;
import org.vr.framework.router.view.ViewRouterListener;
import org.vr.framework.router.view.transition.ViewRouterTransitionListener;
import org.vr.router.router.base.Router;

import javax.inject.Scope;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;

/**
 * Created by vladimirrybkin on 17/01/2017.
 */
public class MultiActivityLifeCycleMeta {

    @Scope
    @interface MultiActivityLifeCyclerScope {
    }

    @MultiActivityLifeCyclerScope
    @Subcomponent(modules = {AppViewRouterMeta.AppViewRouterModule.class,
            MultiActivityLifeCycleMetaModule.class})
    public interface MultiActivityLifeCycleComponent {

        String name = "MultiActivityLifeCycleComponent";

        void inject(MultiActivityLifeCycle lifeCycle);

        PreconditionsComponent createPreconditionsComponent(
                PreconditionsDI.PreconditionsModule preconditionsModule);

        SplashScreenComponent createSplashScreenComponent(
                SplashScreenDI.SplashScreenModule splashScreenModule);

        HomeScreenComponent createHomeScreenComponent(HomeScreenDI.HomeScreenModule screenModule,
                                                      ToolbarModule toolbarModule);

        CollapsingRecyclerScreenComponent createCollapsingRecyclerScreenComponent(CollapseRecyclerScreenMeta.CollapseRecyclerScreenModule dep);

    }

    @Module
    public static class MultiActivityLifeCycleMetaModule {

        private final Context context;
        private final ViewGroup screenContainer;
        private final DrawerLayout drawerLayout;

        public MultiActivityLifeCycleMetaModule(@NonNull Context context,
                                                @NonNull ViewGroup screenContainer,
                                                @NonNull DrawerLayout drawerLayout) {
            this.context = context;
            this.screenContainer = screenContainer;
            this.drawerLayout = drawerLayout;
        }

        @MultiActivityLifeCyclerScope
        @Provides
        public DrawerContract.Presenter provideDrawerPresenter(DrawerPresenter drawerPresenter) {
            return drawerPresenter;
        }

        @MultiActivityLifeCyclerScope
        @Provides
        public DrawerOwner provideDrawerOwner(Activity activity, DrawerContract.Presenter presenter) {
            return new DefaultDrawerOwner(activity, drawerLayout, presenter);
        }

        @MultiActivityLifeCyclerScope
        @Provides
        public ViewRouterListener provideViewRouterListener(
                AppBarPresenter appBarOwner,
                DrawerOwner drawerOwner,
                DrawerContract.Presenter presenter) {
            return transition -> transition.registerListener(new ViewRouterTransitionListener() {
                @Override
                public void onTransitionStart(@NonNull Uri enterKey,
                        @NonNull LifeCycle enterLifeCycle,
                        @Nullable Uri exitKey,
                        @Nullable LifeCycle exitLifeCycle) {
                    appBarOwner.setView(null);
                    drawerOwner.lock();
                    drawerOwner.boundToToolbar(false);
                    presenter.highlightItem(enterKey);
                }

                @Override
                public void onTransitionEnd(@NonNull Uri enterKey,
                                            @NonNull LifeCycle enterLifeCycle,
                                            @Nullable Uri exitKey,
                                            @Nullable LifeCycle exitLifeCycle) {
                    DrawerEnabled drawerEnabledAnnotation = enterLifeCycle.getClass()
                            .getAnnotation(DrawerEnabled.class);
                    boolean drawerEnabled = drawerEnabledAnnotation != null &&
                            drawerEnabledAnnotation.required();
                    if (drawerEnabled) {
                        drawerOwner.unlock();
                        drawerOwner.boundToToolbar(drawerEnabledAnnotation.boundToAppBar());
                    }
                }
            });
        }

        @MultiActivityLifeCyclerScope
        @Provides
        public Router provideAppViewRouter(MultiActivityLifeCyclesFactory lifeCycleFactory,
                                           AppViewRouterTransitionFactory transitionFactory,
                                           ViewRouterListener routerListener) {
            AppViewRouter router = new AppViewRouter(context, screenContainer, lifeCycleFactory);
            router.setTransitionFactory(transitionFactory);
            router.setListener(routerListener);
            return router;
        }

    }

}
