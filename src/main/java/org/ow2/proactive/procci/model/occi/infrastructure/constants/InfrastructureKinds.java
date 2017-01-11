package org.ow2.proactive.procci.model.occi.infrastructure.constants;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.ow2.proactive.procci.model.occi.infrastructure.Compute;
import org.ow2.proactive.procci.model.occi.infrastructure.Network;
import org.ow2.proactive.procci.model.occi.infrastructure.NetworkInterface;
import org.ow2.proactive.procci.model.occi.infrastructure.Storage;
import org.ow2.proactive.procci.model.occi.infrastructure.StorageLink;
import org.ow2.proactive.procci.model.occi.infrastructure.action.DownNetwork;
import org.ow2.proactive.procci.model.occi.infrastructure.action.OfflineStorage;
import org.ow2.proactive.procci.model.occi.infrastructure.action.OnlineStorage;
import org.ow2.proactive.procci.model.occi.infrastructure.action.RestartCompute;
import org.ow2.proactive.procci.model.occi.infrastructure.action.SaveCompute;
import org.ow2.proactive.procci.model.occi.infrastructure.action.StartCompute;
import org.ow2.proactive.procci.model.occi.infrastructure.action.StopCompute;
import org.ow2.proactive.procci.model.occi.infrastructure.action.SuspendCompute;
import org.ow2.proactive.procci.model.occi.infrastructure.action.UpNetwork;
import org.ow2.proactive.procci.model.occi.metamodel.Kind;
import org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelKinds;

/**
 * Created by the Activeeon Team on 3/2/16.
 */

/**
 * Contains and init all the Kind instances of the infrastructure
 */
public class InfrastructureKinds {

    public static final Kind COMPUTE = new Kind.Builder(InfrastructureIdentifiers.INFRASTRUCTURE_SCHEME,
            InfrastructureIdentifiers.COMPUTE)
            .addAttribute(Compute.getAttributes())
            .addParent(MetamodelKinds.RESOURCE)
            .addAction(RestartCompute.getInstance())
            .addAction(SaveCompute.getInstance())
            .addAction(StartCompute.getInstance())
            .addAction(SuspendCompute.getInstance())
            .addAction(StopCompute.getInstance())
            .build();
    public static final Kind NETWORK = new Kind.Builder(InfrastructureIdentifiers.INFRASTRUCTURE_SCHEME,
            InfrastructureIdentifiers.NETWORK)
            .addAttribute(Network.getAttributes())
            .addParent(MetamodelKinds.RESOURCE)
            .addAction(UpNetwork.getInstance())
            .addAction(DownNetwork.getInstance())
            .build();
    public static final Kind STORAGE = new Kind.Builder(InfrastructureIdentifiers.INFRASTRUCTURE_SCHEME,
            InfrastructureIdentifiers.STORAGE)
            .addAttribute(Storage.getAttributes())
            .addParent(MetamodelKinds.RESOURCE)
            .addAction(OfflineStorage.getInstance())
            .addAction(OnlineStorage.getInstance())
            .build();
    public static final Kind NETWORK_INTERFACE = new Kind.Builder(InfrastructureIdentifiers.INFRASTRUCTURE_SCHEME,
            InfrastructureIdentifiers.NETWORK_INTERFACE)
            .addAttribute(NetworkInterface.getAttributes())
            .addParent(MetamodelKinds.LINK)
            .build();
    public static final Kind STORAGE_LINK = new Kind.Builder(InfrastructureIdentifiers.INFRASTRUCTURE_SCHEME,
            InfrastructureIdentifiers.STORAGE_LINK)
            .addAttribute(StorageLink.getAttributes())
            .addParent(MetamodelKinds.LINK)
            .build();
    private static Map<String, Kind> INFRASTRUCTURE_KINDS = new HashMap<>();

    static {
        INFRASTRUCTURE_KINDS.put(InfrastructureIdentifiers.INFRASTRUCTURE_SCHEME+InfrastructureIdentifiers.COMPUTE, COMPUTE);
        INFRASTRUCTURE_KINDS.put(InfrastructureIdentifiers.INFRASTRUCTURE_SCHEME+InfrastructureIdentifiers.NETWORK, NETWORK);
        INFRASTRUCTURE_KINDS.put(InfrastructureIdentifiers.INFRASTRUCTURE_SCHEME+InfrastructureIdentifiers.STORAGE, STORAGE);
        INFRASTRUCTURE_KINDS.put(InfrastructureIdentifiers.INFRASTRUCTURE_SCHEME+InfrastructureIdentifiers.NETWORK_INTERFACE, NETWORK_INTERFACE);
        INFRASTRUCTURE_KINDS.put(InfrastructureIdentifiers.INFRASTRUCTURE_SCHEME+InfrastructureIdentifiers.STORAGE_LINK, STORAGE_LINK);
    }

    public static Optional<Kind> getKind(String term) {
        return Optional.ofNullable(INFRASTRUCTURE_KINDS.get(term));
    }

}
