package org.vr.app.bootstrap;

import android.support.annotation.NonNull;

/**
 * Created by vladimirrybkin on 20/01/2017.
 */

public interface BootstrapConsumer {

    boolean needBootstrapUpdate();

    void setup(@NonNull Bootstrap bootstrap);

    void clear();

}
