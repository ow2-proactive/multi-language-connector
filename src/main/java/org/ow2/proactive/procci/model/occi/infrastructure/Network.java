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
import java.util.Optional;
import java.util.Set;

import org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.occi.infrastructure.state.NetworkState;
import org.ow2.proactive.procci.model.occi.metamodel.Attribute;
import org.ow2.proactive.procci.model.occi.metamodel.Kind;
import org.ow2.proactive.procci.model.occi.metamodel.Link;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import org.ow2.proactive.procci.model.occi.metamodel.Resource;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


/**
 * This class represents a L2 networking entity
 */
@Getter
public class Network extends Resource {

    private Optional<Integer> vlan;
    private Optional<String> label;
    private Optional<NetworkState> state;

    /**
     * Constructor with all parameters
     *
     * @param url     is the user url
     * @param kind    is the network kind
     * @param title   is the display name of the instance
     * @param mixins  are the mixins instance associate to the instance
     * @param summary is the summary of the resource instance
     * @param links   is a set of the Link compositions
     * @param vlan    is an identifier
     * @param label   is a tab based on vlan
     * @param state   is the state aimed by the user or the current state
     */
    private Network(Optional<String> url, Kind kind, Optional<String> title, List<Mixin> mixins,
            Optional<String> summary, List<Link> links,
            Optional<Integer> vlan, Optional<String> label, Optional<NetworkState> state) {

        super(url, kind, title, mixins, summary, links);
        this.state = state;
        this.vlan = vlan;
        this.label = label;
    }

    public static Set<Attribute> createAttributeSet() {
        Set<Attribute> attributes = Resource.createAttributeSet();
        attributes.add(Attributes.VLAN);
        attributes.add(Attributes.LABEL);
        attributes.add(Attributes.NETWORK_STATE);
        attributes.add(Attributes.NETWORK_MESSAGE);
        return attributes;
    }

    @EqualsAndHashCode
    @ToString
    public static class Builder {
        private Optional<String> url;
        private Optional<String> title;
        private List<Mixin> mixins;
        private Optional<String> summary;
        private List<Link> links;
        private Optional<Integer> vlan;
        private Optional<String> label;
        private Optional<NetworkState> state;

        public Builder() {
            this.url = Optional.empty();
            this.title = Optional.empty();
            this.mixins = new ArrayList<>();
            this.summary = Optional.empty();
            this.links = new ArrayList<>();
            this.vlan = Optional.empty();
            this.label = Optional.empty();
            this.state = Optional.empty();
        }

        public Builder url(String url) {
            this.url = Optional.ofNullable(url);
            return this;
        }

        public Builder title(String title) {
            this.title = Optional.ofNullable(title);
            return this;
        }

        public Builder addMixin(Mixin mixin) {
            this.mixins.add(mixin);
            return this;
        }

        public Builder summary(String summary) {
            this.summary = Optional.ofNullable(summary);
            return this;
        }

        public Builder addLink(Link link) {
            this.links.add(link);
            return this;
        }

        public Builder vlan(Integer vlan) {
            this.vlan = Optional.ofNullable(vlan);
            return this;
        }

        public Builder label(String label) {
            this.label = Optional.ofNullable(label);
            return this;
        }

        public Builder state(NetworkState state) {
            this.state = Optional.ofNullable(state);
            return this;
        }

        public Network build() {
            return new Network(url, InfrastructureKinds.NETWORK, title, mixins, summary, links, vlan, label,
                    state);
        }
    }
}
