package org.ow2.proactive.procci.model.occi.platform.bigdata.constants;

import org.ow2.proactive.procci.model.occi.metamodel.Kind;
import org.ow2.proactive.procci.model.occi.platform.bigdata.Swarm;

import static org.ow2.proactive.procci.model.occi.platform.constants.PlatformKinds.COMPONENT;

public class BigDataKinds {

    public static final Kind SWARM = new Kind.Builder(Identifiers.SWARM_SCHEME, Identifiers.SWARM_TERM)
            .addAttribute(Swarm.createAttributeSet())
            .addParent(COMPONENT)
            .build();
}
