package org.vr.app.common.lifecycles.splash;

import android.support.annotation.NonNull;

/**
 * Created by vladimirrybkin on 20/01/2017.
 */

public class SplashScreenContract {

    public interface SplashScreenView {

    }


    public interface SplashScreenPresenter {

        void attachView(@NonNull SplashScreenView screenView);

        void detachView();

    }

}
