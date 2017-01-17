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

/**
 * Created by the Activeeon Team on 3/2/16.
 */

/**
 * Contains all the identifiers for the infrastructure kind : scheme and terms for all resources, links and actions
 */
public class InfrastructureIdentifiers {

    //schemes
    public static final String COMPUTE_SCHEME = "http://schemas.ogf.org/occi/infrastructure/compute#";

    public static final String INFRASTRUCTURE_SCHEME = "http://schemas.ogf.org/occi/infrastructure#";

    public static final String NETWORK_SCHEME = "http://schemas.ogf.org/occi/infrastructure/network#";

    public static final String NETWORKINTERFACE_SCHEME = "http://schemas.ogf.org/occi/infrastructure/networkinterface#";

    public static final String CREDENTIALS_SCHEME = "http://schemas.ogf.org/occi/infrastructure/credentials#";

    public static final String ACTIVEEON_SCHEME = "http://ow2.proactive.org/occi/infrastructure#";

    public static final String OCCIWARE_SCHEME = "http://occiware.org/occi/infrastructure/crtp/backend#";

    //action schemes
    public static final String COMPUTE_ACTION_SCHEME = "http://schemas.ogf.org/occi/infrastructure/compute/action#";

    public static final String NETWORK_ACTION_SCHEME = "http://schemas.ogf.org/occi/infrastructure/network/action#";

    public static final String STORAGE_ACTION_SCHEME = "http://schemas.ogf.org/occi/infrastructure/storage/action#";

    // compute actions
    public static final String START = "Action.start";

    public static final String STOP = "Actions.stop";

    public static final String RESTART = "Actions.restart";

    public static final String SUSPEND = "Actions.suspend";

    public static final String SAVE = "Actions.save";

    // network actions
    public static final String UP = "Actions.up";

    public static final String DOWN = "Actions.down";

    //storage actions
    public static final String ONLINE = "Actions.online";

    public static final String OFFLINE = "Actions.offline";

    // kind terms
    public static final String COMPUTE = "compute";

    public static final String STORAGE = "storage";

    public final static String LINK = "link";

    public final static String STORAGE_LINK = "storagelink";

    public static final String NETWORK = "network";

    public static final String NETWORK_INTERFACE = "networkinterface";

    //mixins terms
    public static final String IPNETWORK = "ipnetwork";

    public static final String IPNETWORK_INTERFACE = "ipnetworkinterface";

    public static final String CREDENTIALS = "ssh_key";

    public static final String CONTEXTUALIZATION = "user_data";

    public static final String VM_IMAGE = "vmimage";

    //model
    public static final String COMPUTE_MODEL = "occi.infrastructure.compute";

}
