package org.vr.app.activities.multi.lifecycles;

import org.vr.app.common.lifecycles.preconditions.PreconditionsLifeCycle;
import org.vr.app.common.lifecycles.preconditions.PreconditionsMeta;

import dagger.Subcomponent;

/**
 * Created by vladimirrybkin on 28/01/2017.
 */
@PreconditionsMeta.PreconditionsScope
@Subcomponent(modules = PreconditionsMeta.PreconditionsModule.class)
public interface PreconditionsComponent {

    void inject(PreconditionsLifeCycle preconditionsLifeCycle);

}
