package org.vr.framework.router.view.transition

/**
 * Created by vladimirrybkin on 14/02/2017.
 */
interface ViewRouterTransition {

    fun start()

    fun registerListener(listener: ViewRouterTransitionListener)

    fun cancel()

}
