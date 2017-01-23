package org.vr.router.command

import android.os.Bundle
import org.vr.router.Router

/**
 * Created by vladimirrybkin on 12/01/2017.
 */
open class DefaultRoute(val router: Router, override val key: String,
                        override var action: Route.Action = Route.Action.ONTOP) : Route {

    override var state: Bundle? = null

    final override fun state(state: Bundle): Route {
        return super.state(state)
    }

    final override fun action(action: Route.Action): Route {
        return super.action(action)
    }

    override fun go() {
        when(action) {
            Route.Action.ONTOP -> router.push(key, state)
            Route.Action.REPLACE_TOP -> router.replaceTop(key, state)
            Route.Action.CLEARSTACK -> router.restart(key, state)
        }
    }

}
