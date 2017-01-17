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

package org.ow2.proactive.procci.model.occi.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.ow2.proactive.procci.model.exception.SyntaxException;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureAttributes;
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
 * The NetworkInterface is an L2 client device
 */
public class NetworkInterface extends Link {

    @Getter
    private final String linkInterface;

    @Getter
    private final String mac;

    @Getter
    private NetworkState state;

    /**
     * constructor with all the parameters
     *
     * @param url           is the user url
     * @param kind          is the kind instance which uniquely identify the instance
     * @param title         is the display name of the instance
     * @param mixins        are the mixins instance associate to the instancece
     * @param source        is the resource instance the link originate from
     * @param target        is the resource instance the link point to
     * @param mac           is the mac adress associated with the link's device interface
     * @param linkInterface relates the link to the link's device interface
     */
    public NetworkInterface(Optional<String> url, Kind kind, Optional<String> title, List<Mixin> mixins,
            Resource source, String target, Optional<Kind> targetKind, String mac, String linkInterface,
            NetworkState state) throws SyntaxException {

        super(url, kind, title, mixins, source, target, targetKind);
        createAttributesSet();
        this.linkInterface = linkInterface;
        this.mac = mac;
        this.state = state;
    }

    private static Set<Attribute> createAttributesSet() {
        Set<Attribute> attributes = Link.getAttributes();
        attributes.add(InfrastructureAttributes.INTERFACE);
        attributes.add(InfrastructureAttributes.MAC);
        attributes.add(InfrastructureAttributes.NETWORKINTERFACE_STATE);
        attributes.add(InfrastructureAttributes.NETWORKINTERFACE_MESSAGE);
        return attributes;
    }

    public String getMessage() {
        return state.getMessage();
    }

    @EqualsAndHashCode
    @ToString
    public static class Builder {
        private final Resource source;

        private final String target;

        private final String linkInterface;

        private final String mac;

        private Optional<String> url;

        private Optional<String> title;

        private Optional<Kind> targetKind;

        private NetworkState state;

        private List<Mixin> mixins;

        public Builder(Resource source, String targetId, String mac, String linkInterface) {
            this.source = source;
            this.target = targetId;
            this.linkInterface = linkInterface;
            this.mac = mac;
            this.url = Optional.empty();
            this.title = Optional.empty();
            this.targetKind = null;
            this.state = null;
            this.mixins = new ArrayList<>();
        }

        public Builder url(String url) {
            this.url = Optional.ofNullable(url);
            return this;
        }

        public Builder title(String title) {
            this.title = Optional.ofNullable(title);
            return this;
        }

        public Builder targetKind(Kind targetKind) {
            this.targetKind = Optional.ofNullable(targetKind);
            return this;
        }

        public Builder state(NetworkState state) {
            this.state = state;
            return this;
        }

        public Builder addMixin(Mixin mixin) {
            mixins.add(mixin);
            return this;
        }

        public NetworkInterface build() throws SyntaxException {
            return new NetworkInterface(url,
                                        InfrastructureKinds.NETWORK_INTERFACE,
                                        title,
                                        mixins,
                                        source,
                                        target,
                                        targetKind,
                                        mac,
                                        linkInterface,
                                        state);
        }

    }
}
