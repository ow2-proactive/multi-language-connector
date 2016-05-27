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

package org.ow2.proactive.papi.model.metamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.ow2.proactive.papi.model.metamodel.constants.Attributes;
import org.ow2.proactive.papi.model.metamodel.constants.Kinds;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import lombok.Getter;

/**
 * Resource describes a concrete resource that can be inspected or manipulated
 */
public class Resource extends Entity {

    @Getter
    private String summary;
    @Getter
    private ImmutableCollection<Link> links;


    /**
     * Constructor which set all parameters
     *
     * @param url     is the user url
     * @param kind    is the kind instance which uniquely identify the instance
     * @param title   is the display name of the instance
     * @param mixins  are the mixins instance associate to the instance
     * @param summary is the summary of the resource instance
     * @param links   is a set of the Link compositions
     */
    protected Resource(String url, Kind kind, String title, List<Mixin> mixins,
            String summary, List<Link> links) {
        super(url, kind, title, mixins);
        this.summary = summary;
        this.links = new ImmutableList.Builder<Link>().addAll(links).build();
    }

    public static class Builder {
        private final String url;
        private String title;
        private List<Mixin> mixins;
        private String summary;
        private List<Link> links;

        public Builder(String url) {
            this.url = url;
            this.title = "";
            this.mixins = new ArrayList<>();
            this.summary = "";
            this.links = new ArrayList<>();
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

        public Resource build() {
            return new Resource(url, Kinds.RESOURCE, title, mixins, summary, links);
        }
    }

    public static Set<Attribute> getAttributes() {
        Set<Attribute> attributes = Entity.getAttributes();
        attributes.add(Attributes.LINKS);
        attributes.add(Attributes.SUMMARY);
        return attributes;
    }

}
