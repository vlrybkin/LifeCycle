package org.vr.router.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import org.vr.router.LifecycleRouter

/**
 * Created by vladimirrybkin on 12/01/2017.
 */
abstract class ActivityRouter(val activity: Activity) : LifecycleRouter() {

    class LaunchData(val intent: Intent, val requestCode: Int = -1)

    override fun push(key: String, state: Bundle?) {
        val launchData = resolveLaunch(key, state)
        activity.startActivityForResult(launchData.intent, launchData.requestCode)
    }

    protected abstract fun resolveLaunch(key: String, state: Bundle?): LaunchData

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

}
