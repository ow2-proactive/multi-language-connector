package org.ow2.proactive.procci.model.infrastructure.action;

import java.util.HashSet;

import org.ow2.proactive.procci.model.infrastructure.constants.Identifiers;
import org.ow2.proactive.procci.model.metamodel.Attribute;

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
