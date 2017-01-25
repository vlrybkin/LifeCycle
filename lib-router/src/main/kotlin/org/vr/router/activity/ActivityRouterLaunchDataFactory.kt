package org.vr.router.activity

import android.os.Bundle

/**
 * Created by vladimirrybkin on 24/01/2017.
 */
interface ActivityRouterLaunchDataFactory {

    fun getData(key: String, state: Bundle?) : ActivityRouterLaunchData

}
