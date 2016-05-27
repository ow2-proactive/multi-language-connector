package org.ow2.proactive.procci.model.infrastructure.action;

import java.util.HashSet;

import org.ow2.proactive.procci.model.infrastructure.constants.Identifiers;
import org.ow2.proactive.procci.model.metamodel.Attribute;

/**
 * Created by mael on 3/4/16.
 */

/**
 * Change a Network instance state for Active
 */
public final class UpNetwork extends NetworkAction {

    private static UpNetwork UP_NETWORK = new UpNetwork();

    private UpNetwork() {
        super(Identifiers.NETWORK_ACTION_SCHEME, Identifiers.UP, Identifiers.UP, new HashSet<Attribute>());
    }

    public static UpNetwork getInstance() {
        return UP_NETWORK;
    }
}
