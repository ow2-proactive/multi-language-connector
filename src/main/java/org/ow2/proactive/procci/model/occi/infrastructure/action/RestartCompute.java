package org.ow2.proactive.procci.model.occi.infrastructure.action;

import java.util.HashSet;

import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureIdentifiers;
import org.ow2.proactive.procci.model.occi.metamodel.Attribute;


/**
 * Created by the Activeeon Team on 3/4/16.
 */

/**
 * Change a Compute instance state for Active
 */
public final class RestartCompute extends ComputeAction {

    private static RestartCompute RESTART_COMPUTE = new RestartCompute();

    private RestartCompute() {
        super(InfrastructureIdentifiers.COMPUTE_ACTION_SCHEME,
              InfrastructureIdentifiers.RESTART,
              InfrastructureIdentifiers.RESTART,
              new HashSet<Attribute>());
    }

    public static RestartCompute getInstance() {
        return RESTART_COMPUTE;
    }
}
