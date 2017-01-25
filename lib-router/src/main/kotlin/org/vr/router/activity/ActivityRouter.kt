package org.vr.router.activity

import android.app.Activity
import android.os.Bundle
import org.vr.router.Router

/**
 * Created by vladimirrybkin on 12/01/2017.
 */
open class ActivityRouter(val activity: Activity, val dataFactory: ActivityRouterLaunchDataFactory) : Router {

    override fun push(key: String, state: Bundle?) {
        val launchData = dataFactory.getData(key, state)
        activity.startActivityForResult(launchData.intent, launchData.requestCode)
    }

    override fun replaceTop(key: String, state: Bundle?) {
        restart(key, state)
    }

    override fun pop() {
        activity.finish()
    }

    override fun restart(key: String, state: Bundle?) {
        push(key, state)
        activity.finish()
    }

    override fun save(out: Bundle) {
    }

}
