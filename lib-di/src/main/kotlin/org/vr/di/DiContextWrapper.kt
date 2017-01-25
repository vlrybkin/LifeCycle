package org.vr.di

import android.content.Context
import android.content.ContextWrapper
import java.util.*

/**
 * Created by vladimirrybkin on 01/11/16.
 */
class DiContextWrapper(base : Context, val root : Boolean = false) : ContextWrapper(base),
        DiComponentsStorage {

    var components : HashMap<String, Any> = HashMap()

    override fun getSystemService(name : String) : Any? {
        return components[name] ?: super.getSystemService(name) ?: getFromApplicationContext(name)
    }

    fun getFromApplicationContext(name: String) : Any? =
        if (root) null else applicationContext.getSystemService(name)

    override fun addComponent(name : String, component : Any) {
        components.put(name, component)
    }

    override fun findComponent(name : String) : Any? = components[name]

}
