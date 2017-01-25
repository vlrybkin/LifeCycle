package org.vr.app;

import android.support.annotation.NonNull;

import org.vr.app.bootstrap.BootstrapComponent;
import org.vr.di.DiApplication;
import org.vr.di.DiApplicationModule;
import org.vr.di.DiNames;
import org.vr.app.bootstrap.Bootstrap;
import org.vr.app.bootstrap.BootstrapConsumer;
import org.vr.app.bootstrap.BootstrapMeta;

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
        addComponent(DiNames.Companion.getAPPLICATION_COMPONENT(), component);
    }

    @Override
    public void setup(@NonNull Bootstrap bootstrap) {
        ExampleApplicationMeta.ExampleApplicationComponent applicationComponent =
                (ExampleApplicationMeta.ExampleApplicationComponent) findComponent(DiNames.Companion.getAPPLICATION_COMPONENT());
        BootstrapComponent bootstrapComponent = applicationComponent
                .createBootstrapComponent(new BootstrapMeta.BootstrapModule(bootstrap));
        addComponent(DiNames.Companion.getBOOTSTRAP_COMPONENT(), bootstrapComponent);
    }

}
