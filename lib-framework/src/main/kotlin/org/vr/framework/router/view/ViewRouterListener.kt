package org.vr.framework.router.view

import org.vr.framework.router.view.transition.ViewRouterTransition

/**
 * Created by vladimirrybkin on 14/02/2017.
 */
interface ViewRouterListener {

    fun onTransition(transition: ViewRouterTransition)

}
