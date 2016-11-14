package org.vr.example.bootstrap;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vladimirrybkin on 01/11/16.
 */
@Module
public class BootstrapModule {

    @NonNull
    private Bootstrap bootstrap;

    public BootstrapModule(@NonNull Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    @Provides
    public Bootstrap provideBootstrap() {
        return bootstrap;
    }

}
