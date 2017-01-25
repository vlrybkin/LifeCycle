package org.vr.app.common.screens.splash;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import org.vr.cycle.DefaultLifeCycle;
import org.vr.app.annotations.BootstrapRequired;
import org.vr.app.annotations.RootScreenRequired;
import org.vr.app.common.screens.Injector;

import javax.inject.Inject;

/**
 * Created by vladimirrybkin on 01/11/16.
 */
@BootstrapRequired(required = false)
@RootScreenRequired(required = false)
public class SplashScreen extends DefaultLifeCycle implements SplashScreenContract.SplashScreenView {

    @NonNull
    private final Injector<SplashScreen> injector;

    @NonNull
    private SplashScreenContract.SplashScreenPresenter presenter;

    public SplashScreen(@NonNull Injector<SplashScreen> injector) {
        this.injector = injector;
    }

    @Inject
    public void setPresenter(@NonNull SplashScreenContract.SplashScreenPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCreateView(@NonNull ViewGroup parentView, @Nullable Bundle initialState,
                             @Nullable Bundle savedState) {
        super.onCreateView(parentView, initialState, savedState);

        if (initialState == null) {
            throw new RuntimeException("initiate state is null");
        }

        SplashScreenMeta.SplashScreenModule splashModule = new SplashScreenMeta
                .SplashScreenModule(initialState);
        injector.inject(getContext(), this, splashModule);
        presenter.attachView(this);
    }

}
