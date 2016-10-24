package org.ow2.proactive.procci.model.occi.infrastructure.action;

import java.util.HashSet;

import org.ow2.proactive.procci.model.occi.infrastructure.constants.Identifiers;
import org.ow2.proactive.procci.model.occi.metamodel.Attribute;

/**
 * Created by the Activeeon Team on 3/4/16.
 */

/**
 * Change a Network instance state for Inactive
 */
public final class DownNetwork extends NetworkAction {

    private static DownNetwork DOWN_NETWORK = new DownNetwork();

    private DownNetwork() {
        super(Identifiers.NETWORK_ACTION_SCHEME, Identifiers.DOWN, Identifiers.DOWN,
                new HashSet<Attribute>());
    }

    public static DownNetwork getInstance() {
        return DOWN_NETWORK;
    }
}
