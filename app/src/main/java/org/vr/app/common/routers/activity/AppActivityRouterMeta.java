package org.vr.app.common.routers.activity;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;

import org.vr.app.activities.multi.router.AppActivityRouterLaunchDataFactory;
import org.vr.app.common.lifecycles.home.HomeScreenMeta;
import org.vr.app.common.lifecycles.splash.SplashScreenMeta;
import org.vr.app.common.uri.Uris;
import org.vr.di.ActivityScope;
import org.vr.router.route.base.Route;
import org.vr.router.route.transition.RouteTransition;
import org.vr.router.router.base.Router;
import org.vr.router.router.uri.activity.ActivityRouter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vladimirrybkin on 17/01/2017.
 */
public class AppActivityRouterMeta {

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface AppActivityRouterQualifier {
        String value() default "";
    }

    public static class RouteBuilder {

        @NonNull
        public static Route createUriRoute(@NonNull Uri screenKey) {
            return new Route(screenKey).scheme(Uris.SCHEME).authority(Uris.HOST_AUTHORITY_ACTIVITY_ROUTER);
        }

    }

    @Module
    public static class AppActivityRouterModule {

        @ActivityScope
        @Provides
        public ActivityRouter provideAppActivityRouter(Activity activity,
                AppActivityRouterLaunchDataFactory appActivityRouterLaunchDataFactory) {
            return new ActivityRouter(activity, appActivityRouterLaunchDataFactory);
        }

        @AppActivityRouterQualifier
        @ActivityScope
        @Provides
        public Router<Uri> provideRouter(ActivityRouter appActivityRouter) {
            return appActivityRouter;
        }

        @ActivityScope
        @SplashScreenMeta.SplashScreenQualifier
        @Provides
        public Route provideDefaultSplashScreenRoute(
                @SplashScreenMeta.SplashScreenQualifier(Uris.HOST_AUTHORITY_ACTIVITY_ROUTER)
                        Route route) {
            return route.inTransition(new RouteTransition(RouteTransition.Companion.getTYPE_FADEIN()));
        }

        @ActivityScope
        @SplashScreenMeta.SplashScreenQualifier(Uris.HOST_AUTHORITY_ACTIVITY_ROUTER)
        @Provides
        public Route provideSplashScreenRoute(
                @SplashScreenMeta.SplashScreenQualifier Uri key) {
            return RouteBuilder.createUriRoute(key);
        }

        @ActivityScope
        @HomeScreenMeta.HomeScreenQualifier(Uris.HOST_AUTHORITY_ACTIVITY_ROUTER)
        @Provides
        public Route provideHomeScreenRoute(@HomeScreenMeta.HomeScreenQualifier Uri key) {
            return RouteBuilder.createUriRoute(key);
        }

    }

}
