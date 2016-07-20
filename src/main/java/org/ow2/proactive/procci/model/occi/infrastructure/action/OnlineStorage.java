package org.ow2.proactive.procci.model.occi.infrastructure.action;

import org.ow2.proactive.procci.model.occi.infrastructure.constants.Identifiers;
import org.ow2.proactive.procci.model.occi.metamodel.Attribute;

import java.util.HashSet;

/**
 * Created by mael on 3/4/16.
 */

/**
 * Change a Storage instance state for Online
 */
public final class OnlineStorage extends StorageAction {

    private static OnlineStorage ONLINE_STORAGE = new OnlineStorage();

    private OnlineStorage() {
        super(Identifiers.STORAGE_ACTION_SCHEME, Identifiers.ONLINE, Identifiers.ONLINE,
                new HashSet<Attribute>());
    }

    public static OnlineStorage getInstance() {
        return ONLINE_STORAGE;
    }
}
