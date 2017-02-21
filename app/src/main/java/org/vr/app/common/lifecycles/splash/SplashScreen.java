package org.vr.app.common.lifecycles.splash;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import org.vr.app.R;
import org.vr.app.annotations.BootstrapRequired;
import org.vr.app.annotations.ParentLayout;
import org.vr.app.common.lifecycles.Injector;
import org.vr.cycle.DefaultLifeCycle;
import org.vr.cycle.LifeCycleKey;

import javax.inject.Inject;

@LifeCycleKey(value = "/screen/splash")
@BootstrapRequired(required = false)
@ParentLayout(layoutId = R.layout.screen_splash)
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
    public void onCreateView(@NonNull ViewGroup parentView, @Nullable Bundle persistantState,
                             @Nullable Bundle savedState) {
        super.onCreateView(parentView, persistantState, savedState);

        if (persistantState == null) {
            throw new RuntimeException("initiate state is null");
        }

        SplashScreenMeta.SplashScreenModule splashModule = new SplashScreenMeta
                .SplashScreenModule(persistantState);
        injector.inject(getContext(), this, splashModule);
        presenter.attachView(this);
    }

}
