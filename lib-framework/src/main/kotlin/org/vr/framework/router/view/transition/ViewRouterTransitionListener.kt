package org.vr.framework.router.view.transition

import android.net.Uri
import org.vr.cycle.LifeCycle

/**
 * Created by vladimirrybkin on 14/02/2017.
 */
interface ViewRouterTransitionListener {

    fun onTransitionStart(enterKey: Uri, enterLifeCycle: LifeCycle,
                          exitKey: Uri?, exitLifeCycle: LifeCycle?)

    fun onTransitionEnd(enterKey: Uri, enterLifeCycle: LifeCycle,
                        exitKey: Uri?, exitLifeCycle: LifeCycle?)

}
