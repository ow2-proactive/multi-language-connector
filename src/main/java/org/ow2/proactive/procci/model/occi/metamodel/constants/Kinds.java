package org.ow2.proactive.procci.model.occi.metamodel.constants;

import org.ow2.proactive.procci.model.occi.metamodel.Entity;
import org.ow2.proactive.procci.model.occi.metamodel.Kind;
import org.ow2.proactive.procci.model.occi.metamodel.Link;
import org.ow2.proactive.procci.model.occi.metamodel.Resource;

/**
 * Created by mael on 3/3/16.
 */

/**
 * Contains the the meta-model kind instances
 */
public class Kinds {

    public static final Kind ENTITY = new Kind.Builder(Identifiers.CORE_SCHEME, Identifiers.ENTITY_TERM)
            .addAttribute(Entity.getAttributes())
            .build();

    public static final Kind RESOURCE = new Kind.Builder(Identifiers.CORE_SCHEME, Identifiers.RESOURCE_TERM)
            .addAttribute(Resource.getAttributes())
            .addParent(ENTITY)
            .build();

    public static final Kind LINK = new Kind.Builder(Identifiers.CORE_SCHEME, Identifiers.LINK_TERM)
            .addAttribute(Link.getAttributes())
            .addParent(ENTITY)
            .build();
}
