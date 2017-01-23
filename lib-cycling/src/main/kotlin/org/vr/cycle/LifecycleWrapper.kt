package org.vr.cycle

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup

/**
 * Created by vladimirrybkin on 16/01/2017.
 */
open class LifecycleWrapper : LifeCycle {

    protected var wrappedLifeCycle: LifeCycle? = null
    protected var context: Context? = null
    protected var onCreateCalled: Boolean = false
    protected var parentViewGroup: ViewGroup? = null
    protected var onStartCalled: Boolean = false
    protected var onResumeCalled: Boolean = false

    override fun attachBaseContext(context: Context) : Context {
        this.context = context
        wrappedLifeCycle?.attachBaseContext(context)
        return context
    }

    override fun onCreate(initialState: Bundle?, savedState: Bundle?) {
        onCreateCalled = true
        wrappedLifeCycle?.onCreate(initialState, savedState)
    }

    override fun onCreateView(parentViewGroup: ViewGroup, initialState: Bundle?,
                              savedInstanceState : Bundle?) {
        this.parentViewGroup = parentViewGroup
        wrappedLifeCycle?.onCreateView(parentViewGroup, initialState, savedInstanceState)
    }

    override fun onStart() {
        wrappedLifeCycle?.onStart()
        onStartCalled = true
    }

    override fun onResume() {
        wrappedLifeCycle?.onResume()
        onResumeCalled = true
    }

    override fun onSaveInstanceState(outState : Bundle) {
        wrappedLifeCycle?.onSaveInstanceState(outState)
    }

    override fun onPause() {
        wrappedLifeCycle?.onPause()
        onResumeCalled = false
    }

    override fun onStop() {
        wrappedLifeCycle?.onStop()
        onStartCalled = false
    }

    override fun onDestroyView() {
        wrappedLifeCycle?.onDestroyView()
        parentViewGroup = null
    }

    override fun onDestroy() {
        wrappedLifeCycle?.onDestroy()
        onCreateCalled = false
    }

    override fun detachBaseContext() {
        wrappedLifeCycle?.detachBaseContext()
        context = null
    }

    override fun onBackPressed() : Boolean = false

    public fun getLifecycle() : LifeCycle? = wrappedLifeCycle

    public fun attach(lifeCycle: LifeCycle?, initialState: Bundle?, savedState: Bundle?) {
        val localWrappedLifeCycle = wrappedLifeCycle
        if (localWrappedLifeCycle != null) {
            tearDown(localWrappedLifeCycle)
            this.wrappedLifeCycle = null
        }

        if (lifeCycle != null) {
            setup(lifeCycle, initialState, savedState)
        }
    }

    protected fun setup(lifeCycle: LifeCycle, initialState: Bundle?, savedState: Bundle?) {
        this.wrappedLifeCycle = lifeCycle

        val localContext = context
        if (localContext != null) {
            lifeCycle.attachBaseContext(localContext)
        }

        if (onCreateCalled) {
            lifeCycle.onCreate(initialState, savedState)
        }

        val localParentViewGroup = parentViewGroup
        if (localParentViewGroup != null) {
            lifeCycle.onCreateView(localParentViewGroup, initialState, savedState)
        }

        if (onStartCalled) {
            lifeCycle.onStart()
        }

        if (onResumeCalled) {
            lifeCycle.onResume()
        }
    }

    protected fun tearDown(lifeCycle: LifeCycle) {
        if (onResumeCalled) {
            lifeCycle.onPause()
        }

        if (onStartCalled) {
            lifeCycle.onStop()
        }

        val localParentViewGroup = parentViewGroup
        if (localParentViewGroup != null) {
            lifeCycle.onDestroyView()
        }

        if (onCreateCalled) {
            lifeCycle.onDestroy()
        }

        val localContext = context
        if (localContext != null) {
            lifeCycle.detachBaseContext()
        }
    }

}
