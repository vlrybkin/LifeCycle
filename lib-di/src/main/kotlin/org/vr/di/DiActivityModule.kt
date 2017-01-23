package org.vr.di

import android.app.Activity
import android.content.Context

import dagger.Module
import dagger.Provides

/**
 * Created by vladimirrybkin on 01/11/16.
 */
@Module
class DiActivityModule(private val activity: Activity) {

    @ActivityScope
    @Provides
    fun provideContext(): Context {
        return activity
    }

    @ActivityScope
    @Provides
    fun provideActivity(): Activity {
        return activity
    }

}
