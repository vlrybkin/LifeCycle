package org.vr.app.activities.several.router;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;
import org.vr.app.activities.several.ParentActivityComponent;
import org.vr.app.annotations.BootstrapRequired;
import org.vr.app.annotations.RootScreenRequired;
import org.vr.app.common.routes.RoutesModule;
import org.vr.app.common.screens.RootScreen;
import org.vr.app.common.screens.splash.SplashScreenMeta;
import org.vr.cycle.LifeCycle;
import org.vr.cycle.LifecycleWrapper;
import org.vr.di.DiNames;
import org.vr.di.DiRouterModule;
import org.vr.framework.router.LifecycleRouterWrapper;
import org.vr.router.Router;
import org.vr.router.command.RestartRoute;

import javax.inject.Inject;

/**
 * Created by vladimirrybkin on 12/01/2017.
 */
public class ExampleActivityRouter extends LifecycleRouterWrapper {

    @NotNull
    private final String initialLifecycleKey;
    @NonNull
    private RestartRoute splashScreenRoute;
    @NonNull
    private ScreenFactory screenFactory;

    public ExampleActivityRouter(@NonNull Router innerRouter,
                                 @NotNull String initialLifecycleKey) {
        super(innerRouter, new LifecycleWrapper());
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

    @Nullable
    @Override
    protected LifeCycle getInitialLifecycle(@Nullable Bundle initState,@Nullable Bundle savedState) {
        LifeCycle initialLifecycle = screenFactory.create(initialLifecycleKey);
        BootstrapRequired bootstrapRequiredAnnotation =
                initialLifecycle.getClass().getAnnotation(BootstrapRequired.class);
        boolean bootstrapRequired = bootstrapRequiredAnnotation == null || bootstrapRequiredAnnotation.required();
        boolean launchSplash = bootstrapRequired && context.getSystemService(DiNames.Companion.getBOOTSTRAP_COMPONENT()) == null;

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
        ParentActivityComponent activityComponent =
                (ParentActivityComponent) context.getSystemService(DiNames.Companion.getACTIVITY_COMPONENT());
        RouterComponent routerComponent = activityComponent.createRouterComponent
                (new DiRouterModule(this), new RoutesModule());
        routerComponent.inject(this);
        return routerComponent;
    }

}
