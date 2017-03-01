package org.vr.app.common.toolbar;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by vladimirrybkin on 22/02/2017.
 */
public class DefaultToolbarPresenter implements ToolbarPresenter {

    private final Toolbar toolbar;
    private final AppBarPresenter appBarOwner;

    public DefaultToolbarPresenter(@NonNull Toolbar toolbar,
                                   @NonNull AppBarPresenter appBarOwner) {
        this.toolbar = toolbar;
        this.appBarOwner = appBarOwner;
        appBarOwner.setView(toolbar);
    }

    @Override
    public ToolbarPresenter enableHome(boolean enabled) {
        appBarOwner.enableHome(enabled);
        return this;
    }

    @Override
    public ToolbarPresenter homeIndicator(@DrawableRes @Nullable Integer indicatorResId) {
        appBarOwner.setHomeIndicator(indicatorResId);
        return this;
    }

    @Override
    public ToolbarPresenter setTitle(@StringRes int titleResId) {
        appBarOwner.setTitle(titleResId);
        return this;
    }

    @Override
    public ToolbarPresenter setTitle(CharSequence title) {
        appBarOwner.setTitle(title);
        return this;
    }

    @Nullable
    @Override
    public View findViewById(@IdRes int viewId) {
        return toolbar.findViewById(viewId);
    }

    @Override
    public ToolbarPresenter replaceChildViews(@LayoutRes int layoutId) {
        removeAllViews();
        inflateChildView(layoutId);
        return this;
    }

    @Override
    public ToolbarPresenter inflateChildView(@LayoutRes int layoutId) {
        LayoutInflater.from(toolbar.getContext()).inflate(layoutId, toolbar, true);
        return this;
    }

    @Override
    public ToolbarPresenter removeAllViews() {
        toolbar.removeAllViews();
        return this;
    }
}
