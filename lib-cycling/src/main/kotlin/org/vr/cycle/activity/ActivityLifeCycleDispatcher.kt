package org.vr.cycle.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import org.vr.cycle.LifeCycle

/**
 * The activity base life cycle dispatcher.
 *
 * @author Vladimir Rybkin
 */
interface ActivityLifeCycleDispatcher {

    val lifecycle: LifeCycle

    val activity : Activity

    fun intentToInitialState(intent : Intent?) : Bundle? = intent?.extras

    fun intentToResult(intent : Intent?) : Bundle? = intent?.extras

    fun onActivityAttachBaseContext(context: Context) : Context = lifecycle.attachBaseContext(context)

    fun onActivityCreate(viewContainer: ViewGroup, savedState: Bundle?, intent : Intent?) {
        val initialState = intentToInitialState(intent)
        lifecycle.onCreate(initialState, savedState)
        lifecycle.onCreateView(viewContainer, initialState, savedState)
    }

    fun onActivityStart() = lifecycle.onStart()

    fun onActivitySaveInstance(outState: Bundle) = lifecycle.onSaveInstanceState(outState)

    fun onActivityPause() = lifecycle.onPause()

    fun onActivityResume() {}

    fun onActivityPostResume() = lifecycle.onResume()

    fun onActivityStop() = lifecycle.onStop()

    fun onActivityDestroy() {
        lifecycle.onDestroyView()
        lifecycle.onDestroy()
        lifecycle.detachBaseContext()
    }

    fun onActivityBackPressed() : Boolean = lifecycle.onBackPressed()

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) =
            lifecycle.onResult(intentToResult(data))

    fun onActivityConfigurationChanged(config: Configuration) = lifecycle.onConfigurationChanged(config)

    fun onActivityOptionsItemSelected(item: MenuItem): Boolean = lifecycle.onOptionsItemSelected(item)

}
