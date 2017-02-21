package org.vr.di

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity

import dagger.Module
import dagger.Provides

/**
 * Created by vladimirrybkin on 01/11/16.
 */
@Module
class DiAppCompatActivityModule(private val activity: AppCompatActivity) {

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

    @ActivityScope
    @Provides
    fun provideAppCompatActivity(): AppCompatActivity {
        return activity
    }

}
