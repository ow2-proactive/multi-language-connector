package org.ow2.proactive.procci.model.infrastructure.constants;

import org.ow2.proactive.procci.model.metamodel.Attribute;
import org.ow2.proactive.procci.model.metamodel.Type;

/**
 * Created by mael on 3/2/16.
 */

/**
 * Contains the description of all attributes of the infrastructure
 */
public class Attributes {
    public static final Attribute ARCHITECTURE = new Attribute.Builder("occi.compute.architecture",
            Type.OBJECT, true, true).description("CPU Architecture of the instance.").build();
    public static final Attribute CORES = new Attribute.Builder("occi.compute.cores", Type.OBJECT, true,
            true).description("Number of virtual CPU cores assigned to " +
            "the instance.").build();
    public static final Attribute HOSTNAME = new Attribute.Builder("occi.compute.hostname", Type.OBJECT, true,
            true).description("Fully Qualified DNS hostname for the " +
            "instance.").build();
    public static final Attribute SHARE = new Attribute.Builder("occi.compute.share", Type.OBJECT, true,
            true).description("Relative number of CPU shares for the " +
            "instance.").build();
    public static final Attribute MEMORY = new Attribute.Builder("occi.compute.memory", Type.OBJECT, true,
            true).description("Maximum RAM in gigabytes allocated to" +
            "the instance.").build();
    public static final Attribute COMPUTE_STATE = new Attribute.Builder("occi.compute.state", Type.OBJECT,
            false, true).description("Current state of the instance.").build();
    public static final Attribute COMPUTE_MESSAGE = new Attribute.Builder("occi.compute.state.message",
            Type.OBJECT, false, true).description("Human-readable explanation of the " +
            "current instance state.").build();
    public static final Attribute VLAN = new Attribute.Builder("occi.network.vlan", Type.OBJECT, true,
            true).description("802.1q VLAN Identifier (e.g., 343).").build();
    public static final Attribute LABEL = new Attribute.Builder("occi.network.label", Type.OBJECT, true,
            true).description("Tag based VLANs (e.g., external-dmz).").build();
    public static final Attribute NETWORK_STATE = new Attribute.Builder("occi.network.state", Type.OBJECT,
            false, false).description("Current state of the instance.").build();
    public static final Attribute NETWORK_MESSAGE = new Attribute.Builder("occi.network.state.message",
            Type.OBJECT, false, false).description("Human-readable explanation of the " +
            "current instance state.").build();
    public static final Attribute INTERFACE = new Attribute.Builder("occi.networkinterface.interface",
            Type.OBJECT, true, false).description("Identifier that relates the link " +
            "to the link’s device interface.").build();
    public static final Attribute MAC = new Attribute.Builder("occi.networkinterface.mac", Type.OBJECT, true,
            true).description("MAC address associated with the link’s " +
            "device interface.").build();
    public static final Attribute NETWORKINTERFACE_STATE = new Attribute.Builder(
            "occi.networkinterface.state", Type.OBJECT, false, false).description(
            "Current status of the instance.").build();
    public static final Attribute NETWORKINTERFACE_MESSAGE = new Attribute.Builder(
            "occi.networkinterface.state.message", Type.OBJECT, false, false).description(
            "Human-readable explanation of " +
                    "the current instance state.").build();
    public static final Attribute SIZE = new Attribute.Builder("occi.storage.size", Type.OBJECT, true,
            true).description("Storage size of the instance in gigabytes.").build();
    public static final Attribute STORAGE_STATE = new Attribute.Builder("occi.storage.state", Type.OBJECT,
            false, true).description("Current status of the instance.").build();
    public static final Attribute STORAGE_MESSAGE = new Attribute.Builder("occi.storage.state.message",
            Type.OBJECT, false, true).description("Human-readable explanation of the " +
            "current instance state.").build();
    public static final Attribute DEVICEID = new Attribute.Builder("occi.storagelink.deviceid", Type.OBJECT,
            true, true).description("Device identifier as defined by the OCCI " +
            "service provider.").build();
    public static final Attribute MOUNTPOINT = new Attribute.Builder("occi.storagelink.mountpoint",
            Type.OBJECT, true, true).description("Point to where the storage is mounted in " +
            "the guest OS.").build();
    public static final Attribute STORAGELINK_STATE = new Attribute.Builder("occi.storagelink.state",
            Type.OBJECT, false, false).description("Current status of the instance.").build();
    public static final Attribute STORAGELINK_MESSAGE = new Attribute.Builder(
            "occi.storagelink.state.message", Type.OBJECT, false, false).description(
            "Human-readable explanation of the " +
                    "current instance state.").build();
    public static final Attribute USERDATA = new Attribute.Builder("occi.compute.userdata", Type.OBJECT, true,
            true).description("Contextualization data(e.g., script, " +
            "executable) that the client supplies once and only once. It cannot be updated.").build();
    public static final Attribute SSH_PUBLICKEY = new Attribute.Builder("occi.credentials.ssh.publickey",
            Type.OBJECT, true, true).description("The contents of the public key " +
            "file to be injected into the Compute Resource").build();
    public static final Attribute NETWORK_ADDRESS = new Attribute.Builder("occi.network.address", Type.OBJECT,
            true, true).description("Internet Protocol (IP) network address" +
            "(e.g., 192.168.0.1/24, fc00::/7)").build();
    public static final Attribute NETWORK_GATEWAY = new Attribute.Builder("occi.network.gateway", Type.OBJECT,
            true, true).description("Internet Protocol (IP) network address" +
            "(e.g., 192.168.0.1, fc00::)").build();
    public static final Attribute NETWORK_ALLOCATION = new Attribute.Builder("occi.network.allocation",
            Type.OBJECT, true, true).description("Address allocation mechanism: dynamic " +
            "e.g., uses the dynamic host configuration " +
            "protocol, static e.g., uses user supplied " +
            "static network configurations.").build();
    public static final Attribute NETWORKINTERFACE_ADDRESS = new Attribute.Builder(
            "occi.networkinterface.address", Type.OBJECT, true, true).description(
            "Internet Protocol(IP) network address " +
                    "(e.g., 192.168.0.1/24, fc00::/7) of the link").build();
    public static final Attribute NETWORKINTERFACE_GATEWAY = new Attribute.Builder(
            "occi.networkinterface.gateway", Type.OBJECT, true, true).description(
            "Internet Protocol(IP) network address " +
                    "(e.g.. 192.168.0.1/24, fc00::/7)").build();
    public static final Attribute NETWORKINTERFACE_ALLOCATION = new Attribute.Builder(
            "occi.networkinterface.allocation", Type.OBJECT, true, true).description(
            "Address mechanism: dynamic e.g., uses " +
                    "the dynamic host configuration protocol static e.g., uses user supplied static network configurations.").build();

}
