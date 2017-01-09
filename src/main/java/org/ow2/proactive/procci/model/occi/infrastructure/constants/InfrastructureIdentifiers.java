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
