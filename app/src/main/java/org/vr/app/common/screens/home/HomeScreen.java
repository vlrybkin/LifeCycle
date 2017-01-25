package org.vr.app.common.screens.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.vr.app.R;
import org.vr.app.common.screens.Injector;
import org.vr.cycle.DefaultLifeCycle;

import static java.security.AccessController.getContext;

/**
 * Created by vladimirrybkin on 21/11/16.
 */
public class HomeScreen extends DefaultLifeCycle {

    @NonNull
    private final Injector<HomeScreen> injector;

    public HomeScreen(@NonNull Injector<HomeScreen> injector) {
        this.injector = injector;
    }

    @Override
    public void onCreateView(@NonNull ViewGroup parentView, @Nullable Bundle initialState,
                             @Nullable Bundle savedState) {
        LayoutInflater.from(parentView.getContext())
                .inflate(R.layout.screen_home, parentView);
        injector.inject(getContext(), this, new HomeScreenMeta.HomeScreenModule());
    }

}
