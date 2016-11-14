package org.vr.example;

import org.vr.di.ApplicationScope;
import org.vr.example.activities.ActivityComponent;
import org.vr.example.activities.ActivityModule;
import org.vr.example.bootstrap.BootstrapComponent;
import org.vr.example.bootstrap.BootstrapModule;

import dagger.Component;

/**
 * Created by vladimirrybkin on 01/11/16.
 */
@ApplicationScope
@Component(modules = {
        ExampleApplicationModule.class,
})
public interface ExampleApplicationComponent {

    BootstrapComponent getBootstrapComponent(BootstrapModule bootstrapModule);

    ActivityComponent getActivityComponent(ActivityModule activityModule);

}
