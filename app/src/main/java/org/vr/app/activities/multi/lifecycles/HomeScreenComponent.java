package org.vr.app.activities.multi.lifecycles;

import org.vr.app.common.lifecycles.home.HomeScreen;
import org.vr.app.common.lifecycles.home.HomeScreenMeta;

import dagger.Subcomponent;

/**
 * Created by vladimirrybkin on 28/01/2017.
 */
@HomeScreenMeta.HomeScreenScope
@Subcomponent(modules = HomeScreenMeta.HomeScreenModule.class)
public interface HomeScreenComponent {

    void inject(HomeScreen homeScreen);

}
