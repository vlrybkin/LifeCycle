package org.vr.app.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by vladimirrybkin on 17/01/2017.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface BootstrapRequired {
    boolean required() default true;
}
