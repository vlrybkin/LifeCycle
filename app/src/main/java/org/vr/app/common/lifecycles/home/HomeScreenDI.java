package org.vr.app.common.lifecycles.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;
import javax.inject.Scope;

import dagger.Module;

/**
 * Created by vladimirrybkin on 17/01/2017.
 */

public class HomeScreenDI {

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface HomeScreenQualifier {
        String value() default "";
    }

    public interface Injector {

        @NonNull
        Object inject(Context context,
                      HomeScreen source,
                      HomeScreenModule module,
                      View parentView);

    }

    @Scope
    public @interface HomeScreenScope {
    }

    @Module
    public static class HomeScreenModule {

    }

}
