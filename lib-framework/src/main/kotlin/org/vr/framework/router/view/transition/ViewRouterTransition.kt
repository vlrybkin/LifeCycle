package org.vr.framework.router.view

import org.vr.framework.router.view.transition.ViewRouterTransitionListener

interface ViewRouterTransition {

    fun start()

    fun registerListener(listener: ViewRouterTransitionListener)

    fun cancel()

}
