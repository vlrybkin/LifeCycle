package org.vr.app.common.lifecycles;

import android.net.Uri;

import org.vr.app.common.lifecycles.home.HomeScreen;
import org.vr.app.common.lifecycles.home.HomeScreenMeta;
import org.vr.app.common.lifecycles.preconditions.PreconditionsLifeCycle;
import org.vr.app.common.lifecycles.preconditions.PreconditionsMeta;
import org.vr.app.common.lifecycles.splash.SplashScreen;
import org.vr.app.common.lifecycles.splash.SplashScreenMeta;
import org.vr.cycle.LifeCycle;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vladimirrybkin on 28/11/16.
 */
@Module
public class LifeCycleKeysModule {

    @PreconditionsMeta.PreconditionsQualifier
    @Provides
    public Uri providePreconditionsKey() {
        return Uri.parse(LifeCycle.Companion.getKey(PreconditionsLifeCycle.class));
    }

    @SplashScreenMeta.SplashScreenQualifier
    @Provides
    public Uri provideSplashScreenKey() {
        return Uri.parse(LifeCycle.Companion.getKey(SplashScreen.class));
    }

    @HomeScreenMeta.HomeScreenQualifier
    @Provides
    public Uri provideHomeScreenKey() {
        return Uri.parse(LifeCycle.Companion.getKey(HomeScreen.class));
    }

}
