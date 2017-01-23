package org.vr.di

import android.app.Application

import dagger.Module
import dagger.Provides

/**
 * Created by vladimirrybkin on 01/11/16.
 */
@Module
class DiApplicationModule(private val application: Application) {

    @ApplicationScope
    @Provides
    fun provideApplication(): Application {
        return application
    }

}
