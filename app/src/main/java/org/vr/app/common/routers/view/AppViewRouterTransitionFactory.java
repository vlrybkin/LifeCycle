package org.vr.app.common.routers.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import org.vr.app.annotations.ParentLayout;
import org.vr.cycle.LifeCycle;
import org.vr.framework.router.view.ViewRouterTransition;
import org.vr.framework.router.view.transition.BaseViewRouterTransition;
import org.vr.framework.router.view.transition.ViewRouterTransitionFactory;
import org.vr.framework.router.view.transition.operators.FadeCompletable;
import org.vr.router.route.transition.RouteTransition;

import javax.inject.Inject;

import rx.Completable;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by vladimirrybkin on 14/02/2017.
 */
public class AppViewRouterTransitionFactory implements ViewRouterTransitionFactory {

    @Inject
    public AppViewRouterTransitionFactory() {
    }

    @NonNull
    @Override
    public ViewRouterTransition createTransition(@NonNull Context context,
                                                 @NonNull ViewGroup containerView,
                                                 @NonNull Uri enterKey,
                                                 @NonNull LifeCycle enterLifeCycle,
                                                 @Nullable Bundle enterPersistantState,
                                                 @Nullable Bundle enterSavedState,
                                                 @Nullable Uri exitKey,
                                                 @Nullable LifeCycle exitLifeCycle,
                                                 @Nullable RouteTransition inTransition,
                                                 @Nullable RouteTransition outTransition) {
        return new SimpleTransition(context, containerView, enterKey, enterLifeCycle,
                enterPersistantState, enterSavedState, exitKey, exitLifeCycle, inTransition, outTransition);
    }

    private static class SimpleTransition extends BaseViewRouterTransition {

        private final Context context;
        private final Uri enterKey;
        private final Bundle enterPersistantState;
        private final Bundle enterSavedState;
        private final Uri exitKey;
        private final RouteTransition inTransition;
        private final RouteTransition outTransition;

        private Subscription animationSubscription = Subscriptions.unsubscribed();

        public SimpleTransition(@NonNull Context context,
                                @NonNull ViewGroup containerView,
                                @NonNull Uri enterKey,
                                @NonNull LifeCycle enterLifeCycle,
                                @Nullable Bundle enterPersistantState,
                                @Nullable Bundle enterSavedState,
                                @Nullable Uri exitKey,
                                @Nullable LifeCycle exitLifeCycle,
                                @Nullable RouteTransition inTransition,
                                @Nullable RouteTransition outTransition) {
            super(containerView, enterKey, enterLifeCycle, exitKey, exitLifeCycle);
            this.context = context;
            this.enterKey = enterKey;
            this.enterPersistantState = enterPersistantState;
            this.enterSavedState = enterSavedState;
            this.exitKey = exitKey;
            this.inTransition = inTransition;
            this.outTransition = outTransition;
        }

        @Override
        public void start() {
            ViewGroup rootContainerView = getContainerView();
            LifeCycle enterLifeCycle = getEnterLifeCycle();
            LifeCycle exitLifeCycle = getExitLifeCycle();
            ViewGroup exitContainerView = (ViewGroup) rootContainerView.getChildAt(0);

            notifyStart();

            // start enter lifecycle raising
            enterLifeCycle.attachBaseContext(context);
            enterLifeCycle.onCreate(enterPersistantState, enterSavedState);

            // prepare enter container
            ViewGroup enterContainerView = (ViewGroup) LayoutInflater.from(context).
                    inflate(org.vr.framework.R.layout.app_view_router_root, rootContainerView, false);
            ViewGroup lifeCycleParentView = enterContainerView;

            ParentLayout parentViewAnnotation = enterLifeCycle.getClass().getAnnotation(ParentLayout.class);
            if (parentViewAnnotation != null) {
                ViewGroup wrapView = (ViewGroup) LayoutInflater.from(context).
                        inflate(parentViewAnnotation.layoutId(), enterContainerView, false);
                lifeCycleParentView = parentViewAnnotation.rootViewId() > 0 ?
                        (ViewGroup) wrapView.findViewById(parentViewAnnotation.rootViewId()) : wrapView;
                enterContainerView.addView(wrapView);
            }

            if (lifeCycleParentView == null) {
                throw new IllegalArgumentException("The parent view not found within the parent layout");
            }

            enterLifeCycle.onCreateView(lifeCycleParentView, enterPersistantState, enterSavedState);

            // prepare enter animation
            Completable enterAnimation = prepareEnterAnimation(enterContainerView,
                    enterLifeCycle, inTransition);

            // prepare exit animation
            Completable exitAnimation = null;
            if (exitLifeCycle != null && exitContainerView != null) {
                exitLifeCycle.onPause();
                exitLifeCycle.onStop();

                exitAnimation = prepareExitAnimation(exitContainerView, exitLifeCycle, outTransition);
            }

            if (enterAnimation != null && exitAnimation != null) {
                if (bothAnimationsSameTime(enterKey, enterLifeCycle, exitKey, exitLifeCycle)) {
                    rootContainerView.addView(enterContainerView);
                    animationSubscription = Completable.merge(enterAnimation, exitAnimation)
                            .subscribe(() -> {
                                finishExit(exitLifeCycle, exitContainerView, rootContainerView);
                                finishEnter(enterLifeCycle, enterContainerView, false, rootContainerView);
                                notifyEnd();
                            });

                } else {
                    animationSubscription =
                            exitAnimation.doOnCompleted(() -> {
                                finishExit(exitLifeCycle, exitContainerView, rootContainerView);
                                rootContainerView.addView(enterContainerView);
                            })
                            .andThen(enterAnimation)
                            .subscribe(() -> {
                                finishEnter(enterLifeCycle, enterContainerView, false, rootContainerView);
                                notifyEnd();
                            });
                }
            } else if (enterAnimation != null) {
                if (exitLifeCycle != null && exitContainerView != null) {
                    finishExit(exitLifeCycle, exitContainerView, rootContainerView);
                }

                rootContainerView.addView(enterContainerView);
                animationSubscription = enterAnimation.subscribe(() -> {
                    finishEnter(enterLifeCycle, enterContainerView, false, rootContainerView);
                    notifyEnd();
                });
            } else if (exitAnimation != null) {
                exitAnimation.subscribe(() -> {
                    finishExit(exitLifeCycle, exitContainerView, rootContainerView);
                    finishEnter(enterLifeCycle, enterContainerView, true, rootContainerView);
                    notifyEnd();
                });
            } else {
                if (exitLifeCycle != null && exitContainerView != null) {
                    finishExit(exitLifeCycle, exitContainerView, rootContainerView);
                }

                finishEnter(enterLifeCycle, enterContainerView, true, rootContainerView);
                notifyEnd();
            }
        }

        private void finishExit(@NonNull LifeCycle exitLifeCycle, @NonNull ViewGroup exitView,
                                @NonNull ViewGroup rootContainerView) {
            exitLifeCycle.onDestroyView();
            rootContainerView.removeView(exitView);
            exitLifeCycle.onDestroy();
            exitLifeCycle.detachBaseContext();
        }

        private void finishEnter(@NonNull LifeCycle enterLifeCycle, @NonNull ViewGroup enterView,
                                 boolean addToContainerView, @NonNull ViewGroup rootContainerView) {
            if (addToContainerView) {
                rootContainerView.addView(enterView);
            }

            enterLifeCycle.onStart();
            enterLifeCycle.onResume();
        }

        private boolean bothAnimationsSameTime(Uri enterKey, LifeCycle enterLifeCycle, Uri exitKey,
                                               LifeCycle exitLifeCycle) {
            // TODO
            return false;
        }

        @Nullable
        private Completable prepareExitAnimation(@NonNull View exitView,
                                                 @NonNull LifeCycle exitLifeCycle,
                                                 @Nullable RouteTransition outTransition) {
            // TODO
            return outTransition != null ?
                    Completable.create(new FadeCompletable(exitView, 1, 0, 5000, new LinearInterpolator())) :
                    null;
        }

        @Nullable
        private Completable prepareEnterAnimation(@NonNull View enterView,
                                                      @NonNull LifeCycle enterLifeCycle,
                                                      @Nullable RouteTransition inTransition) {
            // TODO
            return inTransition != null ?
                    Completable.create(new FadeCompletable(enterView, 0, 1, 5000, new LinearInterpolator())) :
                    null;
        }

        @Override
        public void cancel() {
            animationSubscription.unsubscribe();
        }
    }

}
