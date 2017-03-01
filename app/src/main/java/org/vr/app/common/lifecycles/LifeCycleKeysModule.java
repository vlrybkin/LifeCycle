package org.vr.app.common.lifecycles;

import android.net.Uri;

import org.vr.app.common.lifecycles.collapse_recycler.CollapseRecyclerScreen;
import org.vr.app.common.lifecycles.collapse_recycler.CollapseRecyclerScreenMeta;
import org.vr.app.common.lifecycles.home.HomeScreen;
import org.vr.app.common.lifecycles.home.HomeScreenDI;
import org.vr.app.common.lifecycles.preconditions.PreconditionsLifeCycle;
import org.vr.app.common.lifecycles.preconditions.PreconditionsDI;
import org.vr.app.common.lifecycles.splash.SplashScreen;
import org.vr.app.common.lifecycles.splash.SplashScreenDI;
import org.vr.cycle.LifeCycle;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vladimirrybkin on 28/11/16.
 */
@Module
public class LifeCycleKeysModule {

    @PreconditionsDI.PreconditionsQualifier
    @Provides
    public Uri providePreconditionsKey() {
        return Uri.parse(LifeCycle.Companion.getKey(PreconditionsLifeCycle.class));
    }

    @SplashScreenDI.SplashScreenQualifier
    @Provides
    public Uri provideSplashScreenKey() {
        return Uri.parse(LifeCycle.Companion.getKey(SplashScreen.class));
    }

    @HomeScreenDI.HomeScreenQualifier
    @Provides
    public Uri provideHomeScreenKey() {
        return Uri.parse(LifeCycle.Companion.getKey(HomeScreen.class));
    }

    @CollapseRecyclerScreenMeta.CollapseRecyclerScreenQualifier
    @Provides
    public Uri provideCollapseRecyclerScreenKey() {
        return Uri.parse(LifeCycle.Companion.getKey(CollapseRecyclerScreen.class));
    }

}
