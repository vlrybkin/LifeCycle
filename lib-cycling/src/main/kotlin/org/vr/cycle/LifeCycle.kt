package org.vr.cycle
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup

/**
 * The base life cycle interface.
 *
 * @author Vladimir Rybkin
 */
interface LifeCycle {

    fun attachBaseContext(context: Context) : Context = context

    fun onCreate(initialState: Bundle?, savedState: Bundle?) {}

    fun onCreateView(parentViewGroup: ViewGroup) {}

    fun onStart() {}

    fun onResume() {}

    fun onSaveInstanceState(outState : Bundle) {}

    fun onPause() {}

    fun onStop() {}

    fun onDestroyView() {}

    fun onDestroy() {}

    fun detachBaseContext() {}

    fun onBackPressed() : Boolean = false

}
