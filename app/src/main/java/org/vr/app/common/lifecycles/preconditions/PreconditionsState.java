package org.vr.app.common.lifecycles.preconditions;

import android.os.Bundle;
import android.support.annotation.NonNull;

import org.vr.router.route.base.Route;

public final class PreconditionsState {

    private static final String CONTINUE_ROUTE = PreconditionsState.class.getSimpleName() + "#CONTINUE_ROUTE";
    private static final String INIT_ROUTE = PreconditionsState.class.getSimpleName() + "#INIT_ROUTE";

    public static Bundle state(@NonNull Bundle out,
                               @NonNull Route continueRoute,
                               @NonNull Route initRoute) {
        Bundle bundle = new Bundle();
        continueRoute.toBundle(bundle);
        out.putBundle(CONTINUE_ROUTE, bundle);

        bundle = new Bundle();
        initRoute.toBundle(bundle);
        out.putBundle(INIT_ROUTE, bundle);
        return out;
    }

    public static Route getContinueRoute(@NonNull Bundle persistantState) {
        if (persistantState.getBundle(CONTINUE_ROUTE) == null) {
            throw new RuntimeException("Illegal state");
        }

        return new Route(persistantState.getBundle(CONTINUE_ROUTE));
    }

    public static Route getInitRoute(@NonNull Bundle persistantState) {
        if (persistantState.getBundle(INIT_ROUTE) == null) {
            throw new RuntimeException("Illegal state");
        }

        return new Route(persistantState.getBundle(INIT_ROUTE));
    }

}
