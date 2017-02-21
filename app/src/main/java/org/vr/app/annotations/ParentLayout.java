package org.vr.app.annotations;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by vladimirrybkin on 17/01/2017.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ParentLayout {
    @LayoutRes int layoutId();
    @IdRes int rootViewId() default 0;
}
