package org.vr.app.bootstrap;

import android.support.annotation.NonNull;

import javax.inject.Scope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vladimirrybkin on 19/01/2017.
 */

public class BootstrapMeta {

    @Scope
    public static @interface BootstrapScope {
    }

    @Module
    public static class BootstrapModule {

        @NonNull
        private Bootstrap bootstrap;

        public BootstrapModule(@NonNull Bootstrap bootstrap) {
            this.bootstrap = bootstrap;
        }

        @BootstrapScope
        @Provides
        public Bootstrap provideBootstrap() {
            return bootstrap;
        }

    }

}
