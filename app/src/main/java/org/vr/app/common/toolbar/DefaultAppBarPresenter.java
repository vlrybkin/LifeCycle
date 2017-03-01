package org.vr.app.common.toolbar;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

/**
 * Created by vladimirrybkin on 09/01/2017.
 */
public class DefaultAppBarPresenter implements AppBarPresenter {

    @NonNull
    private final AppCompatActivity activity;

    @Inject
    public DefaultAppBarPresenter(@NonNull AppCompatActivity activity) {
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
    public void enableHome(boolean enabled) {
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(enabled);
        }
    }

    @Override
    public void setHomeIndicator(@DrawableRes @Nullable Integer indicatorResId) {
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(indicatorResId != null ?
                    indicatorResId : 0);
        }
    }

    @Override
    public void setTitle(@StringRes int titleResId) {
        activity.setTitle(titleResId);
    }

    @Override
    public void setTitle(CharSequence title) {
        activity.setTitle(title);
    }

}
