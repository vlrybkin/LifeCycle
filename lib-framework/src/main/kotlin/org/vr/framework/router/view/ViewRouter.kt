package org.vr.framework.router.view

import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import org.vr.cycle.LifeCycle
import org.vr.framework.router.view.transition.ViewRouterTransitionFactory
import org.vr.framework.router.view.transition.ViewRouterTransitionListener
import org.vr.router.route.transition.RouteTransition
import org.vr.router.router.base.Router

/**
 * Created by vladimirrybkin on 31/01/2017.
 */
abstract class ViewRouter(val context: Context,
                          val containerView: ViewGroup) : Router<Uri> {

    private var currentKey: Uri? = null
    private var currentLifeCycle : LifeCycle? = null
    private var currentTransition: org.vr.framework.router.view.ViewRouterTransition? = null

    private var nextKey: Uri? = null
    private var nextPersistantState: Bundle? = null
    private var nextSavedState: Bundle? = null
    private var nextInTransition: RouteTransition? = null
    private var nextOutTransition: RouteTransition? = null

    private var listener: ViewRouterListener? = null
    private var transitionFactory : ViewRouterTransitionFactory = DefaultViewRouterTransitionFactory()

    override var destroyed = false

    abstract fun getLifeCycle(key: Uri): LifeCycle

    override fun push(key: Uri, persistantState: Bundle?, savedState: Bundle?,
                      inTransition: RouteTransition?, outTransition: RouteTransition?): Boolean {
        // doesnt support a stack
        return replaceTop(key, persistantState, savedState, inTransition, outTransition)
    }

    override fun pop(result: Bundle?, outTransition: RouteTransition?): Boolean {
        return false
    }

    override fun replaceTop(key: Uri, persistantState: Bundle?, savedState: Bundle?,
                            inTransition: RouteTransition?, outTransition: RouteTransition?): Boolean {
        if (destroyed) return false

        if (currentTransition == null) {
            val enterLifeCycle = getLifeCycle(key)
            val localCurrentTransition = createTransition(context, containerView, key, enterLifeCycle,
                    persistantState, savedState, currentKey, currentLifeCycle, inTransition, outTransition)
            localCurrentTransition.registerListener(object: ViewRouterTransitionListener {
                override fun onTransitionStart(enterKey: Uri, enterLifeCycle: LifeCycle,
                                               exitKey: Uri?, exitLifeCycle: LifeCycle?) {
                }

                override fun onTransitionEnd(enterKey: Uri, enterLifeCycle: LifeCycle,
                                             exitKey: Uri?, exitLifeCycle: LifeCycle?) {
                    currentTransition = null
                    currentKey = key
                    currentLifeCycle = enterLifeCycle

                    val localNextKey = nextKey
                    val localNextPersistantState = nextPersistantState
                    val localNextSavedState = nextSavedState
                    val localNextInTransition = nextInTransition
                    val localNextOutTransition = nextOutTransition

                    if (localNextKey != null) {
                        nextKey = null
                        nextPersistantState = null
                        nextSavedState = null
                        nextInTransition = null
                        nextOutTransition = null

                        replaceTop(localNextKey, localNextPersistantState, localNextSavedState,
                                localNextInTransition, localNextOutTransition)
                    }
                }
            })
            currentTransition = localCurrentTransition
            listener?.onTransition(localCurrentTransition)
            localCurrentTransition.start()
        } else {
            nextKey = key
            nextPersistantState = persistantState
            nextSavedState = savedState
            nextInTransition = inTransition
            nextOutTransition = outTransition
        }

        return true
    }

    override fun restart(key: Uri, persistantState: Bundle?, savedState: Bundle?,
                         inTransition: RouteTransition?, outTransition: RouteTransition?): Boolean {
        // doesnt support a stack
        return replaceTop(key, persistantState, savedState, inTransition, outTransition)
    }

    override fun save(out: Bundle) {
        currentLifeCycle?.onSaveInstanceState(out)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return currentLifeCycle?.onOptionsItemSelected(item) ?: false
    }

    override fun onConfigurationChanged(configuration: Configuration) {
        currentLifeCycle?.onConfigurationChanged(configuration)
    }

    fun setTransitionFactory(transitionFactory: ViewRouterTransitionFactory) {
        this.transitionFactory = transitionFactory
    }

    fun setListener(listener: ViewRouterListener?) {
        this.listener = listener
    }

    private fun createTransition(context: Context, containerView: ViewGroup,
        enterKey: Uri, enterLifeCycle: LifeCycle, enterPersistantState: Bundle?, enterSavedState: Bundle?,
        exitKey: Uri?, exitLifeCycle: LifeCycle?,
        inTransition: RouteTransition?, outTransition: RouteTransition?) : org.vr.framework.router.view.ViewRouterTransition {

        return transitionFactory.createTransition(context, containerView, enterKey, enterLifeCycle,
                enterPersistantState, enterSavedState, exitKey, exitLifeCycle, inTransition, outTransition)
    }

    override fun backPressed(): Boolean {
        val localCurrentLifeCycle = currentLifeCycle ?: return false
        return localCurrentLifeCycle.onBackPressed()
    }

    override fun destroy() {
        super.destroy()
        currentTransition?.cancel()
        nextKey = null
        nextPersistantState = null
        nextSavedState = null
        nextInTransition = null
        nextOutTransition = null
    }

    private inner class DefaultViewRouterTransitionFactory : ViewRouterTransitionFactory

}
