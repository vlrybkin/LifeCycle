package org.vr.example;

import android.support.annotation.NonNull;

import org.vr.di.ApplicationScope;
import org.vr.di.DiActivityModule;
import org.vr.di.DiApplicationModule;
import org.vr.example.activities.several.ActivityComponent;
import org.vr.example.bootstrap.BootstrapConsumer;
import org.vr.example.bootstrap.BootstrapMeta;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

/**
 * Created by vladimirrybkin on 20/01/2017.
 */

public class ExampleApplicationMeta {

    @ApplicationScope
    @Component(modules = {
            DiApplicationModule.class,
            ExampleApplicationModule.class
    })
    public static interface ExampleApplicationComponent {

        ActivityComponent createActivityComponent(DiActivityModule activityModule);

        BootstrapMeta.BootstrapComponent createBootstrapComponent(BootstrapMeta.BootstrapModule bootstrapModule);

        BootstrapConsumer bootstrapConsumer();

    }

    @Module
    public static class ExampleApplicationModule {

        @NonNull
        private final BootstrapConsumer bootstrapConsumer;

        public ExampleApplicationModule(@NonNull BootstrapConsumer bootstrapConsumer) {
            this.bootstrapConsumer = bootstrapConsumer;
        }

        @ApplicationScope
        @Provides
        public BootstrapConsumer provideBootstrapConsumer() {
            return bootstrapConsumer;
        }

    }

}
