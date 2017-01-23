package org.vr.di

import android.app.Application
import android.content.Context

/**
 * Created by vladimirrybkin on 01/11/16.
 */
abstract class DiApplication : Application(), DiComponentsStorage {

    private lateinit var contextWrapper: DiContextWrapper

    protected abstract fun onAddApplicationComponents() : Unit

    override fun attachBaseContext(base : Context) {
        contextWrapper = DiContextWrapper(base, true)
        onAddApplicationComponents()
        super.attachBaseContext(contextWrapper)
    }

    override fun addComponent(name : String, component : Any ) = contextWrapper.addComponent(name, component)

    override fun findComponent(name : String) : Any? = contextWrapper.findComponent(name)

}
