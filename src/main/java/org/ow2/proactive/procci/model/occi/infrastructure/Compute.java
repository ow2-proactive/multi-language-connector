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
import java.util.Set;

import lombok.*;
import org.ow2.proactive.procci.model.cloud.automation.Service;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes;
import org.ow2.proactive.procci.model.occi.infrastructure.state.ComputeState;
import org.ow2.proactive.procci.model.occi.metamodel.Attribute;
import org.ow2.proactive.procci.model.occi.metamodel.Kind;
import org.ow2.proactive.procci.model.occi.metamodel.Link;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import org.ow2.proactive.procci.model.occi.metamodel.Resource;


/**
 * This class represents a generic information processing resource
 */
public class Compute extends Resource {

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
        X86, X64
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

    public String getMessage() {
        return state.getMessage();
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

    public Service toPCAModel(){
        Service.Builder serviceBuilder = new Service.Builder("Docker","create");
        serviceBuilder.addVariable("instance_name",this.getId());
        serviceBuilder.addVariable("infrastructure_name","openstack_activeeon");
        serviceBuilder.addVariable("instance_image","5126265f-e10a-451f-b38c-eebe54b637eb");
        serviceBuilder.addVariable("instance_flavor",String.valueOf(getInstanceFlavor()));
        serviceBuilder.addVariable("instance_key","activeeon");
        return serviceBuilder.build();
    }

    private int getInstanceFlavor(){

        final String smallArchitecture = "x86";
        final int mediumCores = 2;
        final int mediumMemory = 50;
        final int largeCores = 16;
        final int largeMemory = 500;

        if(this.architecture.equals(smallArchitecture) && this.cores<mediumCores && this.memory<mediumMemory){
            //small favor
            return 1;
        }
        else if(this.cores<largeCores && this.memory<largeMemory){
            //medium favor
            return 2;
        }
        else {
            //large favor
            return 3;
        }

    }



}
