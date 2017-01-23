package org.vr.example.activities.several.screen.home;

import android.support.annotation.NonNull;

import org.vr.cycle.LifeCycle;
import org.vr.example.activities.several.ParentActivity;
import org.vr.example.common.screens.home.HomeScreen;

/**
 * Created by vladimirrybkin on 09/11/16.
 */
public class SeveralActivitiesHome extends ParentActivity {

    @NonNull
    protected String getInitialLifecycleKey() {
        return LifeCycle.Companion.getKey(HomeScreen.class);
    }

}
