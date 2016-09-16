package org.ow2.proactive.procci.model.occi.metamodel.rendering;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mael on 14/09/16.
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class ResourceRendering extends EntityRendering {

    private List<LinkRendering> links;
    private String summary;

    private ResourceRendering(String kind, List<String> mixins, AttributesRendering attributes, List<String> actions,
                              String id, String title, List<LinkRendering> links, String summary) {
        super(kind, mixins, attributes, actions, id, title);
        this.links = links;
        this.summary = summary;
    }

    public static class Builder {

        private final String kind;
        private List<String> mixins;
        private AttributesRendering.Builder attributes;
        private List<String> actions;
        private final String id;
        private String title;
        private List<LinkRendering> links;
        private String summary;

        public Builder(String kind, String id) {
            this.kind = kind;
            this.mixins = new ArrayList<>();
            this.attributes = new AttributesRendering.Builder();
            this.actions = new ArrayList<>();
            this.id = id;
            this.title = "";
            this.links = new ArrayList<>();
            this.summary = "";
        }

        public Builder addMixin(String mixin) {
            this.mixins.add(mixin);
            return this;
        }

        public Builder addAttribute(String attributeName, Object attributeValue) {
            this.attributes.addAttribute(attributeName, attributeValue);
            return this;
        }

        public Builder addAction(String action) {
            this.actions.add(action);
            return this;
        }

        public Builder addTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder addLink(LinkRendering link) {
            this.links.add(link);
            return this;
        }

        public Builder addSummary(String summary) {
            this.summary = summary;
            return this;
        }

        public ResourceRendering build() {
            return new ResourceRendering(kind, mixins, attributes.build(), actions, id, title, links, summary);
        }
    }

}
