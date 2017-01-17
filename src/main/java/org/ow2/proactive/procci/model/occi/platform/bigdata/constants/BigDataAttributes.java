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
package org.ow2.proactive.procci.model.occi.platform.bigdata.constants;

import org.ow2.proactive.procci.model.occi.metamodel.Attribute;
import org.ow2.proactive.procci.model.occi.metamodel.Type;


public class BigDataAttributes {

    public static final String SWARM_EXTENSION = "pca.platform.swarm";

    public static final String MACHINE_NAME_NAME = SWARM_EXTENSION + ".machineName";

    public static final String HOST_IP_NAME = SWARM_EXTENSION + ".hostIP";

    public static final String MASTER_IP_NAME = SWARM_EXTENSION + ".masterIP";

    public static final String AGENTS_IP_NAME = SWARM_EXTENSION + ".agentsIP";

    public static final String NETWORK_NAME_NAME = SWARM_EXTENSION + ".networkName";

    public static final Attribute MACHINE_NAME = new Attribute.Builder(MACHINE_NAME_NAME).required(true)
                                                                                         .mutable(false)
                                                                                         .type(Type.OBJECT)
                                                                                         .description("The machine host name")
                                                                                         .defaultValue("swarmMachine")
                                                                                         .build();

    public static final Attribute HOST_IP = new Attribute.Builder(HOST_IP_NAME).required(true)
                                                                               .mutable(false)
                                                                               .type(Type.OBJECT)
                                                                               .description("The machine host ip")
                                                                               .build();

    public static final Attribute MASTER_IP = new Attribute.Builder(MASTER_IP_NAME).required(true)
                                                                                   .mutable(false)
                                                                                   .type(Type.OBJECT)
                                                                                   .description("The master machine ip")
                                                                                   .build();

    public static final Attribute AGENTS_IP = new Attribute.Builder(AGENTS_IP_NAME).required(true)
                                                                                   .mutable(false)
                                                                                   .type(Type.LIST)
                                                                                   .description("The agent machines ip")
                                                                                   .build();


    public static final Attribute NETWORK_NAME = new Attribute.Builder(NETWORK_NAME_NAME).required(false)
                                                                                         .mutable(false)
                                                                                         .type(Type.OBJECT)
                                                                                         .description("The network linking the machines name")
                                                                                         .defaultValue("my-net")
                                                                                         .build();

}
