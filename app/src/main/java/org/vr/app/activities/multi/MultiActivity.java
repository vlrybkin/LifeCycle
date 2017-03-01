package org.vr.app.activities.multi;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.jetbrains.annotations.NotNull;
import org.vr.app.ExampleApplicationMeta;
import org.vr.app.common.routers.view.AppViewRouterMeta;
import org.vr.app.common.uri.Uris;
import org.vr.cycle.LifeCycle;
import org.vr.cycle.activity.LifeCycleActivity;
import org.vr.di.DiAppCompatActivityModule;
import org.vr.di.DiContextWrapper;
import org.vr.di.DiNames;
import org.vr.router.router.uri.activity.ActivityRouter;

/**
 * The main activites to display screens as activities.
 *
 * @author Vladimir Rybkin
 */
public class MultiActivity extends LifeCycleActivity {

    private DiContextWrapper wrapper;

    @Override
    protected void attachBaseContext(@NonNull Context context) {
        wrapper = new DiContextWrapper(context, false);
        super.attachBaseContext(wrapper);
    }

    @NotNull
    @Override
    protected LifeCycle createLifeCycle() {
        return new MultiActivityLifeCycle((context, source, module) -> {
            MultiActivityMeta.MultiActivityComponent activityComponent =
                (MultiActivityMeta.MultiActivityComponent) context.getSystemService(
                        DiNames.Companion.getACTIVITY_COMPONENT());
            MultiActivityLifeCycleMeta.MultiActivityLifeCycleComponent
                    lifeCycleComponent = activityComponent.createMultiActivityLifeCycleComponent(
                        module, new AppViewRouterMeta.AppViewRouterModule());
            wrapper.addComponent(MultiActivityLifeCycleMeta.MultiActivityLifeCycleComponent.name,
                    lifeCycleComponent);
            lifeCycleComponent.inject(source);
            return lifeCycleComponent;
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        createActivityComponent(wrapper, savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    private void createActivityComponent(@NonNull DiContextWrapper wrapper,
                                         @Nullable Bundle savedInstanceState) {
        ExampleApplicationMeta.ExampleApplicationComponent appComponent =
                (ExampleApplicationMeta.ExampleApplicationComponent)
                getSystemService(DiNames.Companion.getAPPLICATION_COMPONENT());

        MultiActivityMeta.MultiActivityComponent activityComponent = appComponent.createActivityComponent(
                new DiAppCompatActivityModule(this), new MultiActivityMeta.MultiActivityModule(savedInstanceState));
        wrapper.addComponent(DiNames.Companion.getACTIVITY_COMPONENT(), activityComponent);

        ActivityRouter activityRouter = activityComponent.provideActivityRouter();
        wrapper.addComponent(Uris.HOST_AUTHORITY_ACTIVITY_ROUTER, activityRouter);
    }

}
