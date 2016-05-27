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

package org.ow2.proactive.procci.model.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.ow2.proactive.procci.model.infrastructure.action.NetworkAction;
import org.ow2.proactive.procci.model.infrastructure.constants.Attributes;
import org.ow2.proactive.procci.model.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.infrastructure.state.NetworkState;
import org.ow2.proactive.procci.model.metamodel.Attribute;
import org.ow2.proactive.procci.model.metamodel.Kind;
import org.ow2.proactive.procci.model.metamodel.Link;
import org.ow2.proactive.procci.model.metamodel.Mixin;
import org.ow2.proactive.procci.model.metamodel.Resource;
import com.google.common.collect.ImmutableList;
import lombok.Getter;


/**
 * This class represents a L2 networking entity
 */
public class Network extends Resource {

    @Getter
    private Integer vlan;
    @Getter
    private String label;
    @Getter
    private NetworkState state;
    @Getter
    private ImmutableList<NetworkAction> actions;


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
     * @param actions is the actions that will be executed on the network
     */
    private Network(String url, Kind kind, String title, List<Mixin> mixins, String summary, List<Link> links,
            Integer vlan, String label, NetworkState state, List<NetworkAction> actions) {

        super(url, kind, title, mixins, summary, links);
        this.state = state;
        this.vlan = vlan;
        this.label = label;
        this.actions = new ImmutableList.Builder<NetworkAction>().addAll(actions).build();
    }

    public static class Builder {
        private final String url;
        private String title;
        private List<Mixin> mixins;
        private String summary;
        private List<Link> links;
        private Integer vlan;
        private String label;
        private NetworkState state;
        private List<NetworkAction> actions;

        public Builder(String url) {
            this.url = url;
            this.title = "";
            this.mixins = new ArrayList<>();
            this.summary = "";
            this.links = new ArrayList<>();
            this.vlan = null;
            this.label = "";
            this.state = null;
            this.actions = new ArrayList<>();
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder addMixin(Mixin mixin) {
            this.mixins.add(mixin);
            return this;
        }

        public Builder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public Builder addLink(Link link) {
            this.links.add(link);
            return this;
        }

        public Builder vlan(Integer vlan) {
            this.vlan = vlan;
            return this;
        }

        public Builder label(String label) {
            this.label = label;
            return this;
        }

        public Builder state(NetworkState state) {
            this.state = state;
            return this;
        }

        public Builder addAction(NetworkAction action) {
            this.actions.add(action);
            return this;
        }

        public Network build() {
            return new Network(url, InfrastructureKinds.NETWORK, title, mixins, summary, links, vlan, label,
                    state, actions);
        }
    }

    public String getMessage() {
        return state.getMessage();
    }

    public static Set<Attribute> getAttributes() {
        Set<Attribute> attributes = Resource.getAttributes();
        attributes.add(Attributes.VLAN);
        attributes.add(Attributes.LABEL);
        attributes.add(Attributes.NETWORK_STATE);
        attributes.add(Attributes.NETWORK_MESSAGE);
        return attributes;
    }
}
