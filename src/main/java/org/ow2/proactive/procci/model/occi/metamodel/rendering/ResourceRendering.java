package org.ow2.proactive.procci.model.occi.metamodel.rendering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.ow2.proactive.procci.model.exception.UnknownAttributeException;
import org.ow2.proactive.procci.model.occi.metamodel.Attribute;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * Created by the Activeeon Team on 14/09/16.
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString
public class ResourceRendering extends EntityRendering {

    private List<LinkRendering> links;

    private ResourceRendering(String kind, List<String> mixins, Map<String, Object> attributes,
            List<String> actions,
            String id, List<LinkRendering> links) {
        super(kind, mixins, attributes, actions, id);
        this.links = links;
    }

    public void checkAttributes(Set<Attribute> attributes, String objectRepresentation){
        String unknownAttributes = this.getAttributes()
                .keySet()
                .stream()
                .filter(key -> attributes.stream()
                        .map(attribute -> attribute.getName())
                        .noneMatch(attributeName -> attributeName.equals(key)))
                .collect(Collectors.joining(", "));
        if (! unknownAttributes.isEmpty()){
            throw new UnknownAttributeException(unknownAttributes,objectRepresentation);
        }
    }

    public static class Builder {

        private final String kind;
        private final String id;
        private List<String> mixins;
        private Map<String, Object> attributes;
        private List<String> actions;
        private List<LinkRendering> links;

        public Builder(String kind, String id) {
            this.kind = kind;
            this.mixins = new ArrayList<>();
            this.attributes = new HashMap<>();
            this.actions = new ArrayList<>();
            this.id = id;
            this.links = new ArrayList<>();
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

        public Builder addLink(LinkRendering link) {
            this.links.add(link);
            return this;
        }


        public ResourceRendering build() {
            return new ResourceRendering(kind, mixins, attributes, actions, id, links);
        }
    }

}
