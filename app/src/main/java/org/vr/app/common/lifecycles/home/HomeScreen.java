package org.vr.app.common.lifecycles.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.vr.app.R;
import org.vr.app.annotations.DrawerEnabled;
import org.vr.app.annotations.ParentLayout;
import org.vr.app.bootstrap.BootstrapConsumer;
import org.vr.app.common.toolbar.ToolbarPresenter;
import org.vr.cycle.DefaultLifeCycle;
import org.vr.cycle.LifeCycleKey;

import javax.inject.Inject;

@LifeCycleKey(value = "/screen/home")
@ParentLayout(layoutId = R.layout.screen_home)
@DrawerEnabled(required = true)
public class HomeScreen extends DefaultLifeCycle {

    public static final String EXTRA_NUMBER = HomeScreen.class.getSimpleName() + "#EXTRA_NUMBER";

    @NonNull
    private final HomeScreenDI.Injector injector;

    private int number = 0;

    @Inject
    BootstrapConsumer bootstrapConsumer;

    @Inject
    ToolbarPresenter toolbarOwner;

    public HomeScreen(@NonNull HomeScreenDI.Injector injector) {
        this.injector = injector;
    }

    @Override
    public void onCreateView(@NonNull ViewGroup parentView, @Nullable Bundle persistantState,
                             @Nullable Bundle savedState) {
        injector.inject(getContext(), this, new HomeScreenDI.HomeScreenModule(), parentView);

        if (savedState != null) {
            number = savedState.getInt(EXTRA_NUMBER);
        }

        toolbarOwner.setTitle(R.string.home_title).enableHome(true);

        TextView text = (TextView) parentView.findViewById(R.id.home_number);
        text.setText("restore " + Integer.toString(number));

        Button clearBootstrap = (Button) parentView.findViewById(R.id.home_clear_bootstrap);
        clearBootstrap.setOnClickListener(view -> bootstrapConsumer.clear());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle out) {
        out.putInt(EXTRA_NUMBER, number + 1);
    }

}
