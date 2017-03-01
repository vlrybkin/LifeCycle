package org.vr.app.common.lifecycles.preconditions;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import org.vr.app.annotations.BootstrapRequired;
import org.vr.app.bootstrap.BootstrapConsumer;
import org.vr.cycle.DefaultLifeCycle;
import org.vr.cycle.LifeCycleKey;
import org.vr.router.route.base.Route;

import javax.inject.Inject;

@LifeCycleKey(value = "/preconditions/default")
@BootstrapRequired(required = false)
final public class PreconditionsLifeCycle extends DefaultLifeCycle {

    @NonNull
    private final PreconditionsDI.Injector injector;

    @PreconditionsDI.ContinueRoute
    @Inject
    protected Route backRoute;

    @Inject
    protected BootstrapConsumer bootstrapConsumer;

    @PreconditionsDI.InitRoute
    @Inject
    protected Route splashScreenRoute;

    @PreconditionsDI.CheckBootstrap
    @Inject
    protected Boolean checkBootstrap;

    public PreconditionsLifeCycle(@NonNull PreconditionsDI.Injector injector) {
        this.injector = injector;
    }

    @Override
    public void onCreateView(@NonNull ViewGroup parentView, @Nullable Bundle persistantState,
                             @Nullable Bundle savedState) {
        super.onCreateView(parentView, persistantState, savedState);

        if (persistantState == null) {
            throw new RuntimeException("The lifecycle initial state is undefined");
        }

        PreconditionsDI.PreconditionsModule preconditionsModule =
                new PreconditionsDI.PreconditionsModule(persistantState);
        injector.inject(getContext(), this, preconditionsModule);
    }

    @Override
    public void onStart() {
        if (checkBootstrap && bootstrapConsumer.needBootstrapUpdate()) {
            splashScreenRoute.go(getContext());
        } else {
            backRoute.go(getContext());
        }
    }

}
