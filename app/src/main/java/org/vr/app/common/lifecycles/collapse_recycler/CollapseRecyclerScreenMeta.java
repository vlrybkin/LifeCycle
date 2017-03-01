package org.vr.app.common.lifecycles.collapse_recycler;

import android.content.Context;
import android.support.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;
import javax.inject.Scope;

import dagger.Module;

/**
 * Created by vladimirrybkin on 17/01/2017.
 */
public class CollapseRecyclerScreenMeta {

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CollapseRecyclerScreenQualifier {
        String value() default "";
    }

    @Scope
    public @interface CollapseRecyclerScreenScope {
    }

    public interface Injector {

        @NonNull
        Object inject(Context context,
                      CollapseRecyclerScreen source,
                      CollapseRecyclerScreenModule module);

    }

    @Module
    public static class CollapseRecyclerScreenModule {


    }

}
