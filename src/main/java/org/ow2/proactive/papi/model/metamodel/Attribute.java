package org.ow2.proactive.papi.model.metamodel;

import lombok.*;

/**
 * Created by mael on 2/23/16
 */

/**
 * Defines the name and properties of the client readable attributes
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(exclude = { "type", "mutable", "required", "pattern", "defaultValue", "description" })
public class Attribute {

    @Getter
    private final String name;
    @Getter
    private final Type type;
    @Getter
    private final boolean required;
    @Getter
    private final boolean mutable;
    @Getter
    @Setter
    private Object pattern;
    @Getter
    @Setter
    private Object defaultValue;
    @Getter
    @Setter
    private String description;

    @RequiredArgsConstructor
    public static class Builder {
        private final String name;
        private final Type type;
        private final boolean mutable;
        private final boolean required;

        private Object pattern;
        private Object defaultValue;
        private String description;

        public Builder pattern(Object pattern) {
            this.pattern = pattern;
            return this;
        }

        public Builder defaultValue(Object defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Attribute build() {
            return new Attribute(this.name, this.type, this.mutable, this.required,
                    this.pattern, this.defaultValue, this.description);
        }
    }

}
