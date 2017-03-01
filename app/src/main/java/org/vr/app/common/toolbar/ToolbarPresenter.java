package org.vr.app.common.toolbar;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;

/**
 * Created by vladimirrybkin on 22/02/2017.
 */

public interface ToolbarPresenter {

    ToolbarPresenter enableHome(boolean enabled);

    ToolbarPresenter homeIndicator(@DrawableRes @Nullable Integer indicatorResId);

    ToolbarPresenter setTitle(@StringRes int titleResId);

    ToolbarPresenter setTitle(CharSequence title);

    @Nullable
    View findViewById(@IdRes int viewId);

    ToolbarPresenter replaceChildViews(@LayoutRes int layoutId);

    ToolbarPresenter inflateChildView(@LayoutRes int layoutId);

    ToolbarPresenter removeAllViews();

}
