package org.vr.router.route.base

import android.content.Context
import android.net.Uri
import android.os.Bundle
import org.vr.router.route.transition.RouteTransition
import org.vr.router.router.base.Router

/**
 * Created by vladimirrybkin on 24/11/16.
 */
class Route {

    companion object {
        val ON_TOP = 1
        val REPLACE_TOP = 2
        val CLEARSTACK = 3
        val POP = 4

        val KEY = Route::class.java.simpleName + "#KEY"
        val ACTION = Route::class.java.simpleName + "#ACTION"
        val PERSISTANT_STATE = Route::class.java.simpleName + "#PERSISTANT_STATE"
        val SAVED_STATE = Route::class.java.simpleName + "#SAVED_STATE"
        val RESULT = Route::class.java.simpleName + "#RESULT"
        val IN_TRANSITION = Route::class.java.simpleName + "#IN_TRANSITION"
        val OUT_TRANSITION = Route::class.java.simpleName + "#OUT_TRANSITION"
    }

    private var key: Uri
    private var action: Int = ON_TOP
    private var persistantState: Bundle? = null
    private var savedState: Bundle? = null
    private var result: Bundle? = null
    private var inTransition: RouteTransition? = null
    private var outTransition: RouteTransition? = null

    constructor(key: Uri) {
        this.key = key
    }

    constructor(inState: Bundle) : this(Uri.parse(inState.getString(KEY))) {
        action = inState.getInt(ACTION)
        persistantState = inState.getBundle(PERSISTANT_STATE)
        savedState = inState.getBundle(SAVED_STATE)
        result = inState.getBundle(RESULT)
        inTransition = if (inState.getBundle(IN_TRANSITION) != null)
            RouteTransition(inState.getBundle(IN_TRANSITION)) else null
        outTransition = if (inState.getBundle(OUT_TRANSITION) != null)
            RouteTransition(inState.getBundle(OUT_TRANSITION)) else null
    }

    fun toBundle(out: Bundle) {
        out.putString(KEY, key.toString())
        out.putInt(ACTION, action)
        out.putBundle(PERSISTANT_STATE, persistantState)
        out.putBundle(SAVED_STATE, savedState)
        out.putBundle(RESULT, result)
        out.putBundle(IN_TRANSITION, inTransition?.toBundle(Bundle()))
        out.putBundle(OUT_TRANSITION, outTransition?.toBundle(Bundle()))
    }

    fun action(action: Int) : Route {
        this.action = action
        return this
    }

    fun persistantState(persistantState: Bundle?) : Route {
        this.persistantState = persistantState
        return this
    }

    fun savedState(savedState: Bundle?) : Route {
        this.savedState = savedState
        return this
    }

    fun result(result: Bundle) : Route {
        this.result = result
        return this
    }

    fun scheme(scheme: String) : Route {
        key = key.buildUpon().scheme(scheme).build()
        return this
    }

    fun authority(authority: String) : Route {
        key = key.buildUpon().authority(authority).build()
        return this
    }

    fun inTransition(inTransition: RouteTransition?) : Route {
        this.inTransition = inTransition
        return this
    }

    fun outTransition(outTransition: RouteTransition?) : Route {
        this.outTransition = outTransition
        return this
    }

    fun go(context: Context) {
        @Suppress("UNCHECKED_CAST")
        val router : Router<Uri> = context.getSystemService(key.authority) as Router<Uri>? ?:
                throw RuntimeException("A router is not found for the authority " + key.authority)

        when(action) {
            Route.ON_TOP -> router.push(key, persistantState, savedState, inTransition, outTransition)
            Route.REPLACE_TOP -> router.replaceTop(key, persistantState, savedState, inTransition, outTransition)
            Route.CLEARSTACK -> router.restart(key, persistantState, savedState, inTransition, outTransition)
            Route.POP -> router.pop(result, inTransition, outTransition)
        }
    }

    fun getKey() = key

}
