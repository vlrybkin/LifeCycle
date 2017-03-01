package org.vr.app.activities.multi.router.activity;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;

import org.vr.app.common.lifecycles.collapse_recycler.CollapseRecyclerScreenMeta;
import org.vr.app.common.lifecycles.home.HomeScreenDI;
import org.vr.app.common.lifecycles.splash.SplashScreenDI;
import org.vr.app.common.uri.Uris;
import org.vr.di.ActivityScope;
import org.vr.router.route.base.Route;
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
        @SplashScreenDI.SplashScreenQualifier
        @Provides
        public Route provideSplashScreenRoute(
                @SplashScreenDI.SplashScreenQualifier Uri key) {
            return RouteBuilder.createUriRoute(key);
        }

        @ActivityScope
        @HomeScreenDI.HomeScreenQualifier
        @Provides
        public Route provideHomeScreenRoute(@HomeScreenDI.HomeScreenQualifier Uri key) {
            return RouteBuilder.createUriRoute(key);
        }

        @ActivityScope
        @CollapseRecyclerScreenMeta.CollapseRecyclerScreenQualifier
        @Provides
        public Route provideCollapseRecycleScreenRoute(
                @CollapseRecyclerScreenMeta.CollapseRecyclerScreenQualifier Uri key) {
            return RouteBuilder.createUriRoute(key);
        }

    }

}
