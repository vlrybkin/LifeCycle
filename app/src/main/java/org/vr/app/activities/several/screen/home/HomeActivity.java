package org.vr.app.activities.several.screen.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.vr.app.activities.several.ParentActivity;
import org.vr.app.activities.several.screen.splash.DaggerSplashActivityMeta_SplashActivityComponent;
import org.vr.app.activities.several.screen.splash.SplashActivityMeta;
import org.vr.di.DiContextWrapper;

/**
 * Created by vladimirrybkin on 09/11/16.
 */
public class HomeActivity extends ParentActivity {

    @Override
    protected void createActivityComponentAndInject(@NonNull DiContextWrapper wrapper,
                                                    @Nullable Bundle savedInstanceState) {
        HomeActivityMeta.HomeActivityComponent activityComponent =
                DaggerHomeActivityMeta_HomeActivityComponent.builder()
                        .homeActivityModule(new HomeActivityMeta.HomeActivityModule()).build();
        activityComponent.inject(this);
    }

}
