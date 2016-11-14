package org.vr.example.activities;

import org.vr.di.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by vladimirrybkin on 01/11/16.
 */
@ActivityScope
@Subcomponent(modules = {
        ActivityModule.class,
})
public interface ActivityComponent {

}
