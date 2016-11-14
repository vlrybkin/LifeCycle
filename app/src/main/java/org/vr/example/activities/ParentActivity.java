package org.vr.example.activities;

import android.content.Context;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;
import org.vr.cycle.LifeCycle;
import org.vr.cycle.activity.LifeCycleActivity;
import org.vr.di.DiContextWrapper;
import org.vr.di.DiNames;
import org.vr.example.ExampleApplicationComponent;
import org.vr.example.R;
import org.vr.example.screens.SplashScreen;

/**
 * The main activites to display screens as activities.
 *
 * @author Vladimir Rybkin
 */
public class ParentActivity extends LifeCycleActivity {

    @Override
    protected void attachBaseContext(@NonNull Context context) {
        DiContextWrapper wrapper = new DiContextWrapper(context);

        //noinspection WrongConstant
        Context appContext = context.getApplicationContext();
        ExampleApplicationComponent appComponent =
                (ExampleApplicationComponent) appContext.getSystemService(DiNames.APPLICATION_COMPONENT);

        appComponent.getActivityComponent(new ActivityModule(this));
        wrapper.addComponent(DiNames.ACTIVITY_COMPONENT, appComponent);
        super.attachBaseContext(wrapper);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.screen_main);
    }

    @NotNull
    @Override
    protected LifeCycle createLifeCycle() {
        return new SplashScreen();
    }

}
