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

import java.util.Optional;

import org.ow2.proactive.procci.model.occi.metamodel.rendering.AttributeRendering;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;


/**
 * Created by the Activeeon Team on 2/23/16
 */

/**
 * Defines the name and properties of the client readable attributes
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = { "name" })
@Getter
public class Attribute {

    private final String name;

    private Optional<Type> type;

    private Optional<Boolean> mutable;

    private Optional<Boolean> required;

    private Optional<Object> pattern;

    private Optional<Object> defaultValue;

    private Optional<String> description;

    public AttributeRendering getRendering() {
        AttributeRendering.Builder attributeRendering = AttributeRendering.builder();
        type.ifPresent(type1 -> attributeRendering.type(type1.name()));
        mutable.ifPresent(m -> attributeRendering.mutable(m));
        required.ifPresent(r -> attributeRendering.required(r));
        pattern.ifPresent(p -> attributeRendering.pattern(p));
        defaultValue.ifPresent(value -> attributeRendering.defaultValue(value));
        description.ifPresent(d -> attributeRendering.description(d));
        return attributeRendering.build();
    }

    public static class Builder {
        private final String name;

        private Optional<Type> type;

        private Optional<Boolean> mutable;

        private Optional<Boolean> required;

        private Optional<Object> pattern;

        private Optional<Object> defaultValue;

        private Optional<String> description;

        public Builder(String name) {
            this.name = name;
            this.type = Optional.empty();
            this.mutable = Optional.empty();
            this.required = Optional.empty();
            this.pattern = Optional.empty();
            this.defaultValue = Optional.empty();
            this.description = Optional.empty();
        }

        public Builder(String name, AttributeRendering attributeRendering) {
            this.name = name;
            this.type = Optional.ofNullable(attributeRendering.getType()).map(type -> Type.valueOf(type));
            this.mutable = Optional.ofNullable(attributeRendering.isMutable());
            this.required = Optional.ofNullable(attributeRendering.isRequired());
            this.pattern = Optional.ofNullable(attributeRendering.getPattern());
            this.defaultValue = Optional.ofNullable(attributeRendering.getDefaultValue());
            this.description = Optional.ofNullable(attributeRendering.getDescription());

        }

        public Builder type(Type type) {
            this.type = Optional.of(type);
            return this;
        }

        public Builder mutable(Boolean mutable) {
            this.mutable = Optional.of(mutable);
            return this;
        }

        public Builder required(Boolean required) {
            this.required = Optional.of(required);
            return this;
        }

        public Builder pattern(Object pattern) {
            this.pattern = Optional.of(pattern);
            return this;
        }

        public Builder defaultValue(Object defaultValue) {
            this.defaultValue = Optional.of(defaultValue);
            return this;
        }

        public Builder description(String description) {
            this.description = Optional.of(description);
            return this;
        }

        public Attribute build() {
            return new Attribute(this.name,
                                 this.type,
                                 this.mutable,
                                 this.required,
                                 this.pattern,
                                 this.defaultValue,
                                 this.description);
        }
    }

}
