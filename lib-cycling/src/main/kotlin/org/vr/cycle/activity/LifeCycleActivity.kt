package org.vr.cycle.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent
import org.vr.cycle.LifeCycle
import org.vr.cycle.R

abstract class LifeCycleActivity : AppCompatActivity() {

    protected val lifeCycleDispatcher = createLifeCycleDispatcher()

    override fun attachBaseContext(context : Context) {
        super.attachBaseContext(lifeCycleDispatcher.onActivityAttachBaseContext(context))
    }

    protected abstract fun createLifeCycle() : LifeCycle

    open protected fun setContentView() {
        frameLayout {
            id = R.id.lifecycle_container
            lparams(width = matchParent, height = matchParent)
            fitsSystemWindows = false
        }
    }

    protected fun getContainerViewId() : Int = R.id.lifecycle_container

    protected fun createLifeCycleDispatcher(): ActivityLifeCycleDispatcher =
            DefaultActivityLifeCycleDispatcher(this, createLifeCycle())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isFinishing) {
            setContentView()
            val containerView: ViewGroup = findViewById(getContainerViewId()) as ViewGroup
            lifeCycleDispatcher.onActivityCreate(containerView, savedInstanceState, intent)
        }
    }

    override fun onStart() {
        super.onStart()
        if (!isFinishing) {
            lifeCycleDispatcher.onActivityStart()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isFinishing) {
            lifeCycleDispatcher.onActivityResume()
        }
    }

    override fun onPostResume() {
        super.onPostResume()
        if (!isFinishing) {
            lifeCycleDispatcher.onActivityPostResume()
        }
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
