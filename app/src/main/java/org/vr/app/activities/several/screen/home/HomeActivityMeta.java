package org.vr.app.activities.several.screen.home;

import org.vr.app.activities.several.router.RouterComponent;
import org.vr.app.bootstrap.BootstrapComponent;
import org.vr.app.common.screens.RootScreenMeta;
import org.vr.app.common.screens.home.HomeScreen;
import org.vr.app.common.screens.home.HomeScreenMeta;
import org.vr.cycle.LifeCycle;
import org.vr.di.ActivityScope;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

/**
 * Created by vladimirrybkin on 25/01/2017.
 */
public class HomeActivityMeta {

    @ActivityScope
    @Component(modules = HomeActivityModule.class)
    public interface HomeActivityComponent {

        void inject(HomeActivity activity);

    }

    @Module
    public static class HomeActivityModule {

        @Provides
        LifeCycle provideLifeCycle() {
            return null;
        }

    }

    @HomeScreenMeta.HomeScreenScope
    @Component(dependencies = {BootstrapComponent.class,
            RouterComponent.class, RootScreenMeta.RootScreenComponent.class},
            modules = {HomeScreenMeta.HomeScreenModule.class})
    public interface HomeScreenComponent {

        void inject(HomeScreen homeScreen);

    }

}
