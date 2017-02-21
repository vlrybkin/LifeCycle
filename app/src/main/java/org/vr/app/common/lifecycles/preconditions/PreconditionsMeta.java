package org.vr.app.common.lifecycles.preconditions;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;

import org.vr.app.annotations.BootstrapRequired;
import org.vr.app.common.lifecycles.LifeCyclesFactory;
import org.vr.router.route.base.Route;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;
import javax.inject.Scope;

import dagger.Module;
import dagger.Provides;

import static org.vr.app.common.lifecycles.preconditions.PreconditionsMeta.PreconditionsState.getContinueRoute;
import static org.vr.app.common.lifecycles.preconditions.PreconditionsMeta.PreconditionsState.getInitRoute;

/**
 * Created by vladimirrybkin on 17/01/2017.
 */
public class PreconditionsMeta {

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface PreconditionsQualifier {
        String value() default "";
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CheckBootstrap {
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ContinueRoute {
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface InitRoute {
    }

    @Scope
    public @interface PreconditionsScope {
    }

    public static final class PreconditionsState {

        private static final String CONTINUE_ROUTE = PreconditionsState.class.getSimpleName() + "#CONTINUE_ROUTE";
        private static final String INIT_ROUTE = PreconditionsState.class.getSimpleName() + "#INIT_ROUTE";

        public static Bundle state(@NonNull Bundle out,
                                   @NonNull Route continueRoute,
                                   @NonNull Route initRoute) {
            Bundle bundle = new Bundle();
            continueRoute.toBundle(bundle);
            out.putBundle(CONTINUE_ROUTE, bundle);

            bundle = new Bundle();
            initRoute.toBundle(bundle);
            out.putBundle(INIT_ROUTE, bundle);
            return out;
        }

        public static Route getContinueRoute(@NonNull Bundle persistantState) {
            if (persistantState.getBundle(CONTINUE_ROUTE) == null) {
                throw new RuntimeException("Illegal state");
            }

            return new Route(persistantState.getBundle(CONTINUE_ROUTE));
        }

        public static Route getInitRoute(@NonNull Bundle persistantState) {
            if (persistantState.getBundle(INIT_ROUTE) == null) {
                throw new RuntimeException("Illegal state");
            }

            return new Route(persistantState.getBundle(INIT_ROUTE));
        }

    }

    @Module
    public static class PreconditionsModule {

        @NonNull
        private final Bundle persistantState;

        public PreconditionsModule(@NonNull Bundle persistantState) {
            this.persistantState = persistantState;
        }

        @PreconditionsScope
        @CheckBootstrap
        @Provides
        public Boolean provideCheckBootstrap(LifeCyclesFactory lifeCyclesFactory,
                                             @ContinueRoute Route backRoute) {
            Uri backKey = backRoute.getKey();
            Class<?> clazz = lifeCyclesFactory.getClass(backKey.getPath());
            BootstrapRequired bootstrapRequiredAnnotation = clazz.getAnnotation(BootstrapRequired.class);
            return bootstrapRequiredAnnotation == null || bootstrapRequiredAnnotation.required();
        }

        @PreconditionsScope
        @ContinueRoute
        @Provides
        public Route provideContinueRoute() {
            return getContinueRoute(persistantState);
        }

        @PreconditionsScope
        @InitRoute
        @Provides
        public Route provideSplashScreenRoute() {
            return getInitRoute(persistantState);
        }

    }

}
