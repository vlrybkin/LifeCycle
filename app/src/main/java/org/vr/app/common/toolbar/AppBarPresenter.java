package org.vr.app.common.toolbar;

import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;

/**
 * Created by vladimirrybkin on 09/01/2017.
 */

public interface AppBarPresenter {

    void visible(boolean value);

    void setView(@Nullable Toolbar toolbarView);

    void enableHome(boolean enabled);

    void setHomeIndicator(@DrawableRes @Nullable Integer indicatorResId);

    void setTitle(@StringRes int titleResId);

    void setTitle(CharSequence title);

}
