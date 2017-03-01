package org.vr.cycle
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup

/**
 * The base life cycle interface.
 *
 * @author Vladimir Rybkin
 */
interface LifeCycle {

    companion object {
        fun <T> getKey(clazz: Class<T>): String {
            val annotation = clazz.getAnnotation(LifeCycleKey::class.java)
            return annotation?.value ?: clazz.simpleName
        }
    }

    fun getKey(): String {
        return getKey(this.javaClass)
    }

    fun attachBaseContext(context: Context) : Context = context

    fun onCreate(persistantState: Bundle?, savedState: Bundle?) {}

    fun onCreateView(parentViewGroup: ViewGroup, persistantState: Bundle?, savedInstanceState : Bundle?) {}

    fun onStart() {}

    fun onResume() {}

    fun onSaveInstanceState(outState : Bundle) {}

    fun onPause() {}

    fun onStop() {}

    fun onDestroyView() {}

    fun onDestroy() {}

    fun detachBaseContext() {}

    fun onBackPressed() : Boolean = false

    fun onResult(intentToResult: Bundle?) {}

    fun onConfigurationChanged(config: Configuration) {}

    fun onOptionsItemSelected(item: MenuItem): Boolean = false

}
