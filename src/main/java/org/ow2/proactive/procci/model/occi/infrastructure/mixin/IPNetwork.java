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
package org.ow2.proactive.procci.model.occi.infrastructure.mixin;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureAttributes;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureIdentifiers;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.occi.metamodel.Attribute;
import org.ow2.proactive.procci.model.occi.metamodel.Entity;
import org.ow2.proactive.procci.model.occi.metamodel.Kind;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;

import lombok.Getter;


/**
 * This class supports L3/L4 capabilities with the Network class
 */
@Getter
public class IPNetwork extends Mixin {

    private String address;

    private String gateway;

    //if dynamic allocation is true then the allocation is dynamic otherwise it is static
    private boolean dynamicAllocation;

    /**
     * Constructor with all the parameters
     *
     * @param address  is the IP network address
     * @param gateway  is the IP adress
     * @param dynamic  defines the allocation protocol
     * @param entities is the set of resource instances
     */
    public IPNetwork(String address, String gateway, boolean dynamic, List<Entity> entities)
            throws UnknownHostException {
        super(InfrastructureIdentifiers.NETWORK_SCHEME,
              InfrastructureIdentifiers.IPNETWORK,
              InfrastructureIdentifiers.IPNETWORK,
              createAttributesSet(),
              new ArrayList<>(),
              new ArrayList<>(),
              setApplies(),
              entities);
        this.address = address;
        this.gateway = gateway;
        this.dynamicAllocation = dynamic;
    }

    private static List<Kind> setApplies() {
        List<Kind> applies = new ArrayList<>();
        applies.add(InfrastructureKinds.NETWORK);
        return applies;
    }

    private static Set<Attribute> createAttributesSet() {
        HashSet<Attribute> attributes = new HashSet<>();
        attributes.add(InfrastructureAttributes.NETWORK_ADDRESS);
        attributes.add(InfrastructureAttributes.NETWORK_ALLOCATION);
        attributes.add(InfrastructureAttributes.NETWORK_GATEWAY);
        return attributes;
    }

}
