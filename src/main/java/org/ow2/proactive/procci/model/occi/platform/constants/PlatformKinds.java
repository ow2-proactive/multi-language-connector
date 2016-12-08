package org.ow2.proactive.procci.model.occi.platform.constants;

import org.ow2.proactive.procci.model.occi.metamodel.Kind;
import org.ow2.proactive.procci.model.occi.metamodel.constants.Kinds;
import org.ow2.proactive.procci.model.occi.platform.Component;

public class PlatformKinds {

    public static final Kind COMPONENT = new Kind.Builder(Identifiers.PLATFORM_SCHEME,
            Identifiers.COMPONENT_TERM)
            .addAttribute(Component.createAttributeSet())
            .addParent(Kinds.RESOURCE)
            .build();


}
