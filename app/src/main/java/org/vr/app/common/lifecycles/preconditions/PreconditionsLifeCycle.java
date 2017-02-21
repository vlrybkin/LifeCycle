package org.vr.app.common.lifecycles.preconditions;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import org.vr.app.annotations.BootstrapRequired;
import org.vr.app.bootstrap.BootstrapConsumer;
import org.vr.app.common.lifecycles.Injector;
import org.vr.cycle.DefaultLifeCycle;
import org.vr.cycle.LifeCycleKey;
import org.vr.router.route.base.Route;

import javax.inject.Inject;

/**
 * Created by vladimirrybkin on 01/11/16.
 */
@LifeCycleKey(value = "/preconditions/default")
@BootstrapRequired(required = false)
final public class PreconditionsLifeCycle extends DefaultLifeCycle {

    @NonNull
    private final Injector<PreconditionsLifeCycle> injector;

    @PreconditionsMeta.ContinueRoute
    @Inject
    protected Route backRoute;

    @Inject
    protected BootstrapConsumer bootstrapConsumer;

    @PreconditionsMeta.InitRoute
    @Inject
    protected Route splashScreenRoute;

    @PreconditionsMeta.CheckBootstrap
    @Inject
    protected Boolean checkBootstrap;

    public PreconditionsLifeCycle(@NonNull Injector<PreconditionsLifeCycle> injector) {
        this.injector = injector;
    }

    @Override
    public void onCreateView(@NonNull ViewGroup parentView, @Nullable Bundle persistantState,
                             @Nullable Bundle savedState) {
        super.onCreateView(parentView, persistantState, savedState);

        if (persistantState == null) {
            throw new RuntimeException("The lifecycle initial state is undefined");
        }

        PreconditionsMeta.PreconditionsModule preconditionsModule =
                new PreconditionsMeta.PreconditionsModule(persistantState);
        injector.inject(getContext(), this, preconditionsModule);

        if (checkBootstrap && bootstrapConsumer.needBootstrapUpdate()) {
            splashScreenRoute.go(getContext());
        } else {
            backRoute.go(getContext());
        }
    }

}
