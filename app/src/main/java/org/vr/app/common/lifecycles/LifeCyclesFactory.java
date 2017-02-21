package org.vr.app.common.lifecycles;

import android.support.annotation.NonNull;

import org.vr.cycle.LifeCycle;

/**
 * Created by vladimirrybkin on 02/02/2017.
 */
public interface LifeCyclesFactory {

    @NonNull
    LifeCycle create(@NonNull String path, Object... params);

    @NonNull
    Class<?> getClass(@NonNull String path);

}
