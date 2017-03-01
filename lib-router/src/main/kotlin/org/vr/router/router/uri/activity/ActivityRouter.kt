package org.vr.router.router.uri.activity

import android.app.Activity
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.net.Uri
import android.os.Bundle
import org.vr.router.route.transition.RouteTransition
import org.vr.router.router.base.Router

/**
 * Created by vladimirrybkin on 12/01/2017.
 */
open class ActivityRouter(val activity: Activity, val dataFactory: ActivityRouterLaunchDataFactory) : Router<Uri> {

    override var destroyed = false

    override fun push(key: Uri, persistantState: Bundle?, savedState: Bundle?,
                      inTransition: RouteTransition?, outTransition: RouteTransition?): Boolean {
        val launchData = dataFactory.getData(key.path, persistantState, savedState, inTransition, outTransition)
        activity.startActivityForResult(launchData.intent, launchData.requestCode)
        return true
    }

    override fun replaceTop(key: Uri, persistantState: Bundle?, savedState: Bundle?,
                            inTransition: RouteTransition?, outTransition: RouteTransition?): Boolean {
        push(key, persistantState, savedState, inTransition, outTransition)
        activity.finish()
        return true
    }

    override fun pop(result: Bundle?, outTransition: RouteTransition?): Boolean {
        activity.finish()
        return true
    }

    override fun restart(key: Uri, persistantState: Bundle?, savedState: Bundle?,
                         inTransition: RouteTransition?, outTransition: RouteTransition?): Boolean {
        val launchData = dataFactory.getData(key.path, persistantState, savedState, inTransition, outTransition)
        launchData.intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
        activity.startActivity(launchData.intent)
        return true
    }

    override fun save(out: Bundle) {
    }

}
