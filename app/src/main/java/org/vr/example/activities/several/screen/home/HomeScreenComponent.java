package org.vr.example.activities.several.screen.home;

import android.support.annotation.NonNull;

import org.vr.example.activities.several.router.RouterComponent;
import org.vr.example.bootstrap.BootstrapMeta;
import org.vr.example.common.screens.RootScreenMeta;
import org.vr.example.common.screens.home.HomeScreen;
import org.vr.example.common.screens.home.HomeScreenMeta;

import dagger.Component;

/**
 * Created by vladimirrybkin on 11/01/2017.
 */
@HomeScreenMeta.HomeScreenScope
@Component(dependencies = {BootstrapMeta.BootstrapComponent.class,
        RouterComponent.class, RootScreenMeta.RootScreenComponent.class},
        modules = {HomeScreenMeta.HomeScreenModule.class})
public interface HomeScreenComponent {

    void inject(@NonNull HomeScreen homeScreen);

}
