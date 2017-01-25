package org.vr.app.activities.several.router;

import org.vr.di.DiRouterModule;
import org.vr.app.common.routes.RoutesModule;
import org.vr.router.Router;

import dagger.Subcomponent;

/**
 * Created by vladimirrybkin on 11/01/2017.
 */
@Subcomponent(modules = {DiRouterModule.class, RoutesModule.class})
public interface RouterComponent {

    void inject(ExampleActivityRouter exampleActivityRouter);

    Router router();

}
