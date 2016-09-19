package org.ow2.proactive.procci.model.occi.metamodel.rendering;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model rendering for a link
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class LinkRendering extends EntityRendering {

    private LinkLocationRendering source;
    private LinkLocationRendering target;

    private LinkRendering(String kind, List<String> mixins, Map<String,Object> attributes, List<String> actions,
                          String id, LinkLocationRendering source, LinkLocationRendering target) {
        super(kind, mixins, attributes, actions, id);
        this.source = source;
        this.target = target;
    }

    public static class Builder {
        private final String kind;
        private List<String> mixins;
        private Map<String,Object> attributes;
        private List<String> actions;
        private final String id;
        private final LinkLocationRendering source;
        private final LinkLocationRendering target;

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
