package org.vr.app.common.lifecycles.splash;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

import org.vr.app.bootstrap.BootstrapConsumer;
import org.vr.app.bootstrap.DummyBootstrap;
import org.vr.router.route.base.Route;

/**
 * Created by vladimirrybkin on 20/01/2017.
 */
public class SplashScreenPresenter implements SplashScreenContract.SplashScreenPresenter {

    @NonNull
    private final Context context;

    @NonNull
    private final BootstrapConsumer bootstrapConsumer;

    @NonNull
    private final Route routeBack;

    public SplashScreenPresenter(@NonNull Context context,
            @NonNull BootstrapConsumer bootstrapConsumer,
            @NonNull Route routeBack) {
        this.bootstrapConsumer = bootstrapConsumer;
        this.routeBack = routeBack;
        this.context = context;
    }

    @Override
    public void attachView(@NonNull SplashScreenContract.SplashScreenView screenView) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            bootstrapConsumer.setup(new DummyBootstrap());
            routeBack.go(context);
        }, 5000);
    }

    @Override
    public void detachView() {

    }

}
