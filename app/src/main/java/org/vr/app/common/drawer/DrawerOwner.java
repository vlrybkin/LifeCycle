package org.vr.app.common.drawer;

import android.content.res.Configuration;
import android.view.MenuItem;

/**
 * Created by vladimirrybkin on 16/02/2017.
 */

public interface DrawerOwner {

    void open();

    void close();

    void lock();

    void unlock();

    void registerListener(DrawerListener listener);

    void unregisterListener(DrawerListener listener);

    boolean onBackPressed();

    boolean onOptionsItemSelected(MenuItem item);

    void onConfigurationChanged(Configuration newConfig);

    void boundToToolbar(boolean bound);

}
