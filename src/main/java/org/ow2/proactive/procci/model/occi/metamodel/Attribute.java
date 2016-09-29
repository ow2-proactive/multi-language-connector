package org.ow2.proactive.procci.model.occi.metamodel;

import lombok.*;

/**
 * Created by mael on 2/23/16
 */

/**
 * Defines the name and properties of the client readable attributes
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"name"})
@Getter
public class Attribute {

    private final String name;
    private final Type type;
    private final boolean mutable;
    private final boolean required;
    private Object pattern;
    private Object defaultValue;
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
