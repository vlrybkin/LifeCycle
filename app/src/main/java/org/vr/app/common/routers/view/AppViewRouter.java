package org.vr.app.common.routers.view;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import org.vr.app.common.lifecycles.LifeCyclesFactory;
import org.vr.cycle.LifeCycle;
import org.vr.framework.router.view.ViewRouter;

import javax.inject.Inject;

/**
 * Created by vladimirrybkin on 28/01/2017.
 */
public class AppViewRouter extends ViewRouter {

    @NonNull
    private final LifeCyclesFactory lifeCyclesFactory;

    @Inject
    public AppViewRouter(@NonNull Context context,
                         @NonNull ViewGroup containerView,
                         @NonNull LifeCyclesFactory lifeCyclesFactory) {
        super(context, containerView);
        this.lifeCyclesFactory = lifeCyclesFactory;
    }

    @NonNull
    @Override
    public LifeCycle getLifeCycle(@NonNull Uri key) {
        return lifeCyclesFactory.create(key.getPath());
    }

}
