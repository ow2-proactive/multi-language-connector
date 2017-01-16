package org.ow2.proactive.procci.model.occi.infrastructure.action;

import java.util.HashSet;

import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureIdentifiers;
import org.ow2.proactive.procci.model.occi.metamodel.Attribute;

/**
 * Created by the Activeeon Team on 3/4/16.
 */

/**
 * Change a Compute instance state for Suspended
 */
public final class SuspendCompute extends ComputeAction {

    private static SuspendCompute SUSPEND_COMPUTE = new SuspendCompute();

    private SuspendCompute() {
        super(InfrastructureIdentifiers.COMPUTE_ACTION_SCHEME, InfrastructureIdentifiers.SUSPEND, InfrastructureIdentifiers.SUSPEND,
                new HashSet<Attribute>());
    }

    public static SuspendCompute getInstance() {
        return SUSPEND_COMPUTE;
    }
}
