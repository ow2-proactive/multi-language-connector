package org.ow2.proactive.procci.model.occi.infrastructure.mixin;


import org.ow2.proactive.procci.model.occi.infrastructure.constants.Identifiers;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.occi.metamodel.Entity;
import org.ow2.proactive.procci.model.occi.metamodel.Kind;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Enable to chose which image will be deployed on the instance
 */
public class ResourceTemplate extends Mixin {

    public ResourceTemplate(List<Entity> entities){
        super(Identifiers.ACTIVEEON_SCHEME,Identifiers.RESOURCE_TEMPLATE,
                Identifiers.ACTIVEEON_SCHEME+Identifiers.RESOURCE_TEMPLATE, new HashSet<>(), new ArrayList<>(),
                new ArrayList<>(),initApplies(),entities);
    }

    private static List<Kind> initApplies() {
        List<Kind> applies = new ArrayList<>();
        applies.add(InfrastructureKinds.COMPUTE);
        return applies;
    }
}
