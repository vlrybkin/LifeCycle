package org.vr.app.activities.multi.lifecycles;

import org.vr.app.common.lifecycles.splash.SplashScreen;
import org.vr.app.common.lifecycles.splash.SplashScreenMeta;

import dagger.Subcomponent;

/**
 * Created by vladimirrybkin on 28/01/2017.
 */
@SplashScreenMeta.SplashScreenScope
@Subcomponent(modules = SplashScreenMeta.SplashScreenModule.class)
public interface SplashScreenComponent {

    void inject(SplashScreen splashScreen);

}
