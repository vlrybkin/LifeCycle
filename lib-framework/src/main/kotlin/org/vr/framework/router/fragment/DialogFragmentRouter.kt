package org.vr.framework.router.fragment

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import org.vr.router.route.transition.RouteTransition
import org.vr.router.router.base.Router

/**
 * Created by vladimirrybkin on 27/02/2017.
 */
abstract class DialogFragmentRouter(val fm: FragmentManager) : Router<Uri> {

    private var dialogFragment : DialogFragment? = null

    override var destroyed: Boolean = false

    abstract fun createDialogFragment(key: Uri) : DialogFragment

    override fun push(key: Uri, persistantState: Bundle?, savedState: Bundle?,
                      inTransition: RouteTransition?, outTransition: RouteTransition?): Boolean {
        return replaceTop(key, persistantState, savedState, inTransition, outTransition)
    }

    override fun pop(result: Bundle?, outTransition: RouteTransition?): Boolean {
        if (dialogFragment != null) {
            dialogFragment?.dismiss()
            return true
        } else {
            return false
        }
    }

    override fun replaceTop(key: Uri, persistantState: Bundle?, savedState: Bundle?,
                            inTransition: RouteTransition?, outTransition: RouteTransition?): Boolean {
        pop(null, outTransition)

        val localDialogFragment = createDialogFragment(key)
        localDialogFragment.arguments = persistantState
        dialogFragment = localDialogFragment
        localDialogFragment.show(fm, null)
        return true
    }

    override fun restart(key: Uri, persistantState: Bundle?, savedState: Bundle?,
                         inTransition: RouteTransition?, outTransition: RouteTransition?): Boolean {
        return replaceTop(key, persistantState, savedState, inTransition, outTransition)
    }

    override fun save(out: Bundle) {
    }

    override fun backPressed(): Boolean {
        if (dialogFragment != null) {
            dialogFragment?.dismiss()
            return true
        } else {
            return false
        }
    }

}
