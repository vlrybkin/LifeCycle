package org.vr.app.activities.multi.lifecycles;

import org.vr.app.common.lifecycles.home.HomeScreen;
import org.vr.app.common.lifecycles.home.HomeScreenDI;
import org.vr.app.common.ui.ToolbarModule;

import dagger.Subcomponent;

/**
 * Created by vladimirrybkin on 28/01/2017.
 */
@HomeScreenDI.HomeScreenScope
@Subcomponent(modules = {HomeScreenDI.HomeScreenModule.class, ToolbarModule.class})
public interface HomeScreenComponent {

    void inject(HomeScreen homeScreen);

}
