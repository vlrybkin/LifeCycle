package org.vr.example.bootstrap;

import android.support.annotation.NonNull;

import javax.inject.Scope;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;

/**
 * Created by vladimirrybkin on 19/01/2017.
 */

public class BootstrapMeta {

    @Scope
    public static @interface BootstrapScope {
    }

    /**
     * Created by vladimirrybkin on 11/01/2017.
     */
    @BootstrapScope
    @Subcomponent(modules = {
            BootstrapModule.class
    })
    public static interface BootstrapComponent {

    }

    /**
     * Created by vladimirrybkin on 01/11/16.
     */
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
