package org.ow2.proactive.papi.model.infrastructure.constants;

import org.ow2.proactive.papi.model.infrastructure.Compute;
import org.ow2.proactive.papi.model.infrastructure.Network;
import org.ow2.proactive.papi.model.infrastructure.NetworkInterface;
import org.ow2.proactive.papi.model.infrastructure.StorageLink;
import org.ow2.proactive.papi.model.infrastructure.action.DownNetwork;
import org.ow2.proactive.papi.model.infrastructure.action.OfflineStorage;
import org.ow2.proactive.papi.model.infrastructure.action.OnlineStorage;
import org.ow2.proactive.papi.model.infrastructure.action.RestartCompute;
import org.ow2.proactive.papi.model.infrastructure.action.SaveCompute;
import org.ow2.proactive.papi.model.infrastructure.action.StartCompute;
import org.ow2.proactive.papi.model.infrastructure.action.StopCompute;
import org.ow2.proactive.papi.model.infrastructure.action.SuspendCompute;
import org.ow2.proactive.papi.model.infrastructure.action.UpNetwork;
import org.ow2.proactive.papi.model.metamodel.Kind;
import org.ow2.proactive.papi.model.metamodel.constants.Kinds;

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
}
