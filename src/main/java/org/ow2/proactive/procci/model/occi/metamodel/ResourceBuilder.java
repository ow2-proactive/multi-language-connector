package org.ow2.proactive.procci.model.occi.metamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.MissingAttributesException;
import org.ow2.proactive.procci.model.exception.SyntaxException;
import org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelKinds;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.ResourceRendering;
import org.ow2.proactive.procci.model.utils.ConvertUtils;
import org.ow2.proactive.procci.service.occi.MixinService;
import lombok.Getter;

import static org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelAttributes.ENTITY_TITLE_NAME;
import static org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelAttributes.ID_NAME;
import static org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelAttributes.SUMMARY_NAME;

@Getter
public class ResourceBuilder {


    protected Optional<String> url;
    protected Optional<String> title;
    protected List<Mixin> mixins;
    protected Optional<String> summary;
    protected List<Link> links;

    public ResourceBuilder() {
        this.url = Optional.empty();
        this.title = Optional.empty();
        this.mixins = new ArrayList<>();
        this.summary = Optional.empty();
        this.links = new ArrayList<>();
    }

    public ResourceBuilder(Model cloudAutomation) {

        Map<String, String> attributes = cloudAutomation.getVariables();

        this.url = Optional.ofNullable(attributes.get(ID_NAME));
        this.title = Optional.ofNullable(attributes.get(ENTITY_TITLE_NAME));
        this.summary = Optional.ofNullable(attributes.get(SUMMARY_NAME));

        this.mixins = new ArrayList<>();
        this.links = new ArrayList<>();
    }

    public ResourceBuilder(MixinService mixinService,
            ResourceRendering rendering) throws ClientException {
        this.url = Optional.ofNullable(rendering.getId());
        this.title = ConvertUtils.convertStringFromObject(Optional.ofNullable(rendering.getAttributes())
                .map(attributes -> attributes.get(ENTITY_TITLE_NAME)));
        this.summary = ConvertUtils.convertStringFromObject(Optional.ofNullable(
                rendering.getAttributes()).map(attributes -> attributes.get(SUMMARY_NAME)));
        this.mixins = new ArrayList<>();
        for (String mixin : Optional.ofNullable(rendering.getMixins()).orElse(new ArrayList<>())) {
            this.mixins.add(mixinService.getMixinByTitle(mixin));
        }
        associateProviderMixin(mixinService, rendering.getAttributes());
        this.links = new ArrayList<>();
    }

    /**
     * Check all attributes and add in the mixin collection the attributes from mixin
     *
     * @param attributes is the attriutes list of the compute
     * @throws ClientException is thrown if there is an error during the mixin reading
     */
    void associateProviderMixin(MixinService mixinService,
            Map<String, Object> attributes) throws ClientException {
        if (attributes == null) {
            return;
        }

        attributes.keySet()
                .forEach(mixinName -> {
                    mixinService.getMixinBuilder(mixinName)
                            .ifPresent(mixinBuilder -> {
                                Object attributeMap = attributes.get(mixinName);
                                if (attributeMap instanceof Map) {
                                    this.mixins.add(mixinBuilder
                                            .attributes((Map) attributeMap)
                                            .build());
                                } else {
                                    throw new SyntaxException(attributeMap.toString(), "Map");
                                }
                            });
                });

    }

    public ResourceBuilder url(String url) {
        this.url = Optional.ofNullable(url);
        return this;
    }

    public ResourceBuilder title(String title) {
        this.title = Optional.ofNullable(title);
        return this;
    }

    public ResourceBuilder addMixin(Mixin mixin) {
        this.mixins.add(mixin);
        return this;
    }

    public ResourceBuilder addMixins(List<Mixin> mixins) {
        this.mixins.addAll(mixins);
        return this;
    }

    public ResourceBuilder summary(String summary) {
        this.summary = Optional.ofNullable(summary);
        return this;
    }

    public ResourceBuilder addLink(Link link) {
        this.links.add(link);
        return this;
    }

    public Resource build() throws ClientException {
        return new Resource(url, MetamodelKinds.RESOURCE, title, mixins, summary, links);
    }
}

