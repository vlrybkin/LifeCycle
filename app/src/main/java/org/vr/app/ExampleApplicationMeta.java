package org.vr.app;

import android.support.annotation.NonNull;

import org.vr.app.activities.several.ParentActivityComponent;
import org.vr.app.bootstrap.BootstrapComponent;
import org.vr.app.bootstrap.BootstrapConsumer;
import org.vr.app.bootstrap.BootstrapMeta;
import org.vr.di.ApplicationScope;
import org.vr.di.DiActivityModule;
import org.vr.di.DiApplicationModule;

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
    public interface ExampleApplicationComponent {

        ParentActivityComponent createParentActivityComponent(DiActivityModule activityModule);

        BootstrapComponent createBootstrapComponent(BootstrapMeta.BootstrapModule bootstrapModule);

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
