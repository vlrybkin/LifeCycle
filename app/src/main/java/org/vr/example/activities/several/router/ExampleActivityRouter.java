package org.vr.example.activities.several.router;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;
import org.vr.cycle.LifeCycle;
import org.vr.di.DiNames;
import org.vr.di.DiRouterModule;
import org.vr.example.activities.several.ActivityComponent;
import org.vr.example.annotations.BootstrapRequired;
import org.vr.example.annotations.RootScreenRequired;
import org.vr.example.common.routes.RoutesModule;
import org.vr.example.common.screens.RootScreen;
import org.vr.example.common.screens.splash.SplashScreenMeta;
import org.vr.router.activity.ActivityRouter;
import org.vr.router.command.RestartRoute;

import javax.inject.Inject;

/**
 * Created by vladimirrybkin on 12/01/2017.
 */
public class ExampleActivityRouter extends ActivityRouter {

    @NotNull
    private final String initialLifecycleKey;
    @NonNull
    private RestartRoute splashScreenRoute;
    @NonNull
    private LaunchDataResolver launchDataResolver;
    @NonNull
    private ScreenFactory screenFactory;

    public ExampleActivityRouter(@NotNull Activity activity, @NotNull String initialLifecycleKey) {
        super(activity);
        this.initialLifecycleKey = initialLifecycleKey;
    }

    @Inject
    public void setScreenFactory(@NonNull ScreenFactory screenFactory) {
        this.screenFactory = screenFactory;
    }

    @Inject
    public void setSplashScreenRoute(@SplashScreenMeta.SplashScreenQualifier
                                         @NonNull RestartRoute splashScreenRoute) {
        this.splashScreenRoute = splashScreenRoute;
    }

    @Inject
    public void setLaunchDataResolver(@NonNull LaunchDataResolver launchDataResolver) {
        this.launchDataResolver = launchDataResolver;
    }

    @NotNull
    @Override
    protected LaunchData resolveLaunch(@NotNull String key, @Nullable Bundle state) {
        return launchDataResolver.resolve(key, state);
    }

    @Nullable
    @Override
    protected LifeCycle getInitialLifecycle(@Nullable Bundle initState,@Nullable Bundle savedState) {
        LifeCycle initialLifecycle = screenFactory.create(initialLifecycleKey);
        BootstrapRequired bootstrapRequiredAnnotation =
                initialLifecycle.getClass().getAnnotation(BootstrapRequired.class);
        boolean bootstrapRequired = bootstrapRequiredAnnotation == null || bootstrapRequiredAnnotation.required();
        boolean launchSplash = bootstrapRequired && context.getSystemService(DiNames.BOOTSTRAP_COMPONENT) == null;

        if (launchSplash) {
            launchSplash(initialLifecycle, initState, savedState);
            return null;
        } else {
            RootScreenRequired rootScreenRequireAnnotation =
                    initialLifecycle.getClass().getAnnotation(RootScreenRequired.class);
            boolean rootScreenRequired = rootScreenRequireAnnotation == null || rootScreenRequireAnnotation.required();
            if (rootScreenRequired) {
                return screenFactory.create(LifeCycle.Companion.getKey(RootScreen.class), initialLifecycle);
            } else {
                return initialLifecycle;
            }
        }
    }

    protected void launchSplash(@NonNull LifeCycle initialLifecycle, @Nullable Bundle initState,
                                @Nullable Bundle savedState) {
        String backKey = initialLifecycle.getKey();
        splashScreenRoute.backInfo(backKey, initState, savedState).go();
    }

    @NonNull
    @Override
    protected Object createComponent(@NotNull ViewGroup viewContainer) {
        ActivityComponent activityComponent =
                (ActivityComponent) context.getSystemService(DiNames.ACTIVITY_COMPONENT);
        RouterComponent routerComponent = activityComponent.createRouterComponent
                (new DiRouterModule(this), new RoutesModule());
        routerComponent.inject(this);
        return routerComponent;
    }

}
