package org.vr.app.activities.several;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.jetbrains.annotations.NotNull;
import org.vr.app.ExampleApplicationMeta;
import org.vr.cycle.LifeCycle;
import org.vr.cycle.activity.LifeCycleActivity;
import org.vr.di.DiActivityModule;
import org.vr.di.DiContextWrapper;
import org.vr.di.DiNames;
import org.vr.router.command.RestartRoute;

import javax.inject.Inject;

/**
 * The main activites to display screens as activities.
 *
 * @author Vladimir Rybkin
 */
public abstract class ParentActivity extends LifeCycleActivity {

    private DiContextWrapper wrapper;

    @Inject
    protected LifeCycle lifecycle;

    @Override
    protected void attachBaseContext(@NonNull Context context) {
        wrapper = new DiContextWrapper(context, false);
        super.attachBaseContext(wrapper);
    }

    @NotNull
    @Override
    protected LifeCycle createLifeCycle() {
        return lifecycle;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null && getIntent().getExtras() != null) {
            savedInstanceState = getIntent().getExtras().getBundle(
                    RestartRoute.Companion.getSTATE_BACK_SAVED_STATE());
            getIntent().getExtras().remove(RestartRoute.Companion.getSTATE_BACK_SAVED_STATE());
        }

        createParentActivityComponent(wrapper);
        createActivityComponentAndInject(wrapper, savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    private void createParentActivityComponent(@NonNull DiContextWrapper wrapper) {
        ExampleApplicationMeta.ExampleApplicationComponent appComponent = (ExampleApplicationMeta.ExampleApplicationComponent)
                getSystemService(DiNames.Companion.getAPPLICATION_COMPONENT());
        ParentActivityComponent activityComponent = appComponent.createParentActivityComponent(
                new DiActivityModule(this));
        wrapper.addComponent(DiNames.Companion.getACTIVITY_COMPONENT(), activityComponent);
    }

    protected abstract void createActivityComponentAndInject(@NonNull DiContextWrapper wrapper,
                                           @Nullable Bundle savedInstanceState);

}
