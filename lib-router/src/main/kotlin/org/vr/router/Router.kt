package org.vr.router

import android.os.Bundle

/**
 * Created by vladimirrybkin on 17/11/16.
 */
interface Router {

    fun push(key: String, state: Bundle?)

    fun pop()

    fun replaceTop(key: String, state: Bundle?)

    fun restart(key: String, state: Bundle?)

    fun save(out: Bundle)

}
