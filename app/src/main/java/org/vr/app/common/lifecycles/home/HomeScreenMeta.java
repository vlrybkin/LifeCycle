package org.vr.app.common.lifecycles.home;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;
import javax.inject.Scope;

import dagger.Module;

/**
 * Created by vladimirrybkin on 17/01/2017.
 */

public class HomeScreenMeta {

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface HomeScreenQualifier {
        String value() default "";
    }

    @Scope
    public static @interface HomeScreenScope {
    }

    @Module
    public static class HomeScreenModule {

    }

}
