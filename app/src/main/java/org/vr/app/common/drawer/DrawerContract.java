package org.vr.app.common.drawer;

import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;

/**
 * Created by vladimirrybkin on 17/02/2017.
 */

public interface DrawerContract {

    interface View {

        void selectItem(@IdRes int id);

    }

    interface Presenter {

        void attachView(@NonNull View view);

        void detachView();

        boolean onItemSelected(@IdRes int id);

        void highlightItem(@NonNull Uri enterKey);
    }

}
