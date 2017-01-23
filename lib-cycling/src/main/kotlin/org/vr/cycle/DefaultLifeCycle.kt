package org.vr.cycle

import android.content.Context

/**
 * Created by vladimirrybkin on 01/11/16.
 */
abstract class DefaultLifeCycle : LifeCycle {

    protected lateinit var context: Context

    override fun attachBaseContext(context: Context): Context {
        this.context = context
        return super.attachBaseContext(context)
    }

}
