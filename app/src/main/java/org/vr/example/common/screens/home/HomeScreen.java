package org.vr.example.common.screens.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.vr.cycle.DefaultLifeCycle;
import org.vr.example.R;
import org.vr.example.common.screens.Injector;

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
