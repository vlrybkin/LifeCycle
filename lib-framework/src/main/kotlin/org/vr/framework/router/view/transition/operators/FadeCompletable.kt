package org.vr.framework.router.view.transition.operators

import android.support.v4.view.ViewCompat
import android.view.View
import android.view.animation.Interpolator
import rx.Completable
import rx.CompletableSubscriber

/**
 * Created by vladimirrybkin on 17/02/2017.
 */
class FadeCompletable(private val view: View,
                      private val from: Float,
                      private val to: Float,
                      private val duration: Long,
                      private val interpolator: Interpolator) : Completable.OnSubscribe {

    override fun call(subscriber: CompletableSubscriber) {
        view.alpha = from
        ViewCompat.animate(view)
                .alpha(to)
                .setDuration(duration)
                .setInterpolator(interpolator)
                .withEndAction {
                        subscriber.onCompleted()
                }
    }

}
