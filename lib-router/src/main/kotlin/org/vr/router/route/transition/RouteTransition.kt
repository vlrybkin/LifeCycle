package org.vr.router.route.transition

import android.os.Bundle

/**
 * Created by vladimirrybkin on 14/02/2017.
 */
open class RouteTransition(val type: Int) {

    companion object {
        val TYPE = RouteTransition::class.java.simpleName + "#TYPE"

        val TYPE_FADEIN = 1
    }

    constructor(inState: Bundle) : this(inState.getInt(TYPE)) {
    }

    fun toBundle(out: Bundle) : Bundle {
        out.putInt(TYPE, type)
        return out
    }

}
