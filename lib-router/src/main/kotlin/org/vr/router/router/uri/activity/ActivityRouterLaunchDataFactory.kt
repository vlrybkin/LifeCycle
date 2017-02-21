package org.vr.router.router.uri.activity

import android.os.Bundle
import org.vr.router.route.transition.RouteTransition

/**
 * Created by vladimirrybkin on 24/01/2017.
 */
interface ActivityRouterLaunchDataFactory {

    fun getData(path: String, persistantState: Bundle?, savedState: Bundle?,
                inTransition: RouteTransition?, outTransition: RouteTransition?) : ActivityRouterLaunchData

}
