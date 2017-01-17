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
 * The StorageLink is a link from a Resource to a target Storage instance
 */
@Getter
public class StorageLink extends Link {

    private final String deviceId;

    private final NetworkState state;

    private String mountPoint;

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
    private StorageLink(Optional<String> url, Kind kind, Optional<String> title, List<Mixin> mixins, Resource source,
            String target, String deviceId, String mountPoint, Optional<Kind> targetkind, NetworkState state)
            throws SyntaxException {

        super(url, kind, title, mixins, source, target, targetkind);
        createAttributesSet();
        this.deviceId = deviceId;
        this.mountPoint = mountPoint;
        this.state = state;
    }

    private Set<Attribute> createAttributesSet() {
        Set<Attribute> attributes = Link.getAttributes();
        attributes.add(InfrastructureAttributes.DEVICEID);
        attributes.add(InfrastructureAttributes.MOUNTPOINT);
        attributes.add(InfrastructureAttributes.STORAGELINK_STATE);
        attributes.add(InfrastructureAttributes.STORAGELINK_MESSAGE);
        return attributes;
    }

    @EqualsAndHashCode
    @ToString
    public static class Builder {
        private final Resource source;

        private final String target;

        private final String deviceId;

        private Optional<String> url;

        private Optional<String> title;

        private List<Mixin> mixins;

        private Optional<Kind> targetKind;

        private String mountpoint;

        private NetworkState state;

        public Builder(Resource source, String target, String deviceId) {
            this.source = source;
            this.target = target;
            this.deviceId = deviceId;
            this.url = Optional.empty();
            this.title = Optional.empty();
            this.mixins = new ArrayList<>();
            this.targetKind = null;
            this.mountpoint = "";
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

        public Builder targetKind(Kind targetKind) {
            this.targetKind = Optional.ofNullable(targetKind);
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

        public StorageLink build() throws SyntaxException {
            return new StorageLink(url,
                                   InfrastructureKinds.STORAGE_LINK,
                                   title,
                                   mixins,
                                   source,
                                   target,
                                   deviceId,
                                   mountpoint,
                                   targetKind,
                                   state);
        }

    }
}
