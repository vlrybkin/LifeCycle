package org.vr.di

/**
 * Created by vladimirrybkin on 09/01/2017.
 */
interface DiComponentsStorage {

    fun addComponent(name : String, component : Any)

    fun findComponent(name : String) : Any?

}
