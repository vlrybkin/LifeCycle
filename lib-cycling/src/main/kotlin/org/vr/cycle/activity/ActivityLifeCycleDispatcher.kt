package org.vr.cycle.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import org.vr.cycle.LifeCycle

/**
 * The activity base life cycle dispatcher.
 *
 * @author Vladimir Rybkin
 */
interface ActivityLifeCycleDispatcher {

    val lifeCycle : LifeCycle

    val activity : Activity

    fun intentToInitialState(intent : Intent?) : Bundle? = Bundle()

    fun onActivityAttachBaseContext(context: Context) : Context = lifeCycle.attachBaseContext(context)

    fun onActivityCreate(savedState: Bundle?, intent : Intent?) {}

    fun onActivityPostCreate(viewContainer: ViewGroup, savedState: Bundle?, intent : Intent?) {
        lifeCycle.onCreate(intentToInitialState(intent), savedState)
        lifeCycle.onCreateView(viewContainer)
    }

    fun onActivityStart() = lifeCycle.onStart()

    fun onActivitySaveInstance(outState: Bundle) = lifeCycle.onSaveInstanceState(outState)

    fun onActivityPause() = lifeCycle.onPause()

    fun onActivityResume() {}

    fun onActivityPostResume() = lifeCycle.onResume()

    fun onActivityStop() = lifeCycle.onStop()

    fun onActivityDestroy() {
        lifeCycle.onDestroyView()
        lifeCycle.onDestroy()
        lifeCycle.detachBaseContext()
    }

    fun onActivityBackPressed() : Boolean = lifeCycle.onBackPressed()

}
