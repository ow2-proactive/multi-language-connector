package org.ow2.proactive.procci.model.occi.metamodel.constants;

import org.ow2.proactive.procci.model.occi.metamodel.Entity;
import org.ow2.proactive.procci.model.occi.metamodel.Kind;
import org.ow2.proactive.procci.model.occi.metamodel.Link;
import org.ow2.proactive.procci.model.occi.metamodel.Resource;


/**
 * Created by the Activeeon Team on 3/3/16.
 */

/**
 * Contains the the meta-model kind instances
 */
public class MetamodelKinds {

    public static final Kind ENTITY = new Kind.Builder(MetamodelIdentifiers.CORE_SCHEME,
                                                       MetamodelIdentifiers.ENTITY_TERM).addAttribute(Entity.getAttributes())
                                                                                        .build();

    public static final Kind RESOURCE = new Kind.Builder(MetamodelIdentifiers.CORE_SCHEME,
                                                         MetamodelIdentifiers.RESOURCE_TERM).addAttribute(Resource.getAttributes())
                                                                                            .addParent(ENTITY)
                                                                                            .build();

    public static final Kind LINK = new Kind.Builder(MetamodelIdentifiers.CORE_SCHEME,
                                                     MetamodelIdentifiers.LINK_TERM).addAttribute(Link.getAttributes())
                                                                                    .addParent(ENTITY)
                                                                                    .build();
}
