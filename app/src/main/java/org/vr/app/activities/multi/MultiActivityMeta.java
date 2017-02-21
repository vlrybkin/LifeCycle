package org.vr.app.activities.multi;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.vr.app.activities.multi.router.MultiActivityLifeCyclesFactory;
import org.vr.app.common.lifecycles.LifeCyclesFactory;
import org.vr.app.common.lifecycles.home.HomeScreenMeta;
import org.vr.app.common.lifecycles.preconditions.PreconditionsMeta;
import org.vr.app.common.lifecycles.splash.SplashScreenMeta;
import org.vr.app.common.routers.activity.AppActivityRouterMeta;
import org.vr.app.common.routers.view.AppViewRouterMeta;
import org.vr.app.common.toolbar.AppBarOwner;
import org.vr.app.common.toolbar.DefaultAppBarOwner;
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
                                         @PreconditionsMeta.PreconditionsQualifier Uri preconditionKey,
                                         @HomeScreenMeta.HomeScreenQualifier Uri homeScreenKey,
                                         @SplashScreenMeta.SplashScreenQualifier Uri splashScreenKey) {
            Intent intent = activity.getIntent();
            Bundle savedState = savedInstanceState;
            Bundle inTransition = null;
            Bundle outTransition = null;

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
                    .persistantState(persistantState)
                    .savedState(savedState)
                    .inTransition(inTransition != null ? new RouteTransition(inTransition) : null)
                    .outTransition(outTransition != null ? new RouteTransition(outTransition) : null);

            Route initRoute = AppActivityRouterMeta.RouteBuilder.createUriRoute(splashScreenKey)
                    .persistantState(SplashScreenMeta.SplashScreenState.state(new Bundle(),
                            targetRoute));

            targetRoute = AppViewRouterMeta.RouteBuilder.createUriRoute(key)
                    .persistantState(persistantState)
                    .savedState(savedState)
                    .inTransition(inTransition != null ? new RouteTransition(inTransition) : null)
                    .outTransition(outTransition != null ? new RouteTransition(outTransition) : null);

            return AppViewRouterMeta.RouteBuilder.createUriRoute(preconditionKey)
                .persistantState(PreconditionsMeta.PreconditionsState.state(new Bundle(), targetRoute,
                        initRoute));
        }

        @ActivityScope
        @Provides
        public LifeCyclesFactory provideLyfeCycleFactory(MultiActivityLifeCyclesFactory factory) {
            return factory;
        }
        @ActivityScope
        @Provides
        public AppBarOwner provideAppBarOwner(DefaultAppBarOwner appBarOwner) {
            return appBarOwner;
        }

    }

}
