package org.vr.app.activities.multi.lifecycles;

import org.vr.app.common.lifecycles.preconditions.PreconditionsDI;
import org.vr.app.common.lifecycles.preconditions.PreconditionsLifeCycle;

import dagger.Subcomponent;

/**
 * Created by vladimirrybkin on 28/01/2017.
 */
@PreconditionsDI.PreconditionsScope
@Subcomponent(modules = PreconditionsDI.PreconditionsModule.class)
public interface PreconditionsComponent {

    void inject(PreconditionsLifeCycle preconditionsLifeCycle);

}
