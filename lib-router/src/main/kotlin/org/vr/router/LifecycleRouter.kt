package org.vr.router

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import org.vr.cycle.LifeCycle
import org.vr.cycle.LifecycleWrapper
import org.vr.di.DiContextWrapper
import org.vr.di.DiNames

/**
 * Created by vladimirrybkin on 11/01/2017.
 */
abstract class LifecycleRouter(val lifecycle: LifecycleWrapper = LifecycleWrapper()) :
        LifeCycle by lifecycle, Router {

    protected lateinit var context: DiContextWrapper
    protected lateinit var viewContainer: ViewGroup

    override fun attachBaseContext(context: Context): Context {
        this.context = DiContextWrapper(context)
        return lifecycle.attachBaseContext(this.context)
    }

    override fun onCreateView(parentViewGroup: ViewGroup, initialState: Bundle?,
                              savedInstanceState : Bundle?) {
        viewContainer = getContainerView(parentViewGroup)

        val routerComponent = createComponent(viewContainer)
        if (routerComponent != null) {
            context.addComponent(DiNames.ROUTER_COMPONENT, routerComponent)
        }

        val resolvedLifecycle = getInitialLifecycle(initialState, savedInstanceState)
        lifecycle.attach(resolvedLifecycle, initialState, savedInstanceState)
        lifecycle.onCreateView(viewContainer, initialState, savedInstanceState)
    }

    protected open fun getContainerView(parentViewGroup: ViewGroup): ViewGroup = parentViewGroup

    override fun onSaveInstanceState(outState : Bundle) {
        super.onSaveInstanceState(outState)
        save(outState)
    }

    override fun save(out: Bundle) {
    }

    protected abstract fun getInitialLifecycle(initialState: Bundle?,
                                               savedInstanceState : Bundle?): LifeCycle?

    protected abstract fun createComponent(viewContainer: ViewGroup) : Any?

}
