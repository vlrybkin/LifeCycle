package org.vr.example.common.routes;

import org.vr.cycle.LifeCycle;
import org.vr.example.common.screens.RootScreen;
import org.vr.example.common.screens.RootScreenMeta;
import org.vr.example.common.screens.home.HomeScreen;
import org.vr.example.common.screens.home.HomeScreenMeta;
import org.vr.example.common.screens.splash.SplashScreen;
import org.vr.example.common.screens.splash.SplashScreenMeta;
import org.vr.router.Router;
import org.vr.router.command.DefaultRoute;
import org.vr.router.command.RestartRoute;
import org.vr.router.command.Route;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vladimirrybkin on 28/11/16.
 */
@Module
public class RoutesModule {

    @RootScreenMeta.RootScreenQualifier
    @Provides
    public String provideRootScreenKey() {
        return LifeCycle.Companion.getKey(RootScreen.class);
    }

    @SplashScreenMeta.SplashScreenQualifier
    @Provides
    public String provideSplashScreenKey() {
        return LifeCycle.Companion.getKey(SplashScreen.class);
    }

    @HomeScreenMeta.HomeScreenQualifier
    @Provides
    public String provideHomeScreenKey() {
        return LifeCycle.Companion.getKey(HomeScreen.class);
    }

    @SplashScreenMeta.SplashScreenQualifier
    @Provides
    public RestartRoute provideSplashScreenRoute(Router router,
        @SplashScreenMeta.SplashScreenQualifier String key) {
        return new RestartRoute(router, key);
    }

    @HomeScreenMeta.HomeScreenQualifier
    @Provides
    public DefaultRoute provideHomeScreenRoute(Router router, @HomeScreenMeta.HomeScreenQualifier String key) {
        return new DefaultRoute(router, key, Route.Action.CLEARSTACK);
    }

}
