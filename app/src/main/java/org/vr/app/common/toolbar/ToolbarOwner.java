package org.vr.app.common.toolbar;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;

/**
 * Created by vladimirrybkin on 09/01/2017.
 */

public interface ToolbarOwner {

    void setView(@IdRes int toolbarViewId);

    void enableHomeAsUp(boolean enabled);

    void setHomeAsUpIndicator(@DrawableRes @Nullable Integer homeAsUpIndicatorResId);


}
