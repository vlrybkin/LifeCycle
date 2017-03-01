package org.vr.framework.router.fragment

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.FragmentManager
import org.vr.cycle.LifeCycle
import org.vr.cycle.fragment.DefaultLifeCycleFragment
import org.vr.cycle.fragment.LifeCycleFragment
import org.vr.router.route.transition.RouteTransition
import org.vr.router.router.base.Router

/**
 * Created by vladimirrybkin on 22/02/2017.
 */
abstract class FragmentRouter(val fm: FragmentManager, val containerViewId: Int) : Router<Uri>  {

    override var destroyed: Boolean = false

    abstract fun getLifeCycle(key: Uri): LifeCycle

    override fun push(key: Uri, persistantState: Bundle?, savedState: Bundle?, inTransition: RouteTransition?,
                      outTransition: RouteTransition?): Boolean {
        if (destroyed) return false

        val lifeCycle = getLifeCycle(key)
        val fragment = createFragment(lifeCycle, persistantState, savedState)
        fm.beginTransaction().replace(containerViewId, fragment)
                .addToBackStack(Integer.toString(fm.backStackEntryCount)).commitNow()
        return true
    }

    override fun pop(result: Bundle?, outTransition: RouteTransition?): Boolean {
        if (destroyed) return false

        if (fm.backStackEntryCount > 0) {
            fm.popBackStackImmediate()
            return true
        }
        return false
    }

    override fun replaceTop(key: Uri, persistantState: Bundle?, savedState: Bundle?, inTransition: RouteTransition?,
                            outTransition: RouteTransition?): Boolean {
        if (destroyed) return false

        val lifeCycle = getLifeCycle(key)
        val fragment = createFragment(lifeCycle, persistantState, savedState)

        if (fm.backStackEntryCount > 0) {
            fm.popBackStackImmediate()
        }

        fm.beginTransaction().replace(containerViewId, fragment)
                .addToBackStack(Integer.toString(fm.backStackEntryCount)).commitNow()
        return true
    }

    override fun restart(key: Uri, persistantState: Bundle?, savedState: Bundle?, inTransition: RouteTransition?,
                         outTransition: RouteTransition?): Boolean {
        if (destroyed) return false

        val lifeCycle = getLifeCycle(key)
        val fragment = createFragment(lifeCycle, persistantState, savedState)

        if (fm.backStackEntryCount > 0) {
            fm.popBackStackImmediate(Integer.toString(0), FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        fm.beginTransaction().replace(containerViewId, fragment)
                .addToBackStack(Integer.toString(fm.backStackEntryCount)).commitNow()
        return true
    }

    override fun save(out: Bundle) {
    }

    protected fun createFragment(lifeCycle: LifeCycle, persistantState: Bundle?, savedState: Bundle?) : LifeCycleFragment {
        val fragment = DefaultLifeCycleFragment(lifeCycle)
        fragment.arguments = persistantState
        return fragment
    }

    override fun backPressed(): Boolean {
        if (fm.backStackEntryCount > 0) {
            val currentFragment = fm.findFragmentByTag(Integer.toString(fm.backStackEntryCount))
            val lifeCycleFragment = currentFragment as LifeCycleFragment

            if (!lifeCycleFragment.onBackPressed()) {
                return fm.popBackStackImmediate()
            }
            return true
        } else {
            return false
        }
    }

}
