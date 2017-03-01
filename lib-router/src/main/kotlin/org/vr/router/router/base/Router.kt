package org.vr.router.router.base

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import org.vr.router.route.transition.RouteTransition

/**
 * Created by vladimirrybkin on 17/11/16.
 */
interface Router<in K> {

    var destroyed : Boolean

    fun push(key: K, persistantState: Bundle?, savedState: Bundle?,
             inTransition: RouteTransition?, outTransition: RouteTransition?) : Boolean

    fun pop(result: Bundle?, outTransition: RouteTransition?) : Boolean

    fun replaceTop(key: K, persistantState: Bundle?, savedState: Bundle?,
                   inTransition: RouteTransition?, outTransition: RouteTransition?) : Boolean

    fun restart(key: K, persistantState: Bundle?, savedState: Bundle?,
                inTransition: RouteTransition?, outTransition: RouteTransition?) : Boolean

    fun save(out: Bundle)

    fun backPressed() : Boolean = false

    fun onOptionsItemSelected(item: MenuItem) : Boolean = false

    fun onConfigurationChanged(configuration: Configuration) {}

    fun destroy() { destroyed = true }

}
