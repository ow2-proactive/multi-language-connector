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


import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import org.ow2.proactive.procci.model.occi.metamodel.constants.Attributes;

import java.util.*;


/**
 * Kind represent the identification mecanism of the OCCI model
 */
public class Kind extends Category {

    @Getter
    private final ImmutableMap<String, Action> actions;
    @Getter
    private List<Entity> entities;
    @Getter
    private final Kind parent;

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
            attributes = new HashSet<>();
            actions = new HashMap<>();
            parent = null;
            this.setAttributes();
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

        private void setAttributes() {
            this.attributes.add(Attributes.KIND_ACTIONS);
            this.attributes.add(Attributes.PARENT);
            this.attributes.add(Attributes.KIND_ENTITIES);
        }
    }

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
    private Kind(String scheme, String term, String title, Set<Attribute> attributes,
                 Map<String, Action> actions, Kind parent) {
        super(scheme, term, title, attributes);
        this.actions = new ImmutableMap.Builder<String, Action>() {
        }.putAll(actions).build();
        this.parent = parent;
        this.entities = new ArrayList<>();

    }
}
