package org.vr.router.activity

import android.os.Bundle
import java.util.*

/**
 * Created by vladimirrybkin on 24/01/2017.
 */
open class DefaultActivityRouterLaunchDataFactory : ActivityRouterLaunchDataFactory {

    protected val map = HashMap<String, ActivityRouterLaunchData>()

    override fun getData(key: String, state: Bundle?): ActivityRouterLaunchData {
        val data = map[key] ?: throw IllegalArgumentException("launch data not found for the key=" + key)

        if (state != null) {
            data.intent.replaceExtras(state)
        }

        return data
    }

}
