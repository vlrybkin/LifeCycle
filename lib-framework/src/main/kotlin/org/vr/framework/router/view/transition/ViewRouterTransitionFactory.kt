package org.vr.framework.router.view.transition

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import org.vr.cycle.LifeCycle
import org.vr.router.route.transition.RouteTransition
import org.vr.framework.router.view.ViewRouterTransition

/**
 * Created by vladimirrybkin on 14/02/2017.
 */
interface ViewRouterTransitionFactory {

    fun createTransition(context: Context, containerView: ViewGroup,
                         enterKey: Uri, enterLifeCycle: LifeCycle, enterPersistantState: Bundle?,
                         enterSavedState: Bundle?, exitKey: Uri?, exitLifeCycle: LifeCycle?,
                         inTransition: RouteTransition?, outTransition: RouteTransition?) : ViewRouterTransition {
            return DefaultViewRouterTransition(context, containerView, enterKey, enterLifeCycle,
                    enterPersistantState, enterSavedState, exitKey, exitLifeCycle)
    }

}
