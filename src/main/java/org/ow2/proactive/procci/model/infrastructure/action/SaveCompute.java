package org.ow2.proactive.procci.model.infrastructure.action;

import java.util.HashSet;

import org.ow2.proactive.procci.model.infrastructure.constants.Identifiers;
import org.ow2.proactive.procci.model.metamodel.Attribute;

/**
 * Created by mael on 3/4/16.
 */

/**
 * Change a Compute instance state for Active
 */
public final class SaveCompute extends ComputeAction {

    private static SaveCompute SAVE_COMPUTE = new SaveCompute();

    private SaveCompute() {
        super(Identifiers.COMPUTE_ACTION_SCHEME, Identifiers.SAVE, Identifiers.SAVE,
                new HashSet<Attribute>());
    }

    public static SaveCompute getInstance() {
        return SAVE_COMPUTE;
    }
}
