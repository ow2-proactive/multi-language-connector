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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;


/**
 * Rendering model for entities
 */
@Getter
@EqualsAndHashCode
public class EntitiesRendering {

    private final Map<String, EntityRendering> entities;

    private EntitiesRendering(Map<String, EntityRendering> entities) {
        this.entities = entities;
    }

    public static class Builder {
        private final Map<String, EntityRendering> entities;

        public Builder() {
            entities = new HashMap<>();
        }

        public Builder addEntity(EntityRendering entity) {
            this.entities.put(entity.getId(), entity);
            return this;
        }

        public Builder addEntities(List<EntityRendering> results) {
            results.stream().forEach(entityRendering -> this.addEntity(entityRendering));
            return this;
        }

        public EntitiesRendering build() {
            return new EntitiesRendering(entities);
        }

    }
}
