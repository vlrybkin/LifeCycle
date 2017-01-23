package org.vr.example.activities.several.screen.splash;

import android.support.annotation.NonNull;

import org.vr.cycle.LifeCycle;
import org.vr.example.activities.several.ParentActivity;
import org.vr.example.common.screens.splash.SplashScreen;

/**
 * Created by vladimirrybkin on 07/12/16.
 */
public class SplashActivity extends ParentActivity {

    @NonNull
    protected String getInitialLifecycleKey() {
        return LifeCycle.Companion.getKey(SplashScreen.class);
    }

}
