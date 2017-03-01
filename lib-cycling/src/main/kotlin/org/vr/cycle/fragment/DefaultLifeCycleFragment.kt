package org.vr.cycle.fragment

import org.vr.cycle.LifeCycle

/**
 * Created by vladimirrybkin on 27/02/2017.
 */
open class DefaultLifeCycleFragment(val lifeCycle: LifeCycle) : LifeCycleFragment() {

    override fun createLifeCycle(): LifeCycle {
        return lifeCycle
    }

}
