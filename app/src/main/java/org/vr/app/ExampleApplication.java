package org.vr.app;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.vr.app.bootstrap.Bootstrap;
import org.vr.app.bootstrap.BootstrapConsumer;
import org.vr.app.bootstrap.BootstrapMeta;
import org.vr.app.bootstrap.BootstrapProvider;
import org.vr.app.common.lifecycles.LifeCycleKeysModule;
import org.vr.di.DiApplication;
import org.vr.di.DiApplicationModule;
import org.vr.di.DiNames;

/**
 * Created by vladimirrybkin on 01/11/16.
 */
public class ExampleApplication extends DiApplication implements BootstrapConsumer, BootstrapProvider {

    @Nullable
    private Bootstrap bootstrap;

    @Override
    public void onAddApplicationComponents() {
        ExampleApplicationMeta.ExampleApplicationComponent component =
                DaggerExampleApplicationMeta_ExampleApplicationComponent.builder()
                    .diApplicationModule(new DiApplicationModule(this))
                    .exampleApplicationModule(new ExampleApplicationMeta.ExampleApplicationModule(this))
                    .bootstrapModule(new BootstrapMeta.BootstrapModule(this))
                    .lifeCycleKeysModule(new LifeCycleKeysModule())
                    .build();
        addComponent(DiNames.Companion.getAPPLICATION_COMPONENT(), component);
    }

    @Override
    public boolean needBootstrapUpdate() {
        return bootstrap == null;
    }

    @Override
    public void setup(@NonNull Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    @Override
    public void clear() {
        bootstrap = null;
    }

    @Nullable
    @Override
    public Bootstrap getBootstrap() {
        return bootstrap;
    }

}
