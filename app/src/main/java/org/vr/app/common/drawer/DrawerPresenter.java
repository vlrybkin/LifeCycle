package org.vr.app.common.drawer;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;

import org.vr.app.R;
import org.vr.app.common.lifecycles.collapse_recycler.CollapseRecyclerScreenMeta;
import org.vr.app.common.lifecycles.home.HomeScreenDI;
import org.vr.router.route.base.Route;

import javax.inject.Inject;

/**
 * Created by vladimirrybkin on 17/02/2017.
 */
public class DrawerPresenter implements DrawerContract.Presenter {

    private DrawerContract.View view;

    private final Context context;

    private final Route homeRoute;
    private final Route collapseRecyclerRoute;

    @Inject
    public DrawerPresenter(Context context,
                           @HomeScreenDI.HomeScreenQualifier Route homeRoute,
                           @CollapseRecyclerScreenMeta.CollapseRecyclerScreenQualifier
                                       Route collapseRecyclerRoute) {
        this.context = context;
        this.homeRoute = homeRoute;
        this.collapseRecyclerRoute = collapseRecyclerRoute;
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
    public boolean onItemSelected(@IdRes int id) {
        switch (id) {
            case R.id.sidebar_home:
                homeRoute.go(context);
                break;

            case R.id.sidebar_collapse_recycler:
                collapseRecyclerRoute.go(context);
                break;
        }

        return false;
    }

    @Override
    public void highlightItem(@NonNull Uri enterKey) {
        if (view != null) {
            if (enterKey.getPath().equals(homeRoute.getKey().getPath())) {
                view.selectItem(R.id.sidebar_home);
            } else if (enterKey.getPath().equals(collapseRecyclerRoute.getKey().getPath())) {
                view.selectItem(R.id.sidebar_collapse_recycler);
            }
        }
    }

}
