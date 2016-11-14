package org.vr.example;

import org.jetbrains.annotations.NotNull;
import org.vr.di.DiApplication;
import org.vr.di.DiContextWrapper;
import org.vr.di.DiNames;

/**
 * Created by vladimirrybkin on 01/11/16.
 */
public class ExampleApplication extends DiApplication {

    @Override
    public void addApplicationComponents(@NotNull DiContextWrapper contextWrapper) {
        ExampleApplicationComponent component = DaggerExampleApplicationComponent.builder()
                .exampleApplicationModule(new ExampleApplicationModule(this)).build();
        contextWrapper.addComponent(DiNames.APPLICATION_COMPONENT, component);
    }

}
