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

    companion object {
        fun <T> getKey(clazz: Class<T>): String {
            val annotation = clazz.getAnnotation(LifecycleKey::class.java)
            return annotation?.value ?: clazz.simpleName
        }
    }

    fun getKey(): String {
        return getKey(this.javaClass)
    }

    fun attachBaseContext(context: Context) : Context = context

    fun onCreate(initialState: Bundle?, savedState: Bundle?) {}

    fun onCreateView(parentViewGroup: ViewGroup, initialState: Bundle?, savedInstanceState : Bundle?) {}

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
