package org.vr.example;

import android.support.annotation.NonNull;

import org.vr.di.DiApplication;
import org.vr.di.DiApplicationModule;
import org.vr.di.DiNames;
import org.vr.example.bootstrap.Bootstrap;
import org.vr.example.bootstrap.BootstrapConsumer;
import org.vr.example.bootstrap.BootstrapMeta;

/**
 * Created by vladimirrybkin on 01/11/16.
 */
public class ExampleApplication extends DiApplication implements BootstrapConsumer {

    @Override
    public void onAddApplicationComponents() {
        ExampleApplicationMeta.ExampleApplicationComponent component =
                DaggerExampleApplicationMeta_ExampleApplicationComponent.builder()
                .diApplicationModule(new DiApplicationModule(this))
                        .exampleApplicationModule(new ExampleApplicationMeta.ExampleApplicationModule(this))
                        .build();
        addComponent(DiNames.APPLICATION_COMPONENT, component);
    }

    @Override
    public void setup(@NonNull Bootstrap bootstrap) {
        ExampleApplicationMeta.ExampleApplicationComponent applicationComponent =
                (ExampleApplicationMeta.ExampleApplicationComponent) findComponent(DiNames.APPLICATION_COMPONENT);
        BootstrapMeta.BootstrapComponent bootstrapComponent = applicationComponent
                .createBootstrapComponent(new BootstrapMeta.BootstrapModule(bootstrap));
        addComponent(DiNames.BOOTSTRAP_COMPONENT, bootstrapComponent);
    }

}
