package org.vr.app.activities.several.screen.splash;

import org.vr.app.ExampleApplicationMeta;
import org.vr.app.activities.several.router.RouterComponent;
import org.vr.app.common.screens.splash.SplashScreen;
import org.vr.app.common.screens.splash.SplashScreenMeta;
import org.vr.cycle.LifeCycle;
import org.vr.di.ActivityScope;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

/**
 * Created by vladimirrybkin on 25/01/2017.
 */
public class SplashActivityMeta {

    @ActivityScope
    @Component(modules = {SplashActivityModule.class})
    public interface SplashActivityComponent {

        void inject(SplashActivity activity);

    }

    @Module
    static class SplashActivityModule {

        @Provides
        LifeCycle provideLifeCycle() {
            return null;
        }

    }

    @SplashScreenMeta.SplashScreenScope
    @Component(dependencies =
            {ExampleApplicationMeta.ExampleApplicationComponent.class, RouterComponent.class},
            modules = {SplashScreenMeta.SplashScreenModule.class})
    public interface SplashScreenComponent {

        void inject(SplashScreen splashScreen);

    }

}
