package org.vr.example.common.screens;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.vr.cycle.LifeCycle;
import org.vr.cycle.LifecycleWrapper;
import org.vr.di.DiContextWrapper;
import org.vr.di.DiNames;
import org.vr.example.R;

/**
 * Created by vladimirrybkin on 11/01/2017.
 */

public class RootScreen extends LifecycleWrapper {

    private DiContextWrapper contextWrapper;

    @NonNull
    private final Injector<RootScreen> injector;

    @NonNull
    private final LifeCycle nestedLifecycle;

    public RootScreen(@NonNull Injector<RootScreen> injector, @NonNull LifeCycle nestedLifecycle) {
        this.injector = injector;
        this.nestedLifecycle = nestedLifecycle;
    }

    @NonNull
    @Override
    public Context attachBaseContext(@NonNull Context context) {
        contextWrapper = new DiContextWrapper(context, false);
        return super.attachBaseContext(contextWrapper);
    }

    @Override
    public void onCreateView(@NonNull ViewGroup parentView, @Nullable Bundle initialState,
                             @Nullable Bundle savedState) {
        LayoutInflater.from(parentView.getContext())
                .inflate(R.layout.several_activities_root, parentView, true);
        ViewGroup contentContainer = (ViewGroup) parentView.findViewById(R.id.content_container);
        LinearLayout toolbarContainerView = (LinearLayout) parentView.findViewById(R.id.toolbar_container);
        contextWrapper.addComponent(DiNames.ROOT_SCREEN_COMPONENT, initComponent(toolbarContainerView));
        attach(nestedLifecycle, initialState, savedState);
        super.onCreateView(contentContainer, initialState, savedState);
    }

    @NonNull
    protected Object initComponent(@NonNull LinearLayout toolbarContainerView) {
        RootScreenMeta.RootScreenModule screenModule =
                new RootScreenMeta.RootScreenModule(toolbarContainerView);
        return injector.inject(getContext(), this, screenModule);
    }

}
