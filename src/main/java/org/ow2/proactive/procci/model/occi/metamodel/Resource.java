/*
 * ProActive Parallel Suite(TM): The Java(TM) library for
 *    Parallel, Distributed, Multi-Core Computing for
 *    Enterprise Grids & Clouds
 *
 * Copyright (C) 2013-2015 ActiveEon
 * 
 * Contact: proactive@ow2.org or contact@activeeon.com
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; version 3 of
 * the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 *
 *  Initial developer(s):               The ProActive Team
 *                        http://proactive.inria.fr/team_members.htm
 *  Contributor(s):
 *
 * $$ACTIVEEON_INITIAL_DEV$$
 */

package org.ow2.proactive.procci.model.occi.metamodel;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelAttributes;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.ResourceRendering;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelAttributes.ENTITY_TITLE_NAME;
import static org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelAttributes.SUMMARY_NAME;

/**
 * Resource describes a concrete resource that can be inspected or manipulated
 */
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
@Getter
public class Resource extends Entity {

    private Optional<String> summary;
    private ImmutableCollection<Link> links;


    /**
     * Constructor which set all parameters
     *
     * @param url     is the user url
     * @param kind    is the kind instance which uniquely identify the instance
     * @param title   is the display name of the instance
     * @param mixins  are the mixins instance associate to the instance
     * @param summary is the summary of the resource instance
     * @param links   is a set of the Link compositions
     */
    protected Resource(Optional<String> url, Kind kind, Optional<String> title, List<Mixin> mixins,
            Optional<String> summary, List<Link> links) {
        super(url, kind, title, mixins);
        this.summary = summary;
        this.links = new ImmutableList.Builder<Link>().addAll(links).build();
    }


    public static Set<Attribute> getAttributes() {
        Set<Attribute> attributes = Entity.getAttributes();
        attributes.add(MetamodelAttributes.LINKS);
        attributes.add(MetamodelAttributes.SUMMARY);
        return attributes;
    }

    @Override
    public ResourceRendering getRendering() {
        ResourceRendering.Builder resourceRendering = new ResourceRendering.Builder(this.getKind().getTitle(),
                this.getRenderingId());
        this.getTitle().ifPresent(title -> resourceRendering.addAttribute(ENTITY_TITLE_NAME, title));
        this.getSummary().ifPresent(summary -> resourceRendering.addAttribute(SUMMARY_NAME, summary));
        this.getMixins().forEach(mixin -> resourceRendering.addMixin(mixin.getTitle()));
        return resourceRendering.build();
    }

}
