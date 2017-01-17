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
package org.ow2.proactive.procci.model.occi.metamodel.rendering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * Model rendering for a link
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class LinkRendering extends EntityRendering {

    private LinkLocationRendering source;

    private LinkLocationRendering target;

    private LinkRendering(String kind, List<String> mixins, Map<String, Object> attributes, List<String> actions,
            String id, LinkLocationRendering source, LinkLocationRendering target) {
        super(kind, mixins, attributes, actions, id);
        this.source = source;
        this.target = target;
    }

    public static class Builder {
        private final String kind;

        private final String id;

        private final LinkLocationRendering source;

        private final LinkLocationRendering target;

        private List<String> mixins;

        private Map<String, Object> attributes;

        private List<String> actions;

        public Builder(String kind, String id, LinkLocationRendering source, LinkLocationRendering target) {
            this.kind = kind;
            this.mixins = new ArrayList<>();
            this.attributes = new HashMap<>();
            this.actions = new ArrayList<>();
            this.id = id;
            this.source = source;
            this.target = target;
        }

        public Builder addMixin(String mixin) {
            this.mixins.add(mixin);
            return this;
        }

        public Builder addAttribute(String attributeName, Object attributeValue) {
            this.attributes.put(attributeName, attributeValue);
            return this;
        }

        public Builder addAction(String action) {
            this.actions.add(action);
            return this;
        }

        public LinkRendering build() {
            return new LinkRendering(kind, mixins, attributes, actions, id, source, target);
        }

    }
}
