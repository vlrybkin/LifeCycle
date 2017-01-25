package org.vr.app.common.screens.home;

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
    }

    @Scope
    public static @interface HomeScreenScope {
    }

    @Module
    public static class HomeScreenModule {

    }

}
