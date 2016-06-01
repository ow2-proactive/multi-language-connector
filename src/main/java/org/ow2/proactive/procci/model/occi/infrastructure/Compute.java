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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.*;
import org.ow2.proactive.procci.model.occi.infrastructure.action.ComputeAction;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureKinds;
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
     * @param url          is the user url
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
    public Compute(String url, Kind kind, String title, List<Mixin> mixins, String summary, List<Link> links,
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

    @EqualsAndHashCode @ToString
    public static class Builder {
        @Getter @Setter
        private  String url;
        @Getter @Setter
        private String title;
        //private List<Mixin> mixins;
        @Getter @Setter
        private String summary;
        //private List<Link> links;
        @Getter @Setter
        private Architecture architecture;
        @Getter @Setter
        private Integer cores;
        @Getter @Setter
        private Integer share;
        @Getter @Setter
        private String hostname;
        @Getter @Setter
        private Float memory; // in Gigabytes
        private ComputeState state;

        public Builder() {
            this.url = "";
            title = "";
            //mixins = new ArrayList<>();
            summary = "";
            //links = new ArrayList<>();
            architecture = null;
            cores = null;
            share = null;
            hostname = "";
            memory = null;
            state = null;
        }

        public Builder url(String url){
            this.url = url;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public Builder addMixin(Mixin mixin) {
           // this.mixins.add(mixin);
            return this;
        }

        public Builder addLink(Link link) {
            //this.links.add(link);
            return this;
        }

        public Builder architecture(Architecture architecture) {
            this.architecture = architecture;
            return this;
        }

        public Builder cores(Integer cores) {
            this.cores = cores;
            return this;
        }

        public Builder share(Integer share) {
            this.share = share;
            return this;
        }

        public Builder hostame(String hostname) {
            this.hostname = hostname;
            return this;
        }

        public Builder memory(Float memory) {
            this.memory = memory;
            return this;
        }

        public Builder state(ComputeState state) {
            this.state = state;
            return this;
        }

        public Compute build() {
            return new Compute(url, InfrastructureKinds.COMPUTE, title, new ArrayList<>(), summary, new ArrayList<>(), architecture,
                    cores, share, hostname, memory, state);
        }

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
}
