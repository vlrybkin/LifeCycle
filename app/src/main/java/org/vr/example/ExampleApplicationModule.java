package org.vr.example;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vladimirrybkin on 01/11/16.
 */
@Module
public class ExampleApplicationModule {

    @NonNull
    private ExampleApplication application;

    public ExampleApplicationModule(@NonNull ExampleApplication application) {
        this.application = application;
    }

    @Provides
    public ExampleApplication provideApplication() {
        return application;
    }

}
