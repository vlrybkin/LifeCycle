package org.vr.router.router.base

import android.os.Bundle
import org.vr.router.route.transition.RouteTransition

/**
 * Created by vladimirrybkin on 17/11/16.
 */
interface Router<in K> {

    fun push(key: K, persistantState: Bundle?, savedState: Bundle?,
             inTransition: RouteTransition?, outTransition: RouteTransition?) : Boolean

    fun pop(result: Bundle?, inTransition: RouteTransition?, outTransition: RouteTransition?) : Boolean

    fun replaceTop(key: K, persistantState: Bundle?, savedState: Bundle?,
                   inTransition: RouteTransition?, outTransition: RouteTransition?) : Boolean

    fun restart(key: K, persistantState: Bundle?, savedState: Bundle?,
                inTransition: RouteTransition?, outTransition: RouteTransition?) : Boolean

    fun save(out: Bundle)

    fun backPressed() : Boolean = false

}
