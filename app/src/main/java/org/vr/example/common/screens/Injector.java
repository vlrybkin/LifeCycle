package org.vr.example.common.screens;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by vladimirrybkin on 19/01/2017.
 */

public interface Injector<S> {

    @NonNull
    Object inject(Context context, S screen, Object... deps);

}
