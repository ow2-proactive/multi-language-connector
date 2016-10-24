package org.ow2.proactive.procci.model.occi.infrastructure.action;

import java.util.HashSet;

import org.ow2.proactive.procci.model.occi.infrastructure.constants.Identifiers;
import org.ow2.proactive.procci.model.occi.metamodel.Attribute;

/**
 * Created by the Activeeon Team on 3/4/16.
 */

/**
 * Change a Storage instance state for Offline
 */
public final class OfflineStorage extends StorageAction {

    private static OfflineStorage OFFLINE_STORAGE = new OfflineStorage();

    private OfflineStorage() {
        super(Identifiers.STORAGE_ACTION_SCHEME, Identifiers.OFFLINE, Identifiers.OFFLINE,
                new HashSet<Attribute>());
    }

    public static OfflineStorage getInstance() {
        return OFFLINE_STORAGE;
    }
}
