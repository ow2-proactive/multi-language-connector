package org.ow2.proactive.procci.model.occi.platform.bigdata.constants;

import org.ow2.proactive.procci.model.occi.metamodel.Attribute;
import org.ow2.proactive.procci.model.occi.metamodel.Type;

public class BigDataAttributes {

    public static final String SWARM_EXTENSION = "pca.plaform.swarm";

    public static final String MACHINE_NAME_NAME = SWARM_EXTENSION + ".machineName";
    public static final String HOST_IP_NAME = SWARM_EXTENSION + ".hostIP";
    public static final String MASTER_IP_NAME = SWARM_EXTENSION + ".masterIP";
    public static final String AGENTS_IP_NAME = SWARM_EXTENSION + ".agentIP";
    public static final String NETWORK_NAME_NAME = SWARM_EXTENSION + ".networkName";

    public static final Attribute MACHINE_NAME = new Attribute.Builder(MACHINE_NAME_NAME)
            .required(true)
            .mutable(false)
            .type(Type.OBJECT)
            .description("The machine host name")
            .defaultValue("swarmMachine")
            .build();

    public static final Attribute HOST_IP = new Attribute.Builder(HOST_IP_NAME)
            .required(true)
            .mutable(false)
            .type(Type.OBJECT)
            .description("The machine host ip")
            .build();

    public static final Attribute MASTER_IP = new Attribute.Builder(MASTER_IP_NAME)
            .required(true)
            .mutable(false)
            .type(Type.OBJECT)
            .description("The master machine ip")
            .build();

    public static final Attribute AGENTS_IP = new Attribute.Builder(AGENTS_IP_NAME)
            .required(true)
            .mutable(false)
            .type(Type.LIST)
            .description("The agent machines ip")
            .build();

    public static final Attribute NETWORK_NAME = new Attribute.Builder(NETWORK_NAME_NAME)
            .required(false)
            .mutable(false)
            .type(Type.OBJECT)
            .description("The network linking the machines name")
            .defaultValue("my-net")
            .build();


}
