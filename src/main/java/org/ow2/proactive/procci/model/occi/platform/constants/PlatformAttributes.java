package org.ow2.proactive.procci.model.occi.platform.constants;

import org.ow2.proactive.procci.model.occi.metamodel.Attribute;
import org.ow2.proactive.procci.model.occi.metamodel.Type;
import org.ow2.proactive.procci.model.occi.platform.Status;


public class PlatformAttributes {

    public static final String STATUS_NAME = "occi.platform.status";

    public static final Attribute STATUS = new Attribute.Builder(STATUS_NAME).type(Type.OBJECT)
                                                                             .defaultValue(Status.INACTIVE)
                                                                             .mutable(false)
                                                                             .required(false)
                                                                             .description("State of the component")
                                                                             .build();

}
