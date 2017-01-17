/*
 * ProActive Parallel Suite(TM):
 * The Open Source library for parallel and distributed
 * Workflows & Scheduling, Orchestration, Cloud Automation
 * and Big Data Analysis on Enterprise Grids & Clouds.
 *
 * Copyright (c) 2007 - 2017 ActiveEon
 * Contact: contact@activeeon.com
 *
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation: version 3 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 */
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
                                                        InfrastructureIdentifiers.COMPUTE).addAttribute(Compute.getAttributes())
                                                                                          .addParent(MetamodelKinds.RESOURCE)
                                                                                          .addAction(RestartCompute.getInstance())
                                                                                          .addAction(SaveCompute.getInstance())
                                                                                          .addAction(StartCompute.getInstance())
                                                                                          .addAction(SuspendCompute.getInstance())
                                                                                          .addAction(StopCompute.getInstance())
                                                                                          .build();

    public static final Kind NETWORK = new Kind.Builder(InfrastructureIdentifiers.INFRASTRUCTURE_SCHEME,
                                                        InfrastructureIdentifiers.NETWORK).addAttribute(Network.getAttributes())
                                                                                          .addParent(MetamodelKinds.RESOURCE)
                                                                                          .addAction(UpNetwork.getInstance())
                                                                                          .addAction(DownNetwork.getInstance())
                                                                                          .build();

    public static final Kind STORAGE = new Kind.Builder(InfrastructureIdentifiers.INFRASTRUCTURE_SCHEME,
                                                        InfrastructureIdentifiers.STORAGE).addAttribute(Storage.getAttributes())
                                                                                          .addParent(MetamodelKinds.RESOURCE)
                                                                                          .addAction(OfflineStorage.getInstance())
                                                                                          .addAction(OnlineStorage.getInstance())
                                                                                          .build();

    public static final Kind NETWORK_INTERFACE = new Kind.Builder(InfrastructureIdentifiers.INFRASTRUCTURE_SCHEME,
                                                                  InfrastructureIdentifiers.NETWORK_INTERFACE).addAttribute(NetworkInterface.getAttributes())
                                                                                                              .addParent(MetamodelKinds.LINK)
                                                                                                              .build();

    public static final Kind STORAGE_LINK = new Kind.Builder(InfrastructureIdentifiers.INFRASTRUCTURE_SCHEME,
                                                             InfrastructureIdentifiers.STORAGE_LINK).addAttribute(StorageLink.getAttributes())
                                                                                                    .addParent(MetamodelKinds.LINK)
                                                                                                    .build();

    private static Map<String, Kind> INFRASTRUCTURE_KINDS = new HashMap<>();

    static {
        INFRASTRUCTURE_KINDS.put(InfrastructureIdentifiers.INFRASTRUCTURE_SCHEME + InfrastructureIdentifiers.COMPUTE,
                                 COMPUTE);
        INFRASTRUCTURE_KINDS.put(InfrastructureIdentifiers.INFRASTRUCTURE_SCHEME + InfrastructureIdentifiers.NETWORK,
                                 NETWORK);
        INFRASTRUCTURE_KINDS.put(InfrastructureIdentifiers.INFRASTRUCTURE_SCHEME + InfrastructureIdentifiers.STORAGE,
                                 STORAGE);
        INFRASTRUCTURE_KINDS.put(InfrastructureIdentifiers.INFRASTRUCTURE_SCHEME +
                                 InfrastructureIdentifiers.NETWORK_INTERFACE, NETWORK_INTERFACE);
        INFRASTRUCTURE_KINDS.put(InfrastructureIdentifiers.INFRASTRUCTURE_SCHEME +
                                 InfrastructureIdentifiers.STORAGE_LINK, STORAGE_LINK);
    }

    public static Optional<Kind> getKind(String term) {
        return Optional.ofNullable(INFRASTRUCTURE_KINDS.get(term));
    }

}
