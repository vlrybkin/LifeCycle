package org.vr.app.common.lifecycles.splash;

import android.os.Bundle;
import android.support.annotation.NonNull;

import org.vr.router.route.base.Route;

/**
 * Created by vladimirrybkin on 01/03/2017.
 */
public final class SplashScreenState {

    private static final String ROUTE = SplashScreenState.class.getSimpleName() + "#ROUTE";

    public static Bundle state(@NonNull Bundle out, @NonNull Route route) {
        Bundle bundle = new Bundle();
        route.toBundle(bundle);
        out.putBundle(ROUTE, bundle);
        return out;
    }

    public static Route getInitialLifeCycleRoute(@NonNull Bundle persistantState) {
        if (persistantState.getBundle(ROUTE) == null) {
            throw new RuntimeException("Illegal state");
        }

        return new Route(persistantState.getBundle(ROUTE));
    }

}
