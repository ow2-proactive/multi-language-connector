package org.ow2.proactive.papi.model.infrastructure.action;

import java.util.HashSet;

import org.ow2.proactive.papi.model.infrastructure.constants.Identifiers;
import org.ow2.proactive.papi.model.metamodel.Attribute;

/**
 * Created by mael on 3/4/16.
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
