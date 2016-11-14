package org.vr.example.bootstrap;

import org.vr.di.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by vladimirrybkin on 01/11/16.
 */
@ActivityScope
@Subcomponent(modules = {
        BootstrapModule.class,
})
public interface BootstrapComponent {

}
