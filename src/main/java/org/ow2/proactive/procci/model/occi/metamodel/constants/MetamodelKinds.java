/*
 * ProActive Parallel Suite(TM):
 * The Open Source library for parallel and distributed
 * Workflows & Scheduling, Orchestration, Cloud Automation
 * and Big Data Analysis on Enterprise Grids & Clouds.
 *
 * Copyright (c) 2007 - 2017 ActiveEon
 * Contact: contact@activeeon.com
 *
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation: version 3 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 */
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
