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

package org.ow2.proactive.procci.model.occi.infrastructure;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes;
import org.ow2.proactive.procci.model.occi.infrastructure.state.ComputeState;
import org.ow2.proactive.procci.model.occi.metamodel.Attribute;
import org.ow2.proactive.procci.model.occi.metamodel.Kind;
import org.ow2.proactive.procci.model.occi.metamodel.Link;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import org.ow2.proactive.procci.model.occi.metamodel.Resource;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.ResourceRendering;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes.ARCHITECTURE_NAME;
import static org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes.COMPUTE_STATE_NAME;
import static org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes.CORES_NAME;
import static org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes.HOSTNAME_NAME;
import static org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes.MEMORY_NAME;
import static org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes.SHARE_NAME;
import static org.ow2.proactive.procci.model.occi.metamodel.constants.Attributes.ENTITY_TITLE_NAME;
import static org.ow2.proactive.procci.model.occi.metamodel.constants.Attributes.SUMMARY_NAME;

/**
 * This class represents a generic information processing resource
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Getter
public class Compute extends Resource {

    private static final String COMPUTE_MODEL = "occi.infrastructure.compute";

    private Optional<Architecture> architecture;
    private Optional<Integer> cores;
    private Optional<Integer> share;
    private Optional<String> hostname;
    private Optional<Float> memory; // in Gigabytes
    private Optional<ComputeState> state;

    /**
     * Constructor with the maximal arguments
     *
     * @param url          is the instance identifier
     * @param kind         is the compute kind
     * @param title        is the display name of the instance
     * @param mixins       are the mixins instance associate to the instance
     * @param summary      is the summary of the resource instance
     * @param links        is a set of the Link compositions
     * @param architecture is the CPU architecture
     * @param cores        is the number of virtual cores assigned for the instance
     * @param share        is the relative number of cpu shares for this instance
     * @param hostname     is the fully qualified hostname for the instance
     * @param memory       is the maxmimum ram allowed for this instance
     * @param state        is the state aimed by the user or the current state
     */
    Compute(Optional<String> url, Kind kind, Optional<String> title, List<Mixin> mixins,
            Optional<String> summary, List<Link> links,
            Optional<Architecture> architecture, Optional<Integer> cores, Optional<Integer> share,
            Optional<String> hostname, Optional<Float> memory,
            Optional<ComputeState> state) {
        super(url, kind, title, mixins, summary, links);
        createAttributesSet();
        this.architecture = architecture;
        this.cores = cores;
        this.share = share;
        this.memory = memory;
        this.hostname = hostname;
        this.state = state;
    }

    private static Set<Attribute> createAttributesSet() {
        Set<Attribute> attributes = Resource.createAttributeSet();
        attributes.add(Attributes.ARCHITECTURE);
        attributes.add(Attributes.CORES);
        attributes.add(Attributes.HOSTNAME);
        attributes.add(Attributes.SHARE);
        attributes.add(Attributes.MEMORY);
        attributes.add(Attributes.COMPUTE_STATE);
        attributes.add(Attributes.COMPUTE_MESSAGE);
        return attributes;
    }

    /**
     * Give the OCCI rendering of a compute
     *
     * @return the compute rendering
     */
    public ResourceRendering getRendering() {

        ResourceRendering.Builder resourceRendering = new ResourceRendering.Builder(this.getKind().getTitle(),
                this.getRenderingId());
        this.getTitle().ifPresent(title -> resourceRendering.addAttribute(ENTITY_TITLE_NAME, title));
        this.getSummary().ifPresent(summary -> resourceRendering.addAttribute(SUMMARY_NAME, summary));
        this.architecture.ifPresent(archi -> resourceRendering.addAttribute(ARCHITECTURE_NAME, archi.name()));
        this.cores.ifPresent(coresNumber -> resourceRendering.addAttribute(CORES_NAME, coresNumber));
        this.memory.ifPresent(memoryNumber -> resourceRendering.addAttribute(MEMORY_NAME, memoryNumber));
        this.share.ifPresent(shareNumber -> resourceRendering.addAttribute(SHARE_NAME, shareNumber));
        this.hostname.ifPresent(host -> resourceRendering.addAttribute(HOSTNAME_NAME, host));
        this.state.ifPresent(
                currentState -> resourceRendering.addAttribute(COMPUTE_STATE_NAME, currentState.name()));

        this.getMixins().forEach(mixin -> resourceRendering.addMixin(mixin.getTitle()));

        return resourceRendering.build();
    }


    public enum Architecture {
        X86, X64;
    }

}
