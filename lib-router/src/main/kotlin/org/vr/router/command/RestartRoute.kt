package org.vr.router.command

import android.os.Bundle
import org.vr.router.Router

/**
 * Created by vladimirrybkin on 17/01/2017.
 */
open class RestartRoute(router: Router, key: String) : DefaultRoute(router, key, Route.Action.REPLACE_TOP) {

    companion object {
        val STATE_BACK_KEY = RestartRoute::class.java.simpleName + "#STATE_BACK_KEY"
        val STATE_BACK_INITIAL_STATE = RestartRoute::class.java.simpleName + "#STATE_BACK_INITIAL_STATE"
        val STATE_BACK_SAVED_STATE = RestartRoute::class.java.simpleName + "#STATE_BACK_SAVED_STATE"
    }

    private var backKey: String? = null
    private var backInitialState: Bundle? = null
    private var backSavedState: Bundle? = null

    constructor(router: Router, inState: Bundle) : this(router, inState.getString(STATE_BACK_KEY)) {
        var initialState = inState.getBundle(STATE_BACK_INITIAL_STATE)

        if (initialState == null) {
            initialState = Bundle()
        }
        initialState.putBundle(STATE_BACK_SAVED_STATE, inState.getBundle(STATE_BACK_SAVED_STATE))
        state(initialState)
    }

    fun backInfo(key: String, initialState: Bundle?, savedState: Bundle?) : RestartRoute {
        backKey = key
        backInitialState = initialState
        backSavedState = savedState
        return this
    }

    override fun go() {
        if (backKey != null) {
            if (state == null) {
                state(Bundle())
            }
            val bundle = state
            bundle?.putString(STATE_BACK_KEY, backKey)
            bundle?.putBundle(STATE_BACK_INITIAL_STATE, backInitialState)
            bundle?.putBundle(STATE_BACK_SAVED_STATE, backSavedState)
        }
        super.go()
    }

}
