package org.vr.app.common.screens.splash;

import android.os.Handler;
import android.support.annotation.NonNull;

import org.vr.app.bootstrap.BootstrapConsumer;
import org.vr.app.bootstrap.DummyBootstrap;
import org.vr.router.command.Route;

/**
 * Created by vladimirrybkin on 20/01/2017.
 */
public class SplashScreenPresenter implements SplashScreenContract.SplashScreenPresenter {

    @NonNull
    private final BootstrapConsumer bootstrapConsumer;

    @NonNull
    private final Route routeBack;

    public SplashScreenPresenter(@NonNull BootstrapConsumer bootstrapConsumer,
                                 @NonNull Route routeBack) {
        this.bootstrapConsumer = bootstrapConsumer;
        this.routeBack = routeBack;
    }


    @Override
    public void attachView(@NonNull SplashScreenContract.SplashScreenView screenView) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bootstrapConsumer.setup(new DummyBootstrap());
                routeBack.go();
            }
        }, 5000);
    }

    @Override
    public void detachView() {

    }

}
