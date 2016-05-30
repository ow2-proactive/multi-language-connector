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

import org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.occi.infrastructure.state.NetworkState;
import org.ow2.proactive.procci.model.occi.metamodel.Attribute;
import org.ow2.proactive.procci.model.occi.metamodel.Kind;
import org.ow2.proactive.procci.model.occi.metamodel.Link;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import org.ow2.proactive.procci.model.occi.metamodel.Resource;
import lombok.Getter;


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
    public NetworkInterface(String url, Kind kind, String title, List<Mixin> mixins,
            Resource source, String target, Kind targetKind, String mac, String linkInterface,
            NetworkState state) {

        super(url, kind, title, mixins, source, target, targetKind);
        setAttributes();
        this.linkInterface = linkInterface;
        this.mac = mac;
        this.state = state;
    }

    public static class Builder {
        private final String url;
        private final Resource source;
        private final String target;
        private final String linkInterface;
        private final String mac;
        private String title;
        private Kind targetKind;
        private NetworkState state;
        private List<Mixin> mixins;

        public Builder(String url, Resource source, String targetId, String mac, String linkInterface) {
            this.url = url;
            this.source = source;
            this.target = targetId;
            this.linkInterface = linkInterface;
            this.mac = mac;
            this.title = "";
            this.targetKind = null;
            this.state = null;
            this.mixins = new ArrayList<>();
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder targetKind(Kind targetKind) {
            this.targetKind = targetKind;
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

        public NetworkInterface build() {
            return new NetworkInterface(url, InfrastructureKinds.NETWORK_INTERFACE, title,
                    mixins, source, target, targetKind, mac, linkInterface, state);
        }

    }

    public String getMessage() {
        return state.getMessage();
    }


    private static Set<Attribute> setAttributes() {
        Set<Attribute> attributes = Link.getAttributes();
        attributes.add(Attributes.INTERFACE);
        attributes.add(Attributes.MAC);
        attributes.add(Attributes.NETWORKINTERFACE_STATE);
        attributes.add(Attributes.NETWORKINTERFACE_MESSAGE);
        return attributes;
    }
}
