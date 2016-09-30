package org.ow2.proactive.procci.model.occi.infrastructure.action;

import java.util.HashSet;

import org.ow2.proactive.procci.model.occi.infrastructure.constants.Identifiers;
import org.ow2.proactive.procci.model.occi.metamodel.Attribute;

/**
 * Created by mael on 3/2/16.
 */

/**
 * Change a Compute instance state for Active
 */
public final class StartCompute extends ComputeAction {

    private static StartCompute START_COMPUTE = new StartCompute();

    private StartCompute() {
        super(Identifiers.COMPUTE_ACTION_SCHEME, Identifiers.START, Identifiers.START,
                new HashSet<Attribute>());
    }

    public static StartCompute getInstance() {
        return START_COMPUTE;
    }
}
