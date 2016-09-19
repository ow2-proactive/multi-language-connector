package org.ow2.proactive.procci.model.occi.infrastructure.constants;

import org.ow2.proactive.procci.model.occi.infrastructure.Compute;
import org.ow2.proactive.procci.model.occi.infrastructure.Network;
import org.ow2.proactive.procci.model.occi.infrastructure.NetworkInterface;
import org.ow2.proactive.procci.model.occi.infrastructure.StorageLink;
import org.ow2.proactive.procci.model.occi.infrastructure.action.*;
import org.ow2.proactive.procci.model.occi.metamodel.Kind;
import org.ow2.proactive.procci.model.occi.metamodel.constants.Kinds;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mael on 3/2/16.
 */

/**
 * Contains and init all the Kind instances of the infrastructure
 */
public class InfrastructureKinds {

    public static final Kind COMPUTE = new Kind.Builder(Identifiers.INFRASTRUCTURE_SCHEME,
            Identifiers.COMPUTE)
            .addAttribute(Compute.getAttributes())
            .addParent(Kinds.RESOURCE)
            .addAction(RestartCompute.getInstance())
            .addAction(SaveCompute.getInstance())
            .addAction(StartCompute.getInstance())
            .addAction(SuspendCompute.getInstance())
            .addAction(StopCompute.getInstance())
            .build();

    public static final Kind NETWORK = new Kind.Builder(Identifiers.INFRASTRUCTURE_SCHEME,
            Identifiers.NETWORK)
            .addAttribute(Network.getAttributes())
            .addParent(Kinds.RESOURCE)
            .addAction(UpNetwork.getInstance())
            .addAction(DownNetwork.getInstance())
            .build();

    public static final Kind STORAGE = new Kind.Builder(Identifiers.INFRASTRUCTURE_SCHEME,
            Identifiers.STORAGE)
            .addAttribute(Network.getAttributes())
            .addParent(Kinds.RESOURCE)
            .addAction(OfflineStorage.getInstance())
            .addAction(OnlineStorage.getInstance())
            .build();

    public static final Kind NETWORK_INTERFACE = new Kind.Builder(Identifiers.INFRASTRUCTURE_SCHEME,
            Identifiers.NETWORK_INTERFACE)
            .addAttribute(NetworkInterface.getAttributes())
            .addParent(Kinds.LINK)
            .build();

    public static final Kind STORAGE_LINK = new Kind.Builder(Identifiers.INFRASTRUCTURE_SCHEME,
            Identifiers.STORAGE_LINK)
            .addAttribute(StorageLink.getAttributes())
            .addParent(Kinds.LINK)
            .build();

    public static Map<String,Kind> INFRASTRUCTURE_KINDS = new HashMap<>();
    static{
        INFRASTRUCTURE_KINDS.put(Identifiers.INFRASTRUCTURE_SCHEME+Identifiers.COMPUTE,COMPUTE);
        INFRASTRUCTURE_KINDS.put(Identifiers.INFRASTRUCTURE_SCHEME+Identifiers.NETWORK,NETWORK);
        INFRASTRUCTURE_KINDS.put(Identifiers.INFRASTRUCTURE_SCHEME+Identifiers.STORAGE,STORAGE);
        INFRASTRUCTURE_KINDS.put(Identifiers.INFRASTRUCTURE_SCHEME+Identifiers.NETWORK_INTERFACE,NETWORK_INTERFACE);
        INFRASTRUCTURE_KINDS.put(Identifiers.INFRASTRUCTURE_SCHEME+Identifiers.STORAGE_LINK,STORAGE_LINK);
    }
}
