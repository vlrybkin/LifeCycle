package org.vr.di

import dagger.Module
import dagger.Provides
import org.vr.router.Router

/**
 * Created by vladimirrybkin on 01/11/16.
 */
@Module
class DiRouterModule(private val router: Router) {

    @Provides
    fun provideRouter(): Router {
        return router
    }

}
