package org.vr.example.activities.several.screen.splash;

import android.support.annotation.NonNull;

import org.vr.example.ExampleApplicationMeta;
import org.vr.example.activities.several.router.RouterComponent;
import org.vr.example.common.screens.splash.SplashScreen;
import org.vr.example.common.screens.splash.SplashScreenMeta;

import dagger.Component;

/**
 * Created by vladimirrybkin on 11/01/2017.
 */
@SplashScreenMeta.SplashScreenScope
@Component(dependencies = {ExampleApplicationMeta.ExampleApplicationComponent.class, RouterComponent.class},
        modules = {SplashScreenMeta.SplashScreenModule.class})
public interface SplashScreenComponent {

    void inject(@NonNull SplashScreen splashScreen);

}
