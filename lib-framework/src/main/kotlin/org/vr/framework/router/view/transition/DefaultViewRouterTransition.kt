package org.vr.framework.router.view.transition

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import org.vr.cycle.LifeCycle
import java.util.*

/**
 * Created by vladimirrybkin on 14/02/2017.
 */
open class DefaultViewRouterTransition(val context: Context,
                                       containerView: ViewGroup,
                                       enterKey: Uri,
                                       enterLifeCycle: LifeCycle,
                                       val enterPersistantState: Bundle?,
                                       val enterSavedState: Bundle?,
                                       exitKey: Uri?,
                                       exitLifeCycle: LifeCycle?) :
        BaseViewRouterTransition(containerView, enterKey, enterLifeCycle, exitKey, exitLifeCycle) {
    private val listeners: LinkedList<ViewRouterTransitionListener> = LinkedList()

    override fun start() {
        notifyStart()
        setup()
        tearDown()
        notifyEnd()
        listeners.clear()
    }

    private fun setup() {
        enterLifeCycle.attachBaseContext(context)
        enterLifeCycle.onCreate(enterPersistantState, enterSavedState)

        val rootFrame = LayoutInflater.from(context)
                .inflate(org.vr.framework.R.layout.app_view_router_root, containerView, false) as ViewGroup
        enterLifeCycle.onCreateView(rootFrame, enterPersistantState, enterSavedState)
        attachView(rootFrame)
        enterLifeCycle.onStart()
        enterLifeCycle.onResume()
    }

    private fun tearDown() {
        if (exitLifeCycle == null) return

        exitLifeCycle.onPause()
        exitLifeCycle.onStop()
        exitLifeCycle.onDestroyView()
        detachView()
        exitLifeCycle.onDestroy()
        exitLifeCycle.detachBaseContext()
        containerView.removeAllViews()
    }

    override fun cancel() {
    }

}
