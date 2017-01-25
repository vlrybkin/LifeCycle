package org.vr.app.activities.several.screen.splash;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.vr.app.activities.several.ParentActivity;
import org.vr.di.DiContextWrapper;

/**
 * Created by vladimirrybkin on 07/12/16.
 */
public class SplashActivity extends ParentActivity {

    @Override
    protected void createActivityComponentAndInject(@NonNull DiContextWrapper wrapper,
            @Nullable Bundle savedInstanceState) {

        SplashActivityMeta.SplashActivityComponent activityComponent =
                DaggerSplashActivityMeta_SplashActivityComponent.builder()
                        .splashActivityModule(new SplashActivityMeta.SplashActivityModule()).build();
        activityComponent.inject(this);
    }

}
