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

import lombok.*;
import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes;
import org.ow2.proactive.procci.model.occi.infrastructure.state.ComputeState;
import org.ow2.proactive.procci.model.occi.metamodel.*;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.ResourceRendering;

import java.util.List;
import java.util.Set;

import static org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes.*;
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

    private Architecture architecture;
    private Integer cores;
    private Integer share;
    private String hostname;
    private Float memory; // in Gigabytes
    private ComputeState state;

    public enum Architecture {
        X86, X64;
    }

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
    Compute(String url, Kind kind, String title, List<Mixin> mixins, String summary, List<Link> links,
            Architecture architecture, Integer cores, Integer share, String hostname, Float memory,
            ComputeState state) {
        super(url, kind, title, mixins, summary, links);
        setAttributes();
        this.architecture = architecture;
        this.cores = cores;
        this.share = share;
        this.memory = memory;
        this.hostname = hostname;
        this.state = state;
    }


    private static Set<Attribute> setAttributes() {
        Set<Attribute> attributes = Resource.getAttributes();
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
     * Convert OCCI compute to Proactive Cloud Automation Compute
     *
     * @param actionType is the action to apply on the compute
     * @return the proactive cloud automation model for the compute
     */
    public Model toCloudAutomationModel(String actionType) {

        Model.Builder serviceBuilder = new Model.Builder(COMPUTE_MODEL, actionType)
                .addVariable(ENTITY_TITLE_NAME, this.getTitle())
                .addVariable(ARCHITECTURE_NAME, this.architecture)
                .addVariable(CORES_NAME, this.cores)
                .addVariable(MEMORY_NAME, this.memory)
                .addVariable(HOSTNAME_NAME, this.hostname)
                .addVariable(SUMMARY_NAME, this.getSummary())
                .addVariable(COMPUTE_STATE_NAME, this.state);

        return serviceBuilder.build();
    }

    /**
     * Give the OCCI rendering of a compute
     *
     * @return the compute rendering
     */
    public ResourceRendering getRendering() {

        ResourceRendering.Builder resourceRendering = new ResourceRendering.Builder(this.getKind().getTitle(), this.getId())
                .addAttribute(ENTITY_TITLE_NAME,this.getTitle())
                .addAttribute(CORES_NAME, this.cores)
                .addAttribute(MEMORY_NAME, this.memory)
                .addAttribute(COMPUTE_STATE_NAME, this.state.name())
                .addAttribute(HOSTNAME_NAME, this.hostname)
                .addAttribute(SUMMARY_NAME,this.getSummary())
                .addAttribute(ARCHITECTURE_NAME,this.architecture.name());

        return resourceRendering.build();
    }

}
