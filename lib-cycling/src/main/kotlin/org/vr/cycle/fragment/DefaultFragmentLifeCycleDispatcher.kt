package org.vr.cycle.fragment

import android.support.v4.app.Fragment
import org.vr.cycle.LifeCycle

/**
 * The default fragment dispatcher implementation.
 *
 * @author Vladimir Rybkin
 */
class DefaultFragmentLifeCycleDispatcher(override val fragment: Fragment,
                                         override val lifeCycle: LifeCycle) : FragmentLifeCycleDispatcher{

}