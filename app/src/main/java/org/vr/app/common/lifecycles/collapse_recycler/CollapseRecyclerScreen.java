package org.vr.app.common.lifecycles.collapse_recycler;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import org.vr.app.R;
import org.vr.app.annotations.DrawerEnabled;
import org.vr.app.annotations.ParentLayout;
import org.vr.cycle.DefaultLifeCycle;
import org.vr.cycle.LifeCycleKey;

/**
 * Created by vladimirrybkin on 21/02/2017.
 */
@LifeCycleKey(value = "/screen/collapse_recycler")
@ParentLayout(layoutId = R.layout.screen_collapse_recycler)
@DrawerEnabled(required = true)
public class CollapseRecyclerScreen extends DefaultLifeCycle {

    @NonNull
    private final CollapseRecyclerScreenMeta.Injector injector;

    public CollapseRecyclerScreen(@NonNull CollapseRecyclerScreenMeta.Injector injector) {
        this.injector = injector;
    }

    @Override
    public void onCreateView(@NonNull ViewGroup parentView, @Nullable Bundle persistantState,
                             @Nullable Bundle savedState) {
        injector.inject(getContext(), this, new CollapseRecyclerScreenMeta.CollapseRecyclerScreenModule());
    }

}
