package org.vr.cycle

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup

/**
 * Created by vladimirrybkin on 16/01/2017.
 */
open class LifeCycleWrapper : LifeCycle {

    protected var wrappedLifeCycle: LifeCycle? = null

    protected var context: Context? = null
    private var lifeCycleAttachContextCalled: Boolean = false

    private var onCreateCalled: Boolean = false
    private var lifeCycleCreateCalled: Boolean = false

    private var parentViewGroup: ViewGroup? = null
    private var lifeCycleCreateViewCalled: Boolean = false

    private var onStartCalled: Boolean = false
    private var lifeCycleStartCalled: Boolean = false

    private var onResumeCalled: Boolean = false
    private var lifeCycleResumeCalled: Boolean = false

    private var inProgress: Boolean = false
    private var postponedData: PostponedData? = null

    private class PostponedData(val lifeCycle: LifeCycle?, val initialState: Bundle?, val savedState: Bundle?)

    override fun attachBaseContext(context: Context) : Context {
        this.context = context
        wrappedLifeCycle?.attachBaseContext(context)
        lifeCycleAttachContextCalled = wrappedLifeCycle != null
        return context
    }

    override fun onCreate(persistantState: Bundle?, savedState: Bundle?) {
        onCreateCalled = true
        wrappedLifeCycle?.onCreate(persistantState, savedState)
        lifeCycleCreateCalled = wrappedLifeCycle != null
    }

    override fun onCreateView(parentViewGroup: ViewGroup, persistantState: Bundle?,
                              savedInstanceState : Bundle?) {
        this.parentViewGroup = parentViewGroup
        wrappedLifeCycle?.onCreateView(parentViewGroup, persistantState, savedInstanceState)
        lifeCycleCreateViewCalled = wrappedLifeCycle != null
    }

    override fun onStart() {
        wrappedLifeCycle?.onStart()
        onStartCalled = true
        lifeCycleStartCalled = wrappedLifeCycle != null
    }

    override fun onResume() {
        wrappedLifeCycle?.onResume()
        onResumeCalled = true
        lifeCycleResumeCalled = false
    }

    override fun onSaveInstanceState(outState : Bundle) {
        wrappedLifeCycle?.onSaveInstanceState(outState)
    }

    override fun onPause() {
        wrappedLifeCycle?.onPause()
        onResumeCalled = false
        lifeCycleResumeCalled = false
    }

    override fun onStop() {
        wrappedLifeCycle?.onStop()
        onStartCalled = false
        lifeCycleStartCalled = false
    }

    override fun onDestroyView() {
        wrappedLifeCycle?.onDestroyView()
        parentViewGroup = null
        lifeCycleCreateViewCalled = false
    }

    override fun onDestroy() {
        wrappedLifeCycle?.onDestroy()
        onCreateCalled = false
        lifeCycleCreateCalled = false
    }

    override fun detachBaseContext() {
        wrappedLifeCycle?.detachBaseContext()
        lifeCycleAttachContextCalled = false
        context = null
    }

    override fun onBackPressed() : Boolean {
        val localWrappedLifeCycle = wrappedLifeCycle
        if (localWrappedLifeCycle != null) {
            return localWrappedLifeCycle.onBackPressed()
        } else {
            return false
        }
    }

    public fun getLifecycle() : LifeCycle? = wrappedLifeCycle

    public fun attach(lifeCycle: LifeCycle?, initialState: Bundle?, savedState: Bundle?) {
        if (!inProgress) {
            inProgress = true
            tearDown()

            if (lifeCycle != null) {
                setup(lifeCycle, initialState, savedState)
            }
            inProgress = false

            val localPostponed = postponedData
            postponedData = null
            if (localPostponed != null) {
                attach(localPostponed.lifeCycle, localPostponed.initialState, localPostponed.savedState)
            }
        } else {
            postponedData = PostponedData(lifeCycle, initialState, savedState)
        }
    }

    private fun setup(lifeCycle: LifeCycle, initialState: Bundle?, savedState: Bundle?) {
        this.wrappedLifeCycle = lifeCycle

        val localContext = context
        if (localContext != null && !lifeCycleAttachContextCalled) {
            lifeCycle.attachBaseContext(localContext)
            lifeCycleAttachContextCalled = true
        }

        if (this.wrappedLifeCycle == lifeCycle && onCreateCalled && !lifeCycleCreateCalled) {
            lifeCycle.onCreate(initialState, savedState)
            lifeCycleCreateCalled = true
        }

        val localParentViewGroup = parentViewGroup
        if (this.wrappedLifeCycle == lifeCycle && localParentViewGroup != null && !lifeCycleCreateViewCalled) {
            lifeCycle.onCreateView(localParentViewGroup, initialState, savedState)
            lifeCycleCreateViewCalled = true
        }

        if (this.wrappedLifeCycle == lifeCycle && onStartCalled && !lifeCycleStartCalled) {
            lifeCycle.onStart()
            lifeCycleStartCalled = true
        }

        if (this.wrappedLifeCycle == lifeCycle && onResumeCalled && !lifeCycleResumeCalled) {
            lifeCycle.onResume()
            lifeCycleResumeCalled = true
        }
    }

    private fun tearDown() {
        val localWrappedLifeCycle = wrappedLifeCycle ?: return
        this.wrappedLifeCycle = null

        if (lifeCycleResumeCalled) {
            localWrappedLifeCycle.onPause()
            lifeCycleResumeCalled = false
        }

        if (lifeCycleStartCalled) {
            localWrappedLifeCycle.onStop()
            lifeCycleStartCalled = false
        }

        if (lifeCycleCreateViewCalled) {
            localWrappedLifeCycle.onDestroyView()
            lifeCycleCreateViewCalled = false
        }

        if (lifeCycleCreateCalled) {
            localWrappedLifeCycle.onDestroy()
            lifeCycleCreateCalled = false
        }

        if (lifeCycleAttachContextCalled) {
            localWrappedLifeCycle.detachBaseContext()
            lifeCycleAttachContextCalled = false
        }
    }

}
