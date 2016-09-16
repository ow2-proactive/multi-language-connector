package org.ow2.proactive.procci.model.occi.metamodel.rendering;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Model rendering for an entity attribute
 */
@Getter
@EqualsAndHashCode
public class AttributesRendering {

    private Map<String, Object> attributes;

    private AttributesRendering(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public static class Builder {
        private Map<String, Object> attributes;

        public Builder() {
            this.attributes = new HashMap<>();
        }

        public Builder addAttribute(String attributeName, Object attributeValue) {
            this.attributes.put(attributeName, attributeValue);
            return this;
        }

        public AttributesRendering build() {
            return new AttributesRendering(this.attributes);
        }
    }

}
