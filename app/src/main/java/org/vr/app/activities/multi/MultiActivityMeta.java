package org.vr.app.activities.multi;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.vr.app.activities.multi.router.activity.MultiActivityLifeCyclesFactory;
import org.vr.app.common.lifecycles.LifeCyclesFactory;
import org.vr.app.common.lifecycles.home.HomeScreenDI;
import org.vr.app.common.lifecycles.preconditions.PreconditionsDI;
import org.vr.app.common.lifecycles.splash.SplashScreenDI;
import org.vr.app.activities.multi.router.activity.AppActivityRouterMeta;
import org.vr.app.common.routers.view.AppViewRouterMeta;
import org.vr.app.common.toolbar.AppBarPresenter;
import org.vr.app.common.toolbar.DefaultAppBarPresenter;
import org.vr.di.ActivityScope;
import org.vr.di.DiAppCompatActivityModule;
import org.vr.router.route.base.Route;
import org.vr.router.route.transition.RouteTransition;
import org.vr.router.router.uri.activity.ActivityRouter;
import org.vr.router.router.uri.activity.DefaultActivityRouterLaunchDataFactory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;

/**
 * Created by vladimirrybkin on 02/02/2017.
 */

public class MultiActivityMeta {

    @ActivityScope
    @Subcomponent(modules = {DiAppCompatActivityModule.class,
            AppActivityRouterMeta.AppActivityRouterModule.class,
            MultiActivityModule.class})
    public interface MultiActivityComponent {
        MultiActivityLifeCycleMeta.MultiActivityLifeCycleComponent createMultiActivityLifeCycleComponent(
                MultiActivityLifeCycleMeta.MultiActivityLifeCycleMetaModule lifeCycleMetaModule,
                AppViewRouterMeta.AppViewRouterModule routesModule);

        ActivityRouter provideActivityRouter();

    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface InitialRoute {
    }

    @Module
    public static class MultiActivityModule {

        @Nullable
        private final Bundle savedInstanceState;

        public MultiActivityModule(@Nullable Bundle savedInstanceState) {
            this.savedInstanceState = savedInstanceState;
        }

        @ActivityScope
        @InitialRoute
        @Provides
        public Route provideInitialRoute(Activity activity,
                                         @PreconditionsDI.PreconditionsQualifier Uri preconditionKey,
                                         @HomeScreenDI.HomeScreenQualifier Uri homeScreenKey,
                                         @SplashScreenDI.SplashScreenQualifier Uri splashScreenKey) {
            Intent intent = activity.getIntent();
            Bundle savedState = savedInstanceState;
            Bundle inTransition;
            Bundle outTransition;

            Bundle persistantState = intent.getBundleExtra(
                    DefaultActivityRouterLaunchDataFactory.Companion.getPERSISTANT_STATE());

            if (persistantState == null) {
                persistantState = intent.getExtras();
            }

            if (savedState == null) {
                savedState = intent.getBundleExtra(
                        DefaultActivityRouterLaunchDataFactory.Companion.getSAVED_STATE());
            }

            inTransition = intent.getBundleExtra(DefaultActivityRouterLaunchDataFactory.
                    Companion.getIN_TRANSITION());

            outTransition = intent.getBundleExtra(DefaultActivityRouterLaunchDataFactory.
                    Companion.getOUT_TRANSITION());

            Uri key = activity.getIntent().getData();
            if (key == null) {
                key = homeScreenKey;
            }

            Route targetRoute = AppActivityRouterMeta.RouteBuilder.createUriRoute(key)
                    .action(Route.Companion.getREPLACE_TOP())
                    .persistantState(persistantState)
                    .savedState(savedState)
                    .inTransition(inTransition != null ? new RouteTransition(inTransition) : null)
                    .outTransition(outTransition != null ? new RouteTransition(outTransition) : null);

            Route initRoute = AppActivityRouterMeta.RouteBuilder.createUriRoute(splashScreenKey)
                    .action(Route.Companion.getREPLACE_TOP())
                    .persistantState(SplashScreenDI.SplashScreenState.state(new Bundle(),
                            targetRoute));

            targetRoute = AppViewRouterMeta.RouteBuilder.createUriRoute(key)
                    .persistantState(persistantState)
                    .savedState(savedState)
                    .inTransition(inTransition != null ? new RouteTransition(inTransition) : null)
                    .outTransition(outTransition != null ? new RouteTransition(outTransition) : null);

            return AppViewRouterMeta.RouteBuilder.createUriRoute(preconditionKey)
                .persistantState(PreconditionsDI.PreconditionsState.state(new Bundle(), targetRoute,
                        initRoute));
        }

        @ActivityScope
        @Provides
        public LifeCyclesFactory provideLyfeCycleFactory(MultiActivityLifeCyclesFactory factory) {
            return factory;
        }
        @ActivityScope
        @Provides
        public AppBarPresenter provideAppBarOwner(DefaultAppBarPresenter appBarOwner) {
            return appBarOwner;
        }

    }

}
