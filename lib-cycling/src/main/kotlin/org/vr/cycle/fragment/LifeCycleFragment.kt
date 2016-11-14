package org.vr.cycle.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import org.vr.cycle.LifeCycle
import org.vr.cycle.R

/**
 * A fragment class with the life cycle mapping.
 *
 * @author Vladimir Rybkin
 */
abstract class LifeCycleFragment : Fragment() {

    protected val lifeCycleDispatcher: FragmentLifeCycleDispatcher
        get() = createLifeCycleDispatcher()

    protected abstract fun createLifeCycle() : LifeCycle

    protected fun getContainerViewId() : Int = R.id.lifecycle_container

    protected fun createLifeCycleDispatcher(): FragmentLifeCycleDispatcher =
            DefaultFragmentLifeCycleDispatcher(this, createLifeCycle())

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifeCycleDispatcher.onFragmentAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifeCycleDispatcher.onFragmentCreate(savedInstanceState, arguments)
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        lifeCycleDispatcher.onFragmentCreateView(view.findViewById(getContainerViewId()) as ViewGroup)
    }

    override fun onActivityCreated(savedInstanceState : Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifeCycleDispatcher.onFragmentActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        lifeCycleDispatcher.onFragmentStart()
    }

    override fun onResume() {
        super.onResume()
        lifeCycleDispatcher.onFragmentResume()
    }

    override fun onPause() {
        super.onPause()
        lifeCycleDispatcher.onFragmentPause()
    }

    override fun onSaveInstanceState(outState : Bundle) {
        super.onSaveInstanceState(outState)
        lifeCycleDispatcher.onFragmentSaveInstance(outState)
    }

    override fun onStop() {
        super.onStop()
        lifeCycleDispatcher.onFragmentStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifeCycleDispatcher.onFragmentDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        lifeCycleDispatcher.onFragmentDestroy()
    }

    override fun onDetach() {
        super.onDetach()
        lifeCycleDispatcher.onFragmentDetach()
    }

}