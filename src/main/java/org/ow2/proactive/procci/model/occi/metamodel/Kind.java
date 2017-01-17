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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelAttributes;

import com.google.common.collect.ImmutableMap;

import lombok.Getter;


/**
 * Kind represent the identification mecanism of the OCCI model
 */
public class Kind extends Category {

    @Getter
    private final ImmutableMap<String, Action> actions;

    @Getter
    private final Kind parent;

    @Getter
    private List<Entity> entities;

    /**
     * Constructor which set all data
     *
     * @param scheme     categorisation scheme
     * @param term       unique identifier
     * @param title      the display name of the instance
     * @param actions    are the actions defined by the kind instance
     * @param attributes are the kind type attributes
     * @param parent     is the set of the kind instances
     */
    private Kind(String scheme, String term, String title, Set<Attribute> attributes, Map<String, Action> actions,
            Kind parent) {
        super(scheme, term, title, attributes);
        this.actions = new ImmutableMap.Builder<String, Action>() {
        }.putAll(actions).build();
        this.parent = parent;
        this.entities = new ArrayList<>();
    }

    public static class Builder {

        private final String scheme;

        private final String term;

        private String title;

        private Set<Attribute> attributes;

        private Map<String, Action> actions;

        private Kind parent;

        public Builder(String scheme, String term) {
            this.scheme = scheme;
            this.term = term;
            this.title = scheme + term;
            this.attributes = new HashSet<>();
            this.actions = new HashMap<>();
            this.parent = null;
            this.createAttributesSet();
        }

        public Builder addTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder addAttribute(Set<Attribute> attributes) {
            this.attributes.addAll(attributes);
            return this;
        }

        public Builder addAction(Action action) {
            this.actions.put(action.getTerm(), action);
            return this;
        }

        public Builder addParent(Kind parent) {
            this.parent = parent;
            return this;
        }

        public Kind build() {
            return new Kind(scheme, term, title, attributes, actions, parent);
        }

        private void createAttributesSet() {
            this.attributes.add(MetamodelAttributes.KIND_ACTIONS);
            this.attributes.add(MetamodelAttributes.PARENT);
            this.attributes.add(MetamodelAttributes.KIND_ENTITIES);
        }
    }
}
