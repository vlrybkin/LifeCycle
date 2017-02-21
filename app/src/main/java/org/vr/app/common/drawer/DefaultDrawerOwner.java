package org.vr.app.common.drawer;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;

import org.vr.app.R;

import java.util.LinkedList;

/**
 * Created by vladimirrybkin on 16/02/2017.
 */
public class DefaultDrawerOwner implements DrawerOwner, DrawerContract.View,
        NavigationView.OnNavigationItemSelectedListener {

    @NonNull
    private final DrawerLayout drawerLayout;
    @NonNull
    private final NavigationView navigationView;
    @NonNull
    private final DrawerContract.Presenter presenter;
    @NonNull
    private final ActionBarDrawerToggle drawerToggle;
    @NonNull
    private final LinkedList<DrawerListener> listeners = new LinkedList<>();

    public DefaultDrawerOwner(@NonNull Activity activity,
            @NonNull DrawerLayout drawerLayout,
            @NonNull DrawerContract.Presenter presenter) {
        this.drawerLayout = drawerLayout;
        navigationView = (NavigationView) drawerLayout.findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
        this.presenter = presenter;

        drawerToggle = new ActionBarDrawerToggle(activity, drawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                notifyOnOpen();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                notifyOnClose();
            }
        };

        // Set the drawer toggle as the DrawerListener
        drawerLayout.addDrawerListener(drawerToggle);
        presenter.attachView(this);
    }

    @Override
    public void open() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void close() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void lock() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void unlock() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    private void notifyOnOpen() {
        for (DrawerListener listener : listeners) {
            listener.onDrawerOpen();
        }
    }

    private void notifyOnClose() {
        for (DrawerListener listener : listeners) {
            listener.onDrawerClose();
        }
    }

    @Override
    public void registerListener(DrawerListener listener) {
        listeners.add(listener);
    }

    @Override
    public void unregisterListener(DrawerListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void selectItem(@IdRes int id) {
        navigationView.setCheckedItem(id);
    }

    @Override
    public boolean onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        presenter.onItemSelected(item.getItemId());
        return true;
    }

}
