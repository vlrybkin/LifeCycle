package org.vr.app.common.toolbar;

import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

/**
 * Created by vladimirrybkin on 09/01/2017.
 */

public interface AppBarOwner {

    void visible(boolean value);

    void setView(@Nullable Toolbar toolbarView);

    void enableHomeAsUp(boolean enabled);

    void setHomeAsUpIndicator(@DrawableRes @Nullable Integer homeAsUpIndicatorResId);


}
