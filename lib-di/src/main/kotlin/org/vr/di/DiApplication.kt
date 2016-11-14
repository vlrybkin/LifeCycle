package org.vr.di

import android.app.Application
import android.content.Context

/**
 * Created by vladimirrybkin on 01/11/16.
 */
abstract class DiApplication : Application() {

    private var contextWrapper: DiContextWrapper? = null

    abstract fun addApplicationComponents(contextWrapper : DiContextWrapper) : Unit

    override fun attachBaseContext(base : Context) {
        contextWrapper = DiContextWrapper(base)
        addApplicationComponents(contextWrapper!!)
        super.attachBaseContext(contextWrapper)
    }

    fun getComponent(name : String) : Any? = contextWrapper?.findComponent(name)

}