package org.ow2.proactive.procci.model.occi.infrastructure.action;

import java.util.HashSet;

import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureIdentifiers;
import org.ow2.proactive.procci.model.occi.metamodel.Attribute;

/**
 * Created by the Activeeon Team on 3/4/16.
 */

/**
 * Change a Storage instance state for Online
 */
public final class OnlineStorage extends StorageAction {

    private static OnlineStorage ONLINE_STORAGE = new OnlineStorage();

    private OnlineStorage() {
        super(InfrastructureIdentifiers.STORAGE_ACTION_SCHEME, InfrastructureIdentifiers.ONLINE, InfrastructureIdentifiers.ONLINE,
                new HashSet<Attribute>());
    }

    public static OnlineStorage getInstance() {
        return ONLINE_STORAGE;
    }
}
