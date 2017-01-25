package org.vr.app.activities.several.router;

import android.content.Context;
import android.support.annotation.NonNull;

import org.vr.app.ExampleApplicationMeta;
import org.vr.app.activities.several.screen.home.DaggerHomeActivityMeta_HomeScreenComponent;
import org.vr.app.activities.several.screen.home.HomeActivityMeta;
import org.vr.app.activities.several.screen.splash.DaggerSplashActivityMeta_SplashScreenComponent;
import org.vr.app.activities.several.screen.splash.SplashActivityMeta;
import org.vr.app.bootstrap.BootstrapComponent;
import org.vr.app.common.screens.DaggerRootScreenMeta_RootScreenComponent;
import org.vr.app.common.screens.RootScreen;
import org.vr.app.common.screens.RootScreenMeta;
import org.vr.app.common.screens.home.HomeScreen;
import org.vr.app.common.screens.home.HomeScreenMeta;
import org.vr.app.common.screens.splash.SplashScreen;
import org.vr.app.common.screens.splash.SplashScreenMeta;
import org.vr.cycle.LifeCycle;
import org.vr.di.DiNames;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by vladimirrybkin on 19/01/2017.
 */
public class ScreenFactory extends HashMap<String, ScreenFactory.Func>{

    @Inject
    public ScreenFactory(@RootScreenMeta.RootScreenQualifier String rootScreenKey,
            @SplashScreenMeta.SplashScreenQualifier String splashScreenKey,
            @HomeScreenMeta.HomeScreenQualifier String homeScreenKey) {
        put(rootScreenKey, params -> new RootScreen((context, screen, deps) -> {
            RootScreenMeta.RootScreenComponent rootScreenComponent =
                    DaggerRootScreenMeta_RootScreenComponent.builder()
                            .rootScreenModule((RootScreenMeta.RootScreenModule) deps[0]).build();
            rootScreenComponent.inject(screen);
            return rootScreenComponent;
        }, (LifeCycle) params[0]));

        put(splashScreenKey, params -> new SplashScreen((context, screen, deps) -> {
            SplashActivityMeta.SplashScreenComponent splashScreenComponent =
                    DaggerSplashActivityMeta_SplashScreenComponent.builder()
                            .exampleApplicationComponent(getApplicationComponent(context)).routerComponent(
                            getRouterComponent(context))
                            .splashScreenModule((SplashScreenMeta.SplashScreenModule) deps[0]).build();
            splashScreenComponent.inject(screen);
            return splashScreenComponent;
        }));

        put(homeScreenKey, params -> new HomeScreen((context, screen, deps) -> {
            HomeActivityMeta.HomeScreenComponent homeScreenComponent =
                    DaggerHomeActivityMeta_HomeScreenComponent.builder()
                            .bootstrapComponent(getBootstrapComponent(context))
                            .routerComponent(getRouterComponent(context))
                            .rootScreenComponent(getRootScreenComponent(context))
                            .homeScreenModule((HomeScreenMeta.HomeScreenModule) deps[0])
                            .build();
            homeScreenComponent.inject(screen);
            return homeScreenComponent;
        }));
    }

    @NonNull
    private ExampleApplicationMeta.ExampleApplicationComponent getApplicationComponent(Context context) {
        return (ExampleApplicationMeta.ExampleApplicationComponent) context.getSystemService(
                DiNames.Companion.getAPPLICATION_COMPONENT());
    }

    @NonNull
    private BootstrapComponent getBootstrapComponent(@NonNull Context context) {
        return (BootstrapComponent) context.getSystemService(DiNames.Companion.getBOOTSTRAP_COMPONENT());
    }

    @NonNull
    private RouterComponent getRouterComponent(@NonNull Context context) {
        return (RouterComponent) context.getSystemService(DiNames.Companion.getROUTER_COMPONENT());
    }

    @NonNull
    private RootScreenMeta.RootScreenComponent getRootScreenComponent(@NonNull Context context) {
        return (RootScreenMeta.RootScreenComponent) context.getSystemService(DiNames.Companion.getROOT_SCREEN_COMPONENT());
    }

    @NonNull
    public LifeCycle create(@NonNull String key, Object... params) {
        return get(key).produce(params);
    }

    protected interface Func {

        @NonNull
        LifeCycle produce(Object... params);

    }

}
