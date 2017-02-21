package org.vr.app.activities.multi.router;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Pair;

import org.vr.app.activities.multi.MultiActivityLifeCycleMeta;
import org.vr.app.activities.multi.lifecycles.HomeScreenComponent;
import org.vr.app.activities.multi.lifecycles.PreconditionsComponent;
import org.vr.app.activities.multi.lifecycles.SplashScreenComponent;
import org.vr.app.common.lifecycles.LifeCyclesFactory;
import org.vr.app.common.lifecycles.home.HomeScreen;
import org.vr.app.common.lifecycles.home.HomeScreenMeta;
import org.vr.app.common.lifecycles.preconditions.PreconditionsLifeCycle;
import org.vr.app.common.lifecycles.preconditions.PreconditionsMeta;
import org.vr.app.common.lifecycles.splash.SplashScreen;
import org.vr.app.common.lifecycles.splash.SplashScreenMeta;
import org.vr.cycle.LifeCycle;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by vladimirrybkin on 19/01/2017.
 */
public class MultiActivityLifeCyclesFactory implements LifeCyclesFactory {

    private HashMap<String, Pair<Class<?>, Func>> produceMap = new HashMap<>();

    @Inject
    public MultiActivityLifeCyclesFactory(@PreconditionsMeta.PreconditionsQualifier Uri preconditionsKey,
                                          @SplashScreenMeta.SplashScreenQualifier Uri splashScreenKey,
                                          @HomeScreenMeta.HomeScreenQualifier Uri homeScreenKey) {
        produceMap.put(preconditionsKey.getPath(), new Pair<>(PreconditionsLifeCycle.class,
                params -> new PreconditionsLifeCycle((context, lifecycle, deps) -> {
                    MultiActivityLifeCycleMeta.MultiActivityLifeCycleComponent lifeCycleComponent = getMultiActivityLifeCycleComponent(context);
            PreconditionsComponent preconditionsComponent = lifeCycleComponent.createPreconditionsComponent(
                    (PreconditionsMeta.PreconditionsModule) deps[0]);
            preconditionsComponent.inject(lifecycle);
            return preconditionsComponent;
        })));

        produceMap.put(splashScreenKey.getPath(), new Pair<>(SplashScreen.class,
            params -> new SplashScreen((context, screen, deps) -> {
                MultiActivityLifeCycleMeta.MultiActivityLifeCycleComponent lifeCycleComponent = getMultiActivityLifeCycleComponent(context);
            SplashScreenComponent splashScreenComponent = lifeCycleComponent.createSplashScreenComponent(
                    (SplashScreenMeta.SplashScreenModule) deps[0]);
            splashScreenComponent.inject(screen);
            return splashScreenComponent;
        })));

        produceMap.put(homeScreenKey.getPath(), new Pair<>(HomeScreen.class,
            params -> new HomeScreen((context, screen, deps) -> {
                MultiActivityLifeCycleMeta.MultiActivityLifeCycleComponent lifeCycleComponent = getMultiActivityLifeCycleComponent(context);
                HomeScreenComponent homeScreenComponent = lifeCycleComponent.createHomeScreenComponent(
                        (HomeScreenMeta.HomeScreenModule) deps[0]);
                homeScreenComponent.inject(screen);
                return homeScreenComponent;
        })));

    }

    @NonNull
    private MultiActivityLifeCycleMeta.MultiActivityLifeCycleComponent getMultiActivityLifeCycleComponent(@NonNull Context context) {
        return (MultiActivityLifeCycleMeta.MultiActivityLifeCycleComponent) context
                .getSystemService(MultiActivityLifeCycleMeta.MultiActivityLifeCycleComponent.name);
    }

    @NonNull
    public LifeCycle create(@NonNull String path, Object... params) {
        return produceMap.get(path).second.produce(params);
    }

    @NonNull
    public Class<?> getClass(@NonNull String path) {
        return produceMap.get(path).first;
    }

    protected interface Func {

        @NonNull
        LifeCycle produce(Object... params);

    }

}
