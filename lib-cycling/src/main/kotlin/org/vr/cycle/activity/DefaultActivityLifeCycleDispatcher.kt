package org.vr.cycle.activity

import android.app.Activity
import org.vr.cycle.LifeCycle

/**
 * The default activity lifecycle dispatcher.
 *
 * @author Vladimir Rybkin
 */
class DefaultActivityLifeCycleDispatcher(override val activity: Activity,
                                         override val lifeCycle: LifeCycle) :
        ActivityLifeCycleDispatcher {
}