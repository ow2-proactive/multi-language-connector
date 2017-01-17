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
package org.ow2.proactive.procci.model.occi.metamodel;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.ow2.proactive.procci.model.exception.SyntaxException;
import org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelAttributes;
import org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelKinds;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.LinkLocationRendering;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.LinkRendering;

import lombok.Getter;


/**
 * Link defines the base associaction between 2 resources
 */
@Getter
public class Link extends Entity {

    private Resource source;

    private URI target;

    private Optional<Kind> targetKind;

    /**
     * Constructor
     *
     * @param url        unique identify of the instance
     * @param kind       is the kind instance which uniquely identify the instance
     * @param title      is the display name of the instance
     * @param mixins     are the mixins instance associate to the instance
     * @param source     is the resource instance the link originate from
     * @param target     is the id of the resource instance the link point to
     * @param targetKind is the kind of the target
     */
    protected Link(Optional<String> url, Kind kind, Optional<String> title, List<Mixin> mixins, Resource source,
            String target, Optional<Kind> targetKind) throws SyntaxException {
        super(url, kind, title, mixins);
        this.source = source;
        this.target = getURIFromString(target);
        this.targetKind = targetKind;
    }

    public static Set<Attribute> getAttributes() {
        Set<Attribute> attributes = Entity.getAttributes();
        attributes.add(MetamodelAttributes.SOURCE);
        attributes.add(MetamodelAttributes.TARGET);
        attributes.add(MetamodelAttributes.TARGET_KIND);
        return attributes;
    }

    @Override
    public LinkRendering getRendering() {
        return new LinkRendering.Builder(this.getKind().getTitle(),
                                         this.getId(),
                                         new LinkLocationRendering(),
                                         new LinkLocationRendering()).build();
    }

    private URI getURIFromString(String uri) throws SyntaxException {
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            throw new SyntaxException(uri, "URI");
        }
    }

    public static class Builder {
        private final Resource source;

        private final String target;

        private Optional<String> url;

        private Optional<String> title;

        private List<Mixin> mixins;

        private Optional<Kind> targetKind;

        public Builder(Resource source, String target) {
            this.url = Optional.empty();
            this.source = source;
            this.target = target;
            this.mixins = new ArrayList<>();
            this.targetKind = Optional.empty();
        }

        public Builder url(String url) {
            this.url = Optional.ofNullable(url);
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

        public Builder title(String title) {
            this.title = Optional.ofNullable(title);
            return this;
        }

        public Link build() throws SyntaxException {
            return new Link(url, MetamodelKinds.LINK, title, mixins, source, target, targetKind);
        }
    }

}
