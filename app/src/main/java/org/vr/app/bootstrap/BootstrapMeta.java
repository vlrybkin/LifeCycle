package org.vr.app.bootstrap;

import android.support.annotation.NonNull;

import org.vr.di.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vladimirrybkin on 19/01/2017.
 */
public class BootstrapMeta {

    @Module
    public static class BootstrapModule {

        @NonNull
        private BootstrapProvider bootstrapProvider;

        public BootstrapModule(@NonNull BootstrapProvider bootstrapProvider) {
            this.bootstrapProvider = bootstrapProvider;
        }

        @ApplicationScope
        @Provides
        public Bootstrap provideBootstrap() {
            Bootstrap bootstrap = bootstrapProvider.getBootstrap();
            if (bootstrap == null) {
                throw new RuntimeException("Bootstrap is not prepared");
            }
            return bootstrap;
        }

    }

}
