package org.vr.cycle.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import org.vr.cycle.LifeCycle
import org.vr.cycle.R

abstract class LifeCycleActivity : AppCompatActivity() {

    protected val lifeCycleDispatcher: ActivityLifeCycleDispatcher
        get() = createLifeCycleDispatcher()

    override fun attachBaseContext(context : Context) {
        super.attachBaseContext(lifeCycleDispatcher.onActivityAttachBaseContext(context))
    }

    protected abstract fun createLifeCycle() : LifeCycle

    protected abstract fun setContentView()

    protected fun getContainerViewId() : Int = R.id.lifecycle_container

    protected fun createLifeCycleDispatcher(): ActivityLifeCycleDispatcher =
            DefaultActivityLifeCycleDispatcher(this, createLifeCycle())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifeCycleDispatcher.onActivityCreate(savedInstanceState, intent)
        setContentView()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        val containerView : ViewGroup = findViewById(getContainerViewId()) as ViewGroup
        lifeCycleDispatcher.onActivityPostCreate(containerView, savedInstanceState, intent)
    }

    override fun onResume() {
        super.onResume()
        lifeCycleDispatcher.onActivityResume()
    }

    override fun onPostResume() {
        super.onPostResume()
        lifeCycleDispatcher.onActivityPostResume()
    }

    override fun onSaveInstanceState(outState : Bundle) {
        super.onSaveInstanceState(outState)
        lifeCycleDispatcher.onActivitySaveInstance(outState)
    }

    override fun onPause() {
        super.onPause()
        lifeCycleDispatcher.onActivityPause()
    }

    override fun onStop() {
        super.onStop()
        lifeCycleDispatcher.onActivityStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        lifeCycleDispatcher.onActivityDestroy()
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        // TODO
    }

    override fun onBackPressed() {
        if (!lifeCycleDispatcher.onActivityBackPressed()) {
            super.onBackPressed()
        }
    }

}
