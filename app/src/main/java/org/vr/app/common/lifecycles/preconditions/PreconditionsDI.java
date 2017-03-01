package org.vr.app.common.lifecycles.preconditions;

import android.content.Context;
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

import static org.vr.app.common.lifecycles.preconditions.PreconditionsState.getContinueRoute;
import static org.vr.app.common.lifecycles.preconditions.PreconditionsState.getInitRoute;

/**
 * Created by vladimirrybkin on 17/01/2017.
 */
public class PreconditionsDI {

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

    public interface Injector {

        @NonNull
        Object inject(Context context,
                      PreconditionsLifeCycle source,
                      PreconditionsModule module);

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
