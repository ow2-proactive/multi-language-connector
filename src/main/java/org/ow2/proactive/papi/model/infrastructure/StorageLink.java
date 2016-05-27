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


package org.ow2.proactive.papi.model.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.ow2.proactive.papi.model.infrastructure.constants.Attributes;
import org.ow2.proactive.papi.model.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.papi.model.infrastructure.state.NetworkState;
import org.ow2.proactive.papi.model.metamodel.Attribute;
import org.ow2.proactive.papi.model.metamodel.Kind;
import org.ow2.proactive.papi.model.metamodel.Link;
import org.ow2.proactive.papi.model.metamodel.Mixin;
import org.ow2.proactive.papi.model.metamodel.Resource;
import lombok.Getter;

/**
 * The StorageLink is a link from a Resource to a target Storage instance
 */
public class StorageLink extends Link {

    @Getter
    private final String deviceId;
    @Getter
    private String mountPoint;
    @Getter
    private final NetworkState state;

    /**
     * Constructor with all StorageLink parameters
     *
     * @param url        is the user url
     * @param kind       is the kind instance which uniquely identify the instance
     * @param title      is the display name of the instance
     * @param mixins     are the mixins instance associate to the instance
     * @param source     is the resource instance the link originate from
     * @param target     is the id of the resource instance the link point to
     * @param targetkind is the kind of the target
     * @param deviceId   is the device identifier
     * @param mountPoint point to where is mounted the guest OS
     */
    private StorageLink(String url, Kind kind, String title, List<Mixin> mixins,
            Resource source, String target, String deviceId, String mountPoint, Kind targetkind,
            NetworkState state) {

        super(url, kind, title, mixins, source, target, targetkind);
        setAttributes();
        this.deviceId = deviceId;
        this.mountPoint = mountPoint;
        this.state = state;
    }

    public static class Builder {
        private final String url;
        private String title;
        private List<Mixin> mixins;
        private final Resource source;
        private final String target;
        private Kind targetKind;
        private final String deviceId;
        private String mountpoint;
        private NetworkState state;

        public Builder(String url, Resource source, String target, String deviceId) {
            this.url = url;
            this.source = source;
            this.target = target;
            this.deviceId = deviceId;
            this.title = "";
            this.mixins = new ArrayList<>();
            this.targetKind = null;
            this.mountpoint = "";
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder addMixin(Mixin mixin) {
            this.mixins.add(mixin);
            return this;
        }

        public Builder targetKind(Kind targetKind) {
            this.targetKind = targetKind;
            return this;
        }

        public Builder mountpoint(String mountpoint) {
            this.mountpoint = mountpoint;
            return this;
        }

        public Builder state(NetworkState state) {
            this.state = state;
            return this;
        }

        public StorageLink build() {
            return new StorageLink(url, InfrastructureKinds.STORAGE_LINK, title, mixins, source, target,
                    deviceId, mountpoint, targetKind, state);
        }

    }

    public String getMessage() {
        return state.getMessage();
    }

    private Set<Attribute> setAttributes() {
        Set<Attribute> attributes = Link.getAttributes();
        attributes.add(Attributes.DEVICEID);
        attributes.add(Attributes.MOUNTPOINT);
        attributes.add(Attributes.STORAGELINK_STATE);
        attributes.add(Attributes.STORAGELINK_MESSAGE);
        return attributes;
    }
}
