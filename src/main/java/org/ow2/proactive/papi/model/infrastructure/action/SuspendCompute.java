package org.ow2.proactive.papi.model.infrastructure.action;

import java.util.HashSet;

import org.ow2.proactive.papi.model.infrastructure.constants.Identifiers;
import org.ow2.proactive.papi.model.metamodel.Attribute;

/**
 * Created by mael on 3/4/16.
 */

/**
 * Change a Compute instance state for Suspended
 */
public final class SuspendCompute extends ComputeAction {

    private static SuspendCompute SUSPEND_COMPUTE = new SuspendCompute();

    private SuspendCompute() {
        super(Identifiers.COMPUTE_ACTION_SCHEME, Identifiers.SUSPEND, Identifiers.SUSPEND,
                new HashSet<Attribute>());
    }

    public static SuspendCompute getInstance() {
        return SUSPEND_COMPUTE;
    }
}
