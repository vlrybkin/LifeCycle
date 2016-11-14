package org.vr.di

import android.content.Context
import android.content.ContextWrapper
import java.util.*

/**
 * Created by vladimirrybkin on 01/11/16.
 */
open class DiContextWrapper(base : Context) : ContextWrapper(base) {

    var components : HashMap<String, Any> = HashMap()

    override fun getSystemService(name : String) : Any? {
        return components[name] ?: super.getSystemService(name)
    }

    fun addComponent(name : String, component : Any) {
        components.put(name, component)
    }

    fun removeComponent(name : String) {
        components.remove(name)
    }

    fun findComponent(name : String) : Any? = components[name]

}