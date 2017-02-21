package org.vr.app.common.drawer;

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

}
