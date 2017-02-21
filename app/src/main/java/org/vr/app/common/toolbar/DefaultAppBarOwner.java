package org.vr.app.common.toolbar;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

/**
 * Created by vladimirrybkin on 09/01/2017.
 */
public class DefaultAppBarOwner implements AppBarOwner {

    @NonNull
    private final AppCompatActivity activity;

    @Inject
    public DefaultAppBarOwner(@NonNull AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void visible(boolean value) {
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            if (value) {
                actionBar.show();
            } else {
                actionBar.hide();
            }
        }
    }

    @Override
    public void setView(@Nullable Toolbar toolbarView) {
        activity.setSupportActionBar(toolbarView);
    }

    @Override
    public void enableHomeAsUp(boolean enabled) {

    }

    @Override
    public void setHomeAsUpIndicator(@DrawableRes @Nullable Integer homeAsUpIndicatorResId) {

    }


}
