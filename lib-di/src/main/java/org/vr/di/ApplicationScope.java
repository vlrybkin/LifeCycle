package org.vr.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Vladimir Kozhevnikov on 28/08/16.
 * Bounded with the application's lifecycle.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationScope {

}
