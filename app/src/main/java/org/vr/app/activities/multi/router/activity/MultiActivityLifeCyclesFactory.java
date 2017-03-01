package org.vr.app.activities.multi.router.activity;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Pair;

import org.vr.app.activities.multi.MultiActivityLifeCycleMeta;
import org.vr.app.activities.multi.lifecycles.CollapsingRecyclerScreenComponent;
import org.vr.app.activities.multi.lifecycles.HomeScreenComponent;
import org.vr.app.activities.multi.lifecycles.PreconditionsComponent;
import org.vr.app.activities.multi.lifecycles.SplashScreenComponent;
import org.vr.app.common.lifecycles.collapse_recycler.CollapseRecyclerScreen;
import org.vr.app.common.lifecycles.collapse_recycler.CollapseRecyclerScreenMeta;
import org.vr.app.common.lifecycles.LifeCyclesFactory;
import org.vr.app.common.lifecycles.home.HomeScreen;
import org.vr.app.common.lifecycles.home.HomeScreenDI;
import org.vr.app.common.lifecycles.preconditions.PreconditionsLifeCycle;
import org.vr.app.common.lifecycles.preconditions.PreconditionsDI;
import org.vr.app.common.lifecycles.splash.SplashScreen;
import org.vr.app.common.lifecycles.splash.SplashScreenDI;
import org.vr.app.common.ui.ToolbarModule;
import org.vr.cycle.LifeCycle;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by vladimirrybkin on 19/01/2017.
 */
public class MultiActivityLifeCyclesFactory implements LifeCyclesFactory {

    private HashMap<String, Pair<Class<?>, Func>> produceMap = new HashMap<>();

    @Inject
    public MultiActivityLifeCyclesFactory(@PreconditionsDI.PreconditionsQualifier Uri preconditionsKey,
                                          @SplashScreenDI.SplashScreenQualifier Uri splashScreenKey,
                                          @HomeScreenDI.HomeScreenQualifier Uri homeScreenKey,
                                          @CollapseRecyclerScreenMeta.CollapseRecyclerScreenQualifier
                                                      Uri collapsingRecyclerKey) {
        produceMap.put(preconditionsKey.getPath(), new Pair<>(PreconditionsLifeCycle.class,
                params ->
                        new PreconditionsLifeCycle((context, lifecycle, module) -> {
                    MultiActivityLifeCycleMeta.MultiActivityLifeCycleComponent lifeCycleComponent =
                            getMultiActivityLifeCycleComponent(context);
            PreconditionsComponent preconditionsComponent = lifeCycleComponent
                    .createPreconditionsComponent(module);
            preconditionsComponent.inject(lifecycle);
            return preconditionsComponent;
        })));

        produceMap.put(splashScreenKey.getPath(), new Pair<>(SplashScreen.class,
            params -> new SplashScreen((context, screen, module) -> {
                MultiActivityLifeCycleMeta.MultiActivityLifeCycleComponent lifeCycleComponent =
                        getMultiActivityLifeCycleComponent(context);
            SplashScreenComponent splashScreenComponent = lifeCycleComponent
                    .createSplashScreenComponent(module);
            splashScreenComponent.inject(screen);
            return splashScreenComponent;
        })));

        produceMap.put(homeScreenKey.getPath(), new Pair<>(HomeScreen.class,
            params -> new HomeScreen((context, screen, screenModule, parentView) -> {
                MultiActivityLifeCycleMeta.MultiActivityLifeCycleComponent lifeCycleComponent =
                        getMultiActivityLifeCycleComponent(context);
                HomeScreenComponent homeScreenComponent = lifeCycleComponent
                        .createHomeScreenComponent(screenModule, new ToolbarModule(parentView));
                homeScreenComponent.inject(screen);
                return homeScreenComponent;
        })));

        produceMap.put(collapsingRecyclerKey.getPath(), new Pair<>(CollapseRecyclerScreen.class,
            params -> new CollapseRecyclerScreen((context, screen, module) -> {
                MultiActivityLifeCycleMeta.MultiActivityLifeCycleComponent lifeCycleComponent =
                        getMultiActivityLifeCycleComponent(context);
                CollapsingRecyclerScreenComponent screenComponent = lifeCycleComponent
                        .createCollapsingRecyclerScreenComponent(module);
                screenComponent.inject(screen);
                return screenComponent;
            })));
    }

    @NonNull
    private MultiActivityLifeCycleMeta.MultiActivityLifeCycleComponent getMultiActivityLifeCycleComponent(
            @NonNull Context context) {
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
