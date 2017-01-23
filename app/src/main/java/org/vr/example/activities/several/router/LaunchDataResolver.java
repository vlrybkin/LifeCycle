package org.vr.example.activities.several.router;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.vr.example.activities.several.screen.home.SeveralActivitiesHome;
import org.vr.example.activities.several.screen.splash.SplashActivity;
import org.vr.example.common.screens.home.HomeScreenMeta;
import org.vr.example.common.screens.splash.SplashScreenMeta;
import org.vr.router.activity.ActivityRouter;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by vladimirrybkin on 18/01/2017.
 */
public class LaunchDataResolver extends HashMap<String, ActivityRouter.LaunchData> {

    @Inject
    public LaunchDataResolver(Activity activity,
                              @SplashScreenMeta.SplashScreenQualifier String splashScreenKey,
                              @HomeScreenMeta.HomeScreenQualifier String homeScreenKey) {
        put(splashScreenKey, new ActivityRouter.LaunchData(new Intent(activity, SplashActivity.class), 1));
        put(homeScreenKey, new ActivityRouter.LaunchData(new Intent(activity, SeveralActivitiesHome.class), -1));
    }

    @NonNull
    public ActivityRouter.LaunchData resolve(String key, @Nullable Bundle state) {
        ActivityRouter.LaunchData launchData = get(key);

        if (state != null) {
            launchData.getIntent().replaceExtras(state);
        }

        return launchData;
    }

}
