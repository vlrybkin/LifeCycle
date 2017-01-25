package org.vr.app.activities.several.router;

import android.app.Activity;
import android.content.Intent;

import org.vr.app.activities.several.screen.home.HomeActivity;
import org.vr.app.activities.several.screen.splash.SplashActivity;
import org.vr.app.common.screens.home.HomeScreenMeta;
import org.vr.app.common.screens.splash.SplashScreenMeta;
import org.vr.router.activity.ActivityRouterLaunchData;
import org.vr.router.activity.DefaultActivityRouterLaunchDataFactory;

import javax.inject.Inject;

/**
 * Created by vladimirrybkin on 24/01/2017.
 */

public class ExampleActivityRouterLaunchDataFactory extends DefaultActivityRouterLaunchDataFactory {

    @Inject
    public ExampleActivityRouterLaunchDataFactory(Activity activity,
              @SplashScreenMeta.SplashScreenQualifier String splashScreenKey,
              @HomeScreenMeta.HomeScreenQualifier String homeScreenKey) {
        this.getMap().put(splashScreenKey, new ActivityRouterLaunchData(new Intent(activity, SplashActivity.class), 1));
        this.getMap().put(homeScreenKey, new ActivityRouterLaunchData(new Intent(activity, HomeActivity.class), -1));
    }

}
