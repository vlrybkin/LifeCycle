package org.vr.app.activities.multi;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by vladimirrybkin on 19/01/2017.
 */
public interface Injector {

    @NonNull
    Object inject(Context context,
                  MultiActivityLifeCycle source,
                  MultiActivityLifeCycleMeta.MultiActivityLifeCycleMetaModule module);

}
