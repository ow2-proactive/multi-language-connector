package org.ow2.proactive.procci.model.occi.infrastructure.action;

import org.ow2.proactive.procci.model.occi.infrastructure.constants.Identifiers;
import org.ow2.proactive.procci.model.occi.metamodel.Attribute;

import java.util.HashSet;

/**
 * Created by mael on 3/4/16.
 */

/**
 * Change a Compute instance state for Active
 */
public final class RestartCompute extends ComputeAction {

    private static RestartCompute RESTART_COMPUTE = new RestartCompute();

    private RestartCompute() {
        super(Identifiers.COMPUTE_ACTION_SCHEME, Identifiers.RESTART, Identifiers.RESTART,
                new HashSet<Attribute>());
    }

    public static RestartCompute getInstance() {
        return RESTART_COMPUTE;
    }
}
