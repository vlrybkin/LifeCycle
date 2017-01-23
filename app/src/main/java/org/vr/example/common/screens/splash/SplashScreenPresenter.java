package org.vr.example.common.screens.splash;

import android.support.annotation.NonNull;

import org.vr.example.bootstrap.BootstrapConsumer;
import org.vr.example.bootstrap.DummyBootstrap;
import org.vr.router.command.Route;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

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
        Observable.just("")
                .delay(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    bootstrapConsumer.setup(new DummyBootstrap());
                    routeBack.go();
                });
    }

    @Override
    public void detachView() {

    }

}
