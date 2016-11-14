package org.vr.example.activities;

import android.app.Activity;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vladimirrybkin on 01/11/16.
 */
@Module
public class ActivityModule {

    @NonNull
    private Activity activity;

    public ActivityModule(@NonNull Activity activity) {
        this.activity = activity;
    }

    @Provides
    public Activity provideActivity() {
        return activity;
    }

}
