package org.vr.app.activities.multi.lifecycles;

import org.vr.app.common.lifecycles.collapse_recycler.CollapseRecyclerScreen;
import org.vr.app.common.lifecycles.collapse_recycler.CollapseRecyclerScreenMeta;

import dagger.Subcomponent;

/**
 * Created by vladimirrybkin on 28/01/2017.
 */
@CollapseRecyclerScreenMeta.CollapseRecyclerScreenScope
@Subcomponent(modules = CollapseRecyclerScreenMeta.CollapseRecyclerScreenModule.class)
public interface CollapsingRecyclerScreenComponent {

    void inject(CollapseRecyclerScreen screen);

}
