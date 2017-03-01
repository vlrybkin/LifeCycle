package org.vr.app.common.lifecycles.splash;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import org.vr.app.bootstrap.BootstrapConsumer;
import org.vr.router.route.base.Route;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;
import javax.inject.Scope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vladimirrybkin on 17/01/2017.
 */
public class SplashScreenDI {

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SplashScreenQualifier {
        String value() default "";
    }

    @Scope
    public @interface SplashScreenScope {
    }

    public interface Injector {

        @NonNull
        Object inject(Context context,
                      SplashScreen source,
                      SplashScreenModule module);

    }

    @Module
    public static class SplashScreenModule {

        @NonNull
        private final Bundle persistantState;

        public SplashScreenModule(@NonNull Bundle persistantState) {
            this.persistantState = persistantState;
        }

        @SplashScreenScope
        @Provides
        public Route provideBackRoute() {
            return SplashScreenState.getInitialLifeCycleRoute(persistantState);
        }

        @SplashScreenScope
        @Provides
        public SplashScreenContract.SplashScreenPresenter providePresenter(@NonNull Context context,
                @NonNull BootstrapConsumer bootstrapComsumer,
                @NonNull Route routeBack) {
            return new SplashScreenPresenter(context, bootstrapComsumer, routeBack);
        }

    }

}
