package org.vr.cycle.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.ViewGroup
import org.vr.cycle.LifeCycle

/**
 * The fragment base life cycle dispatcher.
 *
 * @author Vladimir Rybkin
 */
interface FragmentLifeCycleDispatcher {

    val lifeCycle : LifeCycle

    val fragment : Fragment

    fun onFragmentAttach(context: Context) : Context = lifeCycle.attachBaseContext(context)

    fun onFragmentCreate(savedState: Bundle?, arguments: Bundle?) = lifeCycle.onCreate(arguments, savedState)

    fun onFragmentCreateView(viewContainer: ViewGroup, savedState : Bundle?, arguments: Bundle?)
            = lifeCycle.onCreateView(viewContainer, arguments, savedState)

    fun onFragmentActivityCreated(savedState: Bundle?) = {}

    fun onFragmentStart() = lifeCycle.onStart()

    fun onFragmentResume() = lifeCycle.onResume()

    fun onFragmentSaveInstance(outState: Bundle) = lifeCycle.onSaveInstanceState(outState)

    fun onFragmentPause() = lifeCycle.onPause()

    fun onFragmentStop() = lifeCycle.onStop()

    fun onFragmentDestroyView() = lifeCycle.onDestroyView()

    fun onFragmentDestroy() = lifeCycle.onDestroy()

    fun onFragmentDetach() = lifeCycle.detachBaseContext()

}
