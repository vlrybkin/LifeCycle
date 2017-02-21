package org.vr.framework.router.view.transition

import android.net.Uri
import android.view.View
import android.view.ViewGroup
import org.vr.cycle.LifeCycle
import java.util.*
/**
 * Created by vladimirrybkin on 14/02/2017.
 */
abstract class BaseViewRouterTransition(val containerView: ViewGroup,
                                        val enterKey: Uri,
                                        val enterLifeCycle: LifeCycle,
                                        val exitKey: Uri?,
                                        val exitLifeCycle: LifeCycle?) :
        ViewRouterTransition {

    private val listeners: LinkedList<ViewRouterTransitionListener> = LinkedList()

    protected fun attachView(view: View) {
        containerView.addView(view)
    }

    protected fun detachView() : View? {
        val detachedView = containerView.getChildAt(0)
        containerView.removeViewAt(0)
        return detachedView
    }

    override fun registerListener(listener: ViewRouterTransitionListener) {
        listeners.add(listener)
    }

    protected fun notifyStart() {
        for (item in listeners){
            item.onTransitionStart(enterKey, enterLifeCycle, exitKey, exitLifeCycle)
        }
    }

    protected fun notifyEnd() {
        for (item in listeners){
            item.onTransitionEnd(enterKey, enterLifeCycle, exitKey, exitLifeCycle)
        }
    }

}
