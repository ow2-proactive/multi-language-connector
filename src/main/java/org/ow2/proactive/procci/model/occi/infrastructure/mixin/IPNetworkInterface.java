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
 * This class enables to support L3/L4 capabilities with the NetworkInterface class
 */
@Getter
public class IPNetworkInterface extends Mixin {

    private String address;
    private String gateway;
    //if dynamic allocation is true then the allocation is dynamic otherwise it is static
    //default is false
    private boolean dynamic;

    /**
     * Constructor which set all paramters
     *
     * @param address  is the IP network address of the link
     * @param gateway  is the IP adress
     * @param dynamic  defines the allocation protocol
     * @param entities is the set of resource instances
     */
    public IPNetworkInterface(String address, String gateway, boolean dynamic,
            List<Entity> entities) throws UnknownHostException {

        super(InfrastructureIdentifiers.NETWORKINTERFACE_SCHEME, InfrastructureIdentifiers.IPNETWORK_INTERFACE,
                InfrastructureIdentifiers.IPNETWORK_INTERFACE,
                createAttributesSet(), new ArrayList<>(), new ArrayList<>(), initApplies(),
                entities);

        this.address = address;
        this.gateway = gateway;
        this.dynamic = dynamic;
    }

    private static List<Kind> initApplies() {
        List<Kind> applies = new ArrayList<>();
        applies.add(InfrastructureKinds.NETWORK_INTERFACE);
        return applies;
    }

    private static Set<Attribute> createAttributesSet() {
        Set<Attribute> attributes = new HashSet<>();
        attributes.add(InfrastructureAttributes.NETWORKINTERFACE_ADDRESS);
        attributes.add(InfrastructureAttributes.NETWORKINTERFACE_ALLOCATION);
        attributes.add(InfrastructureAttributes.NETWORKINTERFACE_GATEWAY);
        return attributes;
    }
}
