package org.vr.app.common.toolbar;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

/**
 * Created by vladimirrybkin on 09/01/2017.
 */

public class DefaultToolbarOwner implements ToolbarOwner {

    @NonNull
    private final LinearLayout container;

    public DefaultToolbarOwner(@NonNull LinearLayout container) {
        this.container = container;
    }

    @Override
    public void setView(@IdRes int toolbarViewId) {

    }

    @Override
    public void enableHomeAsUp(boolean enabled) {

    }

    @Override
    public void setHomeAsUpIndicator(@DrawableRes @Nullable Integer homeAsUpIndicatorResId) {

    }


}
