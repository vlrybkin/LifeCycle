package org.vr.example.activities.several;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.jetbrains.annotations.NotNull;
import org.vr.cycle.LifeCycle;
import org.vr.cycle.LifecycleKey;
import org.vr.cycle.activity.LifeCycleActivity;
import org.vr.di.DiActivityModule;
import org.vr.di.DiContextWrapper;
import org.vr.di.DiNames;
import org.vr.example.ExampleApplicationMeta;
import org.vr.example.activities.several.router.ExampleActivityRouter;
import org.vr.router.command.RestartRoute;

/**
 * The main activites to display screens as activities.
 *
 * @author Vladimir Rybkin
 */
public abstract class ParentActivity extends LifeCycleActivity {

    private DiContextWrapper wrapper;

    @Override
    protected void attachBaseContext(@NonNull Context context) {
        wrapper = new DiContextWrapper(context, false);
        super.attachBaseContext(wrapper);
    }

    @NotNull
    @Override
    protected LifeCycle createLifeCycle() {
        return new ExampleActivityRouter(this, getInitialLifecycleKey());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null && getIntent().getExtras() != null) {
            savedInstanceState = getIntent().getExtras().getBundle(
                    RestartRoute.Companion.getSTATE_BACK_SAVED_STATE());
            getIntent().getExtras().remove(RestartRoute.Companion.getSTATE_BACK_SAVED_STATE());
        }
        createActivityComponent(wrapper, savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    protected void createActivityComponent(@NonNull DiContextWrapper wrapper,
                                           @Nullable Bundle savedInstanceState) {
        ExampleApplicationMeta.ExampleApplicationComponent appComponent = (ExampleApplicationMeta.ExampleApplicationComponent)
                getSystemService(DiNames.APPLICATION_COMPONENT);
        ActivityComponent activityComponent = appComponent.createActivityComponent(
                new DiActivityModule(this));
        wrapper.addComponent(DiNames.ACTIVITY_COMPONENT, activityComponent);
    }

    @NonNull
    protected String getInitialLifecycleKey() {
        LifecycleKey lifecycleKey = getClass().getAnnotation(LifecycleKey.class);
        if (lifecycleKey == null) {
            throw new RuntimeException("Lifecycle key is not defined");
        }

        return lifecycleKey.value();
    }

}
