package org.vr.app.bootstrap;

import dagger.Subcomponent;

/**
 * Created by vladimirrybkin on 11/01/2017.
 */
@BootstrapMeta.BootstrapScope
@Subcomponent(modules = {
        BootstrapMeta.BootstrapModule.class
})
public interface BootstrapComponent {

}
