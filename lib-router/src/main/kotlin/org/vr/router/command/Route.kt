package org.vr.router.command

import android.os.Bundle

/**
 * Created by vladimirrybkin on 24/11/16.
 */
interface Route {

    enum class Action {
        ONTOP, REPLACE_TOP, CLEARSTACK
    }

    val key: String
    var action: Action
    var state: Bundle?

    fun action(action: Action) : Route {
        this.action = action
        return this
    }

    fun state(state: Bundle) : Route {
        this.state = state
        return this
    }

    fun go()

}
