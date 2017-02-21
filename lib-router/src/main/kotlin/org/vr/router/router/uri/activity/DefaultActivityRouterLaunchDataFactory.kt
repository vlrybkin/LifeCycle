package org.vr.router.router.uri.activity

import android.os.Bundle
import org.vr.router.route.transition.RouteTransition
import java.util.*

/**
 * Created by vladimirrybkin on 24/01/2017.
 */
open class DefaultActivityRouterLaunchDataFactory : ActivityRouterLaunchDataFactory {

    companion object {
        val PERSISTANT_STATE = DefaultActivityRouterLaunchDataFactory::class.java.simpleName + "#PERSISTANT_STATE"
        val SAVED_STATE = DefaultActivityRouterLaunchDataFactory::class.java.simpleName + "#SAVED_STATE"
        val IN_TRANSITION = DefaultActivityRouterLaunchDataFactory::class.java.simpleName + "#IN_TRANSITION"
        val OUT_TRANSITION = DefaultActivityRouterLaunchDataFactory::class.java.simpleName + "#OUT_TRANSITION"
    }

    protected val map = HashMap<String, ActivityRouterLaunchData>()

    override fun getData(path: String, persistantState: Bundle?, savedState: Bundle?,
                         inTransition: RouteTransition?, outTransition: RouteTransition?): ActivityRouterLaunchData {
        val data = map[path] ?: throw IllegalArgumentException("launch data not found for the path=" +
                path)

        data.intent.putExtra(PERSISTANT_STATE, persistantState)
        data.intent.putExtra(SAVED_STATE, savedState)
        data.intent.putExtra(IN_TRANSITION, inTransition?.toBundle(Bundle()))
        data.intent.putExtra(OUT_TRANSITION, outTransition?.toBundle(Bundle()))
        return data
    }

}
