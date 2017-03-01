package org.vr.app.common.ui;

import android.support.annotation.IdRes;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.vr.app.R;
import org.vr.app.common.toolbar.AppBarPresenter;
import org.vr.app.common.toolbar.DefaultToolbarPresenter;
import org.vr.app.common.toolbar.ToolbarPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vladimirrybkin on 22/02/2017.
 */
@Module
public class ToolbarModule {

    private final Toolbar toolbar;

    private ToolbarPresenter toolbarOwner;

    public ToolbarModule(View parentView) {
        this(parentView, R.id.toolbar);
    }

    public ToolbarModule(View parentView, @IdRes int toolbarId) {
        toolbar = (Toolbar) parentView.findViewById(toolbarId);
        if (toolbar == null) {
            throw new RuntimeException("The toolbar is not found");
        }
    }

    @Provides
    public ToolbarPresenter provideToolbarOwner(AppBarPresenter appBarOwner) {
        if (toolbarOwner == null) {
            toolbarOwner = new DefaultToolbarPresenter(toolbar, appBarOwner);
        }

        return toolbarOwner;
    }

}
