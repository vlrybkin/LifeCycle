package org.vr.app.common.drawer;

import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;

import org.vr.app.R;
import org.vr.app.common.lifecycles.home.HomeScreenMeta;

import javax.inject.Inject;

/**
 * Created by vladimirrybkin on 17/02/2017.
 */

public class DrawerPresenter implements DrawerContract.Presenter {

    private DrawerContract.View view;

    @HomeScreenMeta.HomeScreenQualifier
    private final Uri homeKey;

    @Inject
    public DrawerPresenter(@HomeScreenMeta.HomeScreenQualifier Uri homeKey) {
        this.homeKey = homeKey;
    }

    @Override
    public void attachView(@NonNull DrawerContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void onItemSelected(@IdRes int id) {

    }

    @Override
    public void highlightItem(@NonNull Uri enterKey) {
        if (view != null) {
            if (enterKey.getPath().equals(homeKey.toString())) {
                view.selectItem(R.id.sidebar_home);
            }
        }

    }

}
