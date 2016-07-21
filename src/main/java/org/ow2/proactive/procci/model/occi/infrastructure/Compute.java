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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes;
import org.ow2.proactive.procci.model.occi.infrastructure.state.ComputeState;
import org.ow2.proactive.procci.model.occi.metamodel.*;

import java.util.List;
import java.util.Set;

import static org.ow2.proactive.procci.model.ModelConstant.*;


/**
 * This class represents a generic information processing resource
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Compute extends Resource {

    private static final String COMPUTE_MODEL = "occi.infrastructure.compute";

    @Getter
    private Architecture architecture;
    @Getter
    private Integer cores;
    @Getter
    private Integer share;
    @Getter
    private String hostname;
    @Getter
    private Float memory; // in Gigabytes
    @Getter
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

    public Model toCloudAutomationModel(String actionType) {

        Model.Builder serviceBuilder = new Model.Builder(COMPUTE_MODEL, actionType)
                .addVariable(TITLE, this.getTitle())
                .addVariable(ARCHITECTURE, this.architecture)
                .addVariable(CORES, this.cores)
                .addVariable(MEMORY, this.memory)
                .addVariable(HOSTNAME, this.hostname);
        return serviceBuilder.build();
    }

}
