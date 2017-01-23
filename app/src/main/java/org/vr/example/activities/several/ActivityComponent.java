package org.vr.example.activities.several;

import org.vr.di.ActivityScope;
import org.vr.di.DiActivityModule;
import org.vr.di.DiRouterModule;
import org.vr.example.activities.several.router.RouterComponent;
import org.vr.example.common.routes.RoutesModule;

import dagger.Subcomponent;

/**
 * Created by vladimirrybkin on 11/01/2017.
 */
@ActivityScope
@Subcomponent(modules = DiActivityModule.class)
public interface ActivityComponent {

    RouterComponent createRouterComponent(DiRouterModule routerModule, RoutesModule routesModule);

}
