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

package org.ow2.proactive.procci.model.occi.metamodel;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.ow2.proactive.procci.model.occi.metamodel.constants.Attributes;
import org.ow2.proactive.procci.model.occi.metamodel.constants.Kinds;
import lombok.Getter;

/**
 * Link defines the base associaction between 2 resources
 */
public class Link extends Entity {

    @Getter
    private Resource source;
    @Getter
    private URI target;
    @Getter
    private Kind targetKind;

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
    protected Link(String url, Kind kind, String title, List<Mixin> mixins,
                   Resource source, String target, Kind targetKind) {
        super(url, kind, title, mixins);
        this.source = source;
        try {
            this.target = new URI(target);
        } catch (URISyntaxException e) {
            throw new RuntimeException(target);
        }
        this.targetKind = targetKind;
    }

    public static class Builder {
        private String url;
        private String title;
        private List<Mixin> mixins;
        private final Resource source;
        private final String target;
        private Kind targetKind;

        public Builder(Resource source, String target) {
            this.url = "";
            this.source = source;
            this.target = target;
            this.mixins = new ArrayList<>();
            this.targetKind = null;
        }


        public Builder url(String url) {
            this.url = url;
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

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Link build() {
            return new Link(url, Kinds.LINK, title, mixins, source, target, targetKind);
        }
    }

    public static Set<Attribute> getAttributes() {
        Set<Attribute> attributes = Entity.getAttributes();
        attributes.add(Attributes.SOURCE);
        attributes.add(Attributes.TARGET);
        attributes.add(Attributes.TARGET_KIND);
        return attributes;
    }

}
