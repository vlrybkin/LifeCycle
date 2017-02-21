package org.vr.app.common.routers.view;

import android.net.Uri;
import android.support.annotation.NonNull;

import org.vr.app.common.uri.Uris;
import org.vr.router.route.base.Route;

import dagger.Module;

/**
 * Created by vladimirrybkin on 17/01/2017.
 */
public class AppViewRouterMeta {

    public static class RouteBuilder {

        @NonNull
        public static Route createUriRoute(@NonNull Uri screenKey) {
            return new Route(screenKey).scheme(Uris.SCHEME).authority(Uris.HOST_AUTHORITY_VIEW_ROUTER);
        }

    }

    @Module
    public static class AppViewRouterModule {

//        @PreconditionsMeta.PreconditionsQualifier
//        @Provides
//        public Route providePreconditionsRoute(@PreconditionsMeta.PreconditionsQualifier Uri key) {
//            return RouteBuilder.createUriRoute(key);
//        }
//
//        @SplashScreenMeta.SplashScreenQualifier(Uris.HOST_AUTHORITY_VIEW_ROUTER)
//        @Provides
//        public Route provideSplashScreenRoute(@SplashScreenMeta.SplashScreenQualifier Uri key) {
//            return RouteBuilder.createUriRoute(key);
//        }
//
//        @HomeScreenMeta.HomeScreenQualifier(Uris.HOST_AUTHORITY_VIEW_ROUTER)
//        @Provides
//        public Route provideHomeScreenRoute(@HomeScreenMeta.HomeScreenQualifier Uri key) {
//            return RouteBuilder.createUriRoute(key);
//        }

    }

}
