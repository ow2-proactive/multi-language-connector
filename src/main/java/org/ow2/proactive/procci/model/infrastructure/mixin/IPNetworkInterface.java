package org.ow2.proactive.procci.model.infrastructure.mixin;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.ow2.proactive.procci.model.infrastructure.constants.Attributes;
import org.ow2.proactive.procci.model.infrastructure.constants.Identifiers;
import org.ow2.proactive.procci.model.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.metamodel.Action;
import org.ow2.proactive.procci.model.metamodel.Attribute;
import org.ow2.proactive.procci.model.metamodel.Entity;
import org.ow2.proactive.procci.model.metamodel.Kind;
import org.ow2.proactive.procci.model.metamodel.Mixin;
import lombok.Getter;

/**
 * This class enables to support L3/L4 capabilities with the NetworkInterface class
 */

public class IPNetworkInterface extends Mixin {

    @Getter
    private String address;
    @Getter
    private String gateway;
    //if dynamic allocation is true then the allocation is dynamic otherwise it is static
    //default is false
    @Getter
    private boolean dynamic;

    /**
     * Constructor which set all paramters
     *
     * @param address   is the IP network address of the link
     * @param gateway   is the IP adress
     * @param dynamic defines the allocation protocol
     * @param entities  is the set of resource instances
     */
    public IPNetworkInterface(String address, String gateway, boolean dynamic,
            List<Entity> entities) throws UnknownHostException {

        super(Identifiers.NETWORKINTERFACE_SCHEME, Identifiers.IPNETWORK_INTERFACE,
                Identifiers.IPNETWORK_INTERFACE,
                setAttributes(), new ArrayList<Action>(), new ArrayList<Mixin>(), initApplies(), entities);

        setAttributes();

        this.address = address;
        this.gateway = gateway;

        this.dynamic = dynamic;
    }

    private static List<Kind> initApplies() {
        List<Kind> applies = new ArrayList<>();
        applies.add(InfrastructureKinds.NETWORK_INTERFACE);
        return applies;
    }

    private static HashSet<Attribute> setAttributes() {
        HashSet<Attribute> attributes = new HashSet<>();
        attributes.add(Attributes.NETWORKINTERFACE_ADDRESS);
        attributes.add(Attributes.NETWORKINTERFACE_ALLOCATION);
        attributes.add(Attributes.NETWORKINTERFACE_GATEWAY);
        return attributes;
    }
}
