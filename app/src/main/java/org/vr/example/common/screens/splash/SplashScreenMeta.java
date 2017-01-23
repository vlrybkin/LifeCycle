package org.vr.example.common.screens.splash;

import android.os.Bundle;
import android.support.annotation.NonNull;

import org.vr.example.bootstrap.BootstrapConsumer;
import org.vr.router.Router;
import org.vr.router.command.RestartRoute;
import org.vr.router.command.Route;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;
import javax.inject.Scope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vladimirrybkin on 17/01/2017.
 */
public class SplashScreenMeta {

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SplashScreenQualifier {
    }

    @Scope
    public @interface SplashScreenScope {
    }

    @Module
    public static class SplashScreenModule {

        @NonNull
        private final Bundle initialState;

        public SplashScreenModule(@NonNull Bundle initialState) {
            this.initialState = initialState;
        }

        @SplashScreenScope
        @Provides
        public Route provideBackRoute(@NonNull Router router) {
            return new RestartRoute(router, initialState);
        }

        @SplashScreenScope
        @Provides
        public SplashScreenContract.SplashScreenPresenter providePresenter(
                @NonNull BootstrapConsumer bootstrapComsumer,
                @NonNull Route route) {
            return new SplashScreenPresenter(bootstrapComsumer, route);
        }

    }

}
