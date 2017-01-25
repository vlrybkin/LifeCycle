package org.vr.app.common.screens;

import android.support.annotation.NonNull;
import android.widget.LinearLayout;

import org.vr.app.common.toolbar.DefaultToolbarOwner;
import org.vr.app.common.toolbar.ToolbarOwner;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

/**
 * Created by vladimirrybkin on 17/01/2017.
 */

public class RootScreenMeta {

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface RootScreenQualifier {
    }

    @Component(modules = RootScreenModule.class)
    public interface RootScreenComponent {

        void inject(@NonNull RootScreen rootScreen);

    }

    @Module
    public static class RootScreenModule {

        @NonNull
        private final ToolbarOwner toolbarOwner;

        public RootScreenModule(@NonNull LinearLayout toolbarContainerView) {
            toolbarOwner = new DefaultToolbarOwner(toolbarContainerView);
        }

        @Provides
        public ToolbarOwner provideToolbarOwner() {
            return toolbarOwner;
        }

    }

}
