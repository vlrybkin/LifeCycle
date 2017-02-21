package org.vr.app.activities.multi;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.vr.app.R;
import org.vr.app.common.drawer.DrawerOwner;
import org.vr.app.common.lifecycles.Injector;
import org.vr.app.common.uri.Uris;
import org.vr.cycle.DefaultLifeCycle;
import org.vr.di.DiContextWrapper;
import org.vr.router.route.base.Route;
import org.vr.router.router.base.Router;

import javax.inject.Inject;

/**
 * Created by vladimirrybkin on 13/02/2017.
 */
public class MultiActivityLifeCycle extends DefaultLifeCycle {

    private DiContextWrapper wrapper;

    @MultiActivityMeta.InitialRoute
    @Inject
    Route initialRoute;

    @Inject
    Router router;

    @Inject
    DrawerOwner drawerOwner;

    @NonNull
    private final Injector<MultiActivityLifeCycle> injector;

    public MultiActivityLifeCycle(@NonNull Injector<MultiActivityLifeCycle> injector) {
        this.injector = injector;
    }

    @Override
    @NonNull
    public Context attachBaseContext(@NonNull Context context) {
        wrapper = new DiContextWrapper(context, false);
        return super.attachBaseContext(wrapper);
    }

    @Override
    public void onCreateView(@NonNull ViewGroup containerView,
                             @Nullable Bundle persistantState, @Nullable Bundle savedState) {
        LayoutInflater.from(getContext()).inflate(R.layout.root_left_nav, containerView, true);

        DrawerLayout drawerLayout = (DrawerLayout) containerView.findViewById(R.id.drawer_layout);
        ViewGroup screenContainer = (ViewGroup) containerView.findViewById(R.id.screen_container);

        MultiActivityLifeCycleMeta.MultiActivityLifeCycleMetaModule module =
                new MultiActivityLifeCycleMeta.MultiActivityLifeCycleMetaModule(wrapper,
                        screenContainer, drawerLayout);
        injector.inject(getContext(), this, module);
        wrapper.addComponent(Uris.HOST_AUTHORITY_VIEW_ROUTER, router);
        initialRoute.go(getContext());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle out) {
        router.save(out);
    }

    @Override
    public boolean onBackPressed() {
        if (drawerOwner.onBackPressed()) {
            return true;
        } else {
            return router.backPressed();
        }
    }

}
