package org.ow2.proactive.procci.model.occi.platform;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.SyntaxException;
import org.ow2.proactive.procci.model.occi.metamodel.Attribute;
import org.ow2.proactive.procci.model.occi.metamodel.Kind;
import org.ow2.proactive.procci.model.occi.metamodel.Link;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import org.ow2.proactive.procci.model.occi.metamodel.Resource;
import org.ow2.proactive.procci.model.occi.metamodel.ResourceBuilder;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.ResourceRendering;
import org.ow2.proactive.procci.model.occi.platform.constants.PlatformAttributes;
import org.ow2.proactive.procci.model.occi.platform.constants.PlatformKinds;
import org.ow2.proactive.procci.service.occi.MixinService;
import lombok.Getter;

public class Component extends Resource {

    @Getter
    private Optional<Status> status;

    public Component(Optional<String> url, Kind kind, Optional<String> title, List<Mixin> mixins,
            Optional<String> summary, List<Link> links, Optional<Status> status) {
        super(url, kind, title, mixins, summary, links);
        this.status = status;
    }

    public static Set<Attribute> createAttributeSet() {
        Set<Attribute> attributeSet = new HashSet<>();
        attributeSet.addAll(Resource.createAttributeSet());
        attributeSet.add(PlatformAttributes.STATUS);
        return attributeSet;
    }

    public static class Builder extends ResourceBuilder {

        protected Optional<Status> status;

        public Builder() {
            status = Optional.empty();
        }

        public Builder(Model cloudAutomation) throws SyntaxException {
            super(cloudAutomation);

            Map<String, String> attributes = cloudAutomation.getVariables();
            Optional<String> status = Optional.ofNullable(attributes.get(PlatformAttributes.STATUS_NAME));
            if (status.isPresent()) {
                this.status = Optional.of(Status.getStatusFromString(status.get()));
            } else {
                this.status = Optional.empty();
            }
        }

        public Builder(MixinService mixinService,
                ResourceRendering rendering) throws ClientException {
            super(mixinService, rendering);
            Optional<String> stringStatus = Optional.ofNullable(
                    rendering.getAttributes())
                    .map(attributes -> attributes.getOrDefault(PlatformAttributes.STATUS_NAME, null))
                    .filter(status -> status instanceof String)
                    .map(status -> (String) status);
            if (stringStatus.isPresent()) {
                this.status = Optional.of(Status.getStatusFromString(stringStatus.get()));
            } else {
                this.status = Optional.empty();
            }
        }

        public Component.Builder status(String status) throws SyntaxException {
            this.status = Optional.ofNullable(Status.getStatusFromString(status));
            return this;
        }

        @Override
        public Component.Builder url(String url) {
            this.url = Optional.ofNullable(url);
            return this;
        }

        @Override
        public Component.Builder title(String title) {
            this.title = Optional.ofNullable(title);
            return this;
        }

        @Override
        public Component.Builder addMixin(Mixin mixin) {
            this.mixins.add(mixin);
            return this;
        }

        @Override
        public Component.Builder addMixins(List<Mixin> mixins) {
            this.mixins.addAll(mixins);
            return this;
        }

        @Override
        public Component.Builder summary(String summary) {
            this.summary = Optional.ofNullable(summary);
            return this;
        }

        @Override
        public Component.Builder addLink(Link link) {
            this.links.add(link);
            return this;
        }


        @Override
        public Component build() throws ClientException {
            return new Component(this.getUrl(), PlatformKinds.COMPONENT, this.getTitle(), this.getMixins(),
                    this.getSummary(), this.getLinks(), status);
        }
    }


}
