package org.vr.app.activities.multi.router.activity;

import android.content.Intent;
import android.net.Uri;

import org.vr.app.common.lifecycles.collapse_recycler.CollapseRecyclerScreenMeta;
import org.vr.app.common.lifecycles.home.HomeScreenDI;
import org.vr.app.common.uri.Uris;
import org.vr.app.common.lifecycles.splash.SplashScreenDI;
import org.vr.router.router.uri.activity.ActivityRouterLaunchData;
import org.vr.router.router.uri.activity.DefaultActivityRouterLaunchDataFactory;

import javax.inject.Inject;

/**
 * Created by vladimirrybkin on 24/01/2017.
 */

public class AppActivityRouterLaunchDataFactory extends DefaultActivityRouterLaunchDataFactory {

    @Inject
    public AppActivityRouterLaunchDataFactory(@SplashScreenDI.SplashScreenQualifier Uri splashScreenKey,
                                              @HomeScreenDI.HomeScreenQualifier Uri homeScreenKey,
                                              @CollapseRecyclerScreenMeta.CollapseRecyclerScreenQualifier Uri collapseRecyclerKey) {
        this.getMap().put(splashScreenKey.getPath(), new ActivityRouterLaunchData(new Intent(Intent.ACTION_VIEW,
                splashScreenKey.buildUpon().scheme(Uris.SCHEME).authority(Uris.HOST_AUTHORITY_VIEW_ROUTER).build()), -1));

        this.getMap().put(homeScreenKey.getPath(), new ActivityRouterLaunchData(new Intent(Intent.ACTION_VIEW,
                homeScreenKey.buildUpon().scheme(Uris.SCHEME).authority(Uris.HOST_AUTHORITY_VIEW_ROUTER).build()), -1));

        this.getMap().put(collapseRecyclerKey.getPath(), new ActivityRouterLaunchData(new Intent(Intent.ACTION_VIEW,
                collapseRecyclerKey.buildUpon().scheme(Uris.SCHEME).authority(Uris.HOST_AUTHORITY_VIEW_ROUTER).build()), -1));
    }

}
