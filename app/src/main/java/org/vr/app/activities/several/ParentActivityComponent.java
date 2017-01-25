package org.vr.app.activities.several;

import org.vr.app.activities.several.router.RouterComponent;
import org.vr.app.common.routes.RoutesModule;
import org.vr.di.ActivityScope;
import org.vr.di.DiActivityModule;
import org.vr.di.DiRouterModule;

import dagger.Subcomponent;

/**
 * Created by vladimirrybkin on 25/01/2017.
 */
@ActivityScope
@Subcomponent(modules = DiActivityModule.class)
public interface ParentActivityComponent {

    RouterComponent createRouterComponent(DiRouterModule routerModule, RoutesModule routesModule);

}
