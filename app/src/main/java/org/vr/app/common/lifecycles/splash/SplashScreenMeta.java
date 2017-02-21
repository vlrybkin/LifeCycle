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
public class SplashScreenMeta {

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SplashScreenQualifier {
        String value() default "";
    }

    @Scope
    public @interface SplashScreenScope {
    }

    public static final class SplashScreenState {

        private static final String ROUTE = SplashScreenState.class.getSimpleName() + "#ROUTE";

        public static Bundle state(@NonNull Bundle out, @NonNull Route route) {
            Bundle bundle = new Bundle();
            route.toBundle(bundle);
            out.putBundle(ROUTE, bundle);
            return out;
        }

        public static Route getInitialLifeCycleRoute(@NonNull Bundle persistantState) {
            if (persistantState.getBundle(ROUTE) == null) {
                throw new RuntimeException("Illegal state");
            }

            return new Route(persistantState.getBundle(ROUTE));
        }

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
