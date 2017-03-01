package org.vr.app.activities.multi;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import org.vr.app.R;
import org.vr.app.common.drawer.DrawerOwner;
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
    private final Injector injector;

    public MultiActivityLifeCycle(@NonNull Injector injector) {
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
        LayoutInflater.from(getContext()).inflate(R.layout.screen_root, containerView, true);

        ViewGroup navContainer = (ViewGroup) containerView.findViewById(R.id.navigation_container);
        LayoutInflater.from(getContext()).inflate(R.layout.left_nav, navContainer, true);

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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return drawerOwner.onOptionsItemSelected(item) ||
                router.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration configuration) {
        drawerOwner.onConfigurationChanged(configuration);
        router.onConfigurationChanged(configuration);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle out) {
        router.save(out);
    }

    @Override
    public boolean onBackPressed() {
        return drawerOwner.onBackPressed() || router.backPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        router.destroy();
    }

}
