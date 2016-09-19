package org.ow2.proactive.procci.model.occi.infrastructure.constants;

import org.ow2.proactive.procci.model.occi.metamodel.Attribute;
import org.ow2.proactive.procci.model.occi.metamodel.Type;

/**
 * Created by mael on 3/2/16.
 */

/**
 * Contains the description of all attributes of the infrastructure
 */
public class Attributes {

    public static final String HOSTNAME_NAME = "occi.compute.hostname";
    public static final String ARCHITECTURE_NAME = "occi.compute.architecture";
    public static final String CORES_NAME = "occi.compute.cores";
    public static final String MEMORY_NAME = "occi.compute.memory";
    public static final String SHARE_NAME = "occi.compute.share";
    public static final String COMPUTE_STATE_NAME = "occi.compute.state";
    public static final String COMPUTE_MESSAGE_NAME = "occi.compute.state.message";
    public static final String VLAN_NAME = "occi.network.vlan";
    public static final String LABEL_NAME = "occi.network.label";
    public static final String NETWORK_STATE_NAME = "occi.network.state";
    public static final String NETWORK_MESSAGE_NAME = "occi.network.state.message";
    public static final String INTERFACE_NAME_NAME ="occi.networkinterface.interface";
    public static final String MAC_NAME = "occi.networkinterface.mac";
    public static final String NETWORKINTERFACE_STATE_NAME ="occi.networkinterface.state";
    public static final String NETWORKINTERFACE_MESSAGE_NAME = "occi.networkinterface.state.message";
    public static final String SIZE_NAME = "occi.storage.size";
    public static final String STORAGE_STATE_NAME = "occi.storage.state";
    public static final String STORAGE_MESSAGE_NAME = "occi.storage.state.message";
    public static final String DEVICEID_NAME = "occi.storagelink.deviceid";
    public static final String MOUNTPOINT_NAME = "occi.storagelink.mountpoint";
    public static final String STORAGELINK_STATE_NAME = "occi.storagelink.state";
    public static final String STORAGELINK_MESSAGE_NAME = "occi.storagelink.state.message";
    public static final String USERDATA_NAME = "occi.compute.userdata";
    public static final String SSH_PUBLICKEY_NAME = "occi.credentials.ssh.publickey";
    public static final String NETWORK_ADDRESS_NAME = "occi.network.address";
    public static final String NETWORK_GATEWAY_NAME = "occi.network.gateway";
    public static final String NETWORK_ALLOCATION_NAME = "occi.network.allocation";
    public static final String NETWORKINTERFACE_ADDRESS_NAME = "occi.networkinterface.address";
    public static final String NETWORKINTERFACE_GATEWAY_NAME = "occi.networkinterface.gateway";
    public static final String NETWORKINTERFACE_ALLOCATION_NAME = "occi.networkinterface.allocation";

    public static final Attribute ARCHITECTURE = new Attribute.Builder(ARCHITECTURE_NAME,
            Type.OBJECT, true, true).description("CPU Architecture of the instance.").build();
    public static final Attribute CORES = new Attribute.Builder(CORES_NAME, Type.OBJECT, true,
            true).description("Number of virtual CPU cores assigned to " +
            "the instance.").build();
    public static final Attribute HOSTNAME = new Attribute.Builder(HOSTNAME_NAME, Type.OBJECT, true,
            true).description("Fully Qualified DNS hostname for the " +
            "instance.").build();
    public static final Attribute SHARE = new Attribute.Builder(SHARE_NAME, Type.OBJECT, true,
            true).description("Relative number of CPU shares for the " +
            "instance.").build();
    public static final Attribute MEMORY = new Attribute.Builder(MEMORY_NAME, Type.OBJECT, true,
            true).description("Maximum RAM in gigabytes allocated to" +
            "the instance.").build();
    public static final Attribute COMPUTE_STATE = new Attribute.Builder(COMPUTE_STATE_NAME, Type.OBJECT,
            false, true).description("Current state of the instance.").build();
    public static final Attribute COMPUTE_MESSAGE = new Attribute.Builder(COMPUTE_MESSAGE_NAME,
            Type.OBJECT, false, true).description("Human-readable explanation of the " +
            "current instance state.").build();
    public static final Attribute VLAN = new Attribute.Builder(VLAN_NAME, Type.OBJECT, true,
            true).description("802.1q VLAN Identifier (e.g., 343).").build();
    public static final Attribute LABEL = new Attribute.Builder(LABEL_NAME, Type.OBJECT, true,
            true).description("Tag based VLANs (e.g., external-dmz).").build();
    public static final Attribute NETWORK_STATE = new Attribute.Builder(NETWORK_STATE_NAME, Type.OBJECT,
            false, false).description("Current state of the instance.").build();
    public static final Attribute NETWORK_MESSAGE = new Attribute.Builder(NETWORK_MESSAGE_NAME,
            Type.OBJECT, false, false).description("Human-readable explanation of the " +
            "current instance state.").build();
    public static final Attribute INTERFACE = new Attribute.Builder(INTERFACE_NAME_NAME,
            Type.OBJECT, true, false).description("Identifier that relates the link " +
            "to the link’s device interface.").build();
    public static final Attribute MAC = new Attribute.Builder(MAC_NAME, Type.OBJECT, true,
            true).description("MAC address associated with the link’s " +
            "device interface.").build();
    public static final Attribute NETWORKINTERFACE_STATE = new Attribute.Builder(NETWORKINTERFACE_STATE_NAME,
            Type.OBJECT, false, false).description("Current status of the instance.").build();
    public static final Attribute NETWORKINTERFACE_MESSAGE = new Attribute.Builder(NETWORKINTERFACE_MESSAGE_NAME,
            Type.OBJECT, false, false).description("Human-readable explanation of the current instance state.").build();
    public static final Attribute SIZE = new Attribute.Builder(SIZE_NAME, Type.OBJECT, true,
            true).description("Storage size of the instance in gigabytes.").build();
    public static final Attribute STORAGE_STATE = new Attribute.Builder(STORAGE_STATE_NAME, Type.OBJECT,
            false, true).description("Current status of the instance.").build();
    public static final Attribute STORAGE_MESSAGE = new Attribute.Builder(STORAGE_MESSAGE_NAME,
            Type.OBJECT, false, true).description("Human-readable explanation of the " +
            "current instance state.").build();
    public static final Attribute DEVICEID = new Attribute.Builder(DEVICEID_NAME, Type.OBJECT,
            true, true).description("Device identifier as defined by the OCCI " +
            "service provider.").build();
    public static final Attribute MOUNTPOINT = new Attribute.Builder(MOUNTPOINT_NAME,
            Type.OBJECT, true, true).description("Point to where the storage is mounted in " +
            "the guest OS.").build();
    public static final Attribute STORAGELINK_STATE = new Attribute.Builder(STORAGELINK_STATE_NAME,
            Type.OBJECT, false, false).description("Current status of the instance.").build();
    public static final Attribute STORAGELINK_MESSAGE = new Attribute.Builder(STORAGELINK_MESSAGE_NAME
            , Type.OBJECT, false, false).description(
            "Human-readable explanation of the " +
                    "current instance state.").build();
    public static final Attribute USERDATA = new Attribute.Builder(USERDATA_NAME, Type.OBJECT, true,
            true).description("Contextualization data(e.g., script, " +
            "executable) that the client supplies once and only once. It cannot be updated.").build();
    public static final Attribute SSH_PUBLICKEY = new Attribute.Builder(SSH_PUBLICKEY_NAME,
            Type.OBJECT, true, true).description("The contents of the public key " +
            "file to be injected into the Compute Resource").build();
    public static final Attribute NETWORK_ADDRESS = new Attribute.Builder(NETWORK_ADDRESS_NAME, Type.OBJECT,
            true, true).description("Internet Protocol (IP) network address" +
            "(e.g., 192.168.0.1/24, fc00::/7)").build();
    public static final Attribute NETWORK_GATEWAY = new Attribute.Builder(NETWORK_GATEWAY_NAME, Type.OBJECT,
            true, true).description("Internet Protocol (IP) network address" +
            "(e.g., 192.168.0.1, fc00::)").build();
    public static final Attribute NETWORK_ALLOCATION = new Attribute.Builder(NETWORK_ALLOCATION_NAME,
            Type.OBJECT, true, true).description("Address allocation mechanism: dynamic " +
            "e.g., uses the dynamic host configuration " +
            "protocol, static e.g., uses user supplied " +
            "static network configurations.").build();
    public static final Attribute NETWORKINTERFACE_ADDRESS = new Attribute.Builder(NETWORKINTERFACE_ADDRESS_NAME
            , Type.OBJECT, true, true).description(
            "Internet Protocol(IP) network address " +
                    "(e.g., 192.168.0.1/24, fc00::/7) of the link").build();
    public static final Attribute NETWORKINTERFACE_GATEWAY = new Attribute.Builder(NETWORKINTERFACE_GATEWAY_NAME
            , Type.OBJECT, true, true).description(
            "Internet Protocol(IP) network address " +
                    "(e.g.. 192.168.0.1/24, fc00::/7)").build();
    public static final Attribute NETWORKINTERFACE_ALLOCATION = new Attribute.Builder(NETWORKINTERFACE_ALLOCATION_NAME
            , Type.OBJECT, true, true).description(
            "Address mechanism: dynamic e.g., uses " +
                    "the dynamic host configuration protocol static e.g., uses user supplied static network configurations.").build();

}
