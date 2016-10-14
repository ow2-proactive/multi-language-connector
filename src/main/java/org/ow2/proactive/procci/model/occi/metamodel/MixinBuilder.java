package org.ow2.proactive.procci.model.occi.metamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.CloudAutomationException;
import org.ow2.proactive.procci.model.exception.MissingAttributesException;
import org.ow2.proactive.procci.model.exception.SyntaxException;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.AttributeRendering;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.MixinRendering;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by mael on 22/09/16.
 */
public class MixinBuilder {

    @Autowired
    private ProviderMixin providerMixin;

    @Getter(value = AccessLevel.PROTECTED)
    private String scheme;
    @Getter(value = AccessLevel.PROTECTED)
    private String term;
    @Getter(value = AccessLevel.PROTECTED)
    private String title;
    @Getter(value = AccessLevel.PROTECTED)
    private Set<Attribute> attributes;
    @Getter(value = AccessLevel.PROTECTED)
    private List<Action> actions;
    @Getter(value = AccessLevel.PROTECTED)
    private List<Mixin> depends;
    @Getter(value = AccessLevel.PROTECTED)
    private List<Kind> applies;
    @Getter(value = AccessLevel.PROTECTED)
    private List<Entity> entities;

    public MixinBuilder(String scheme, String term) {
        this.scheme = scheme;
        this.term = term;
        this.title = this.term;
        this.attributes = new HashSet<>();
        this.actions = new ArrayList<>();
        this.depends = new ArrayList<>();
        this.applies = new ArrayList<>();
        this.entities = new ArrayList<>();
    }

    /**
     * Construct a mixin according to its rendering
     *
     * @param mixinRendering is the rendering mixin
     */
    public MixinBuilder(
            MixinRendering mixinRendering) throws ClientException, IOException{
        this.scheme = Optional.ofNullable(mixinRendering.getScheme()).orElseThrow(
                () -> new MissingAttributesException("scheme", "mixin"));
        this.term = Optional.ofNullable(mixinRendering.getTerm()).orElseThrow(
                () -> new MissingAttributesException("term", "mixin"));
        this.title = Optional.ofNullable(mixinRendering.getTitle()).orElse(this.term);
        this.attributes = convertAttributesMap(Optional.ofNullable(mixinRendering.getAttributes()).orElse(
                new HashMap()));
        //action are not manage yet
        this.actions = new ArrayList<>();
        this.depends = new ArrayList<>();
        for (String depends : Optional.ofNullable(mixinRendering.getDepends()).orElse(new ArrayList<>())) {
            this.depends.add(providerMixin.getMixinByTitle(depends));
        }
        this.applies = new ArrayList<>();
        for (String apply : Optional.ofNullable(mixinRendering.getApplies()).orElse(new ArrayList<>())) {
            this.applies.add(
                    InfrastructureKinds.getKind(apply).orElseThrow(() -> new SyntaxException(apply)));
        }
        this.entities = new ArrayList<>();
    }

    private Set<Attribute> convertAttributesMap(Map<String, AttributeRendering> attributeMap) {
        return attributeMap.keySet()
                .stream()
                .map(key -> new Attribute.Builder(key, attributeMap.get(key)).build())
                .collect(Collectors.toSet());
    }

    public MixinBuilder scheme(String scheme) {
        this.scheme = scheme;
        return this;
    }

    public MixinBuilder term(String term) {
        this.term = term;
        return this;
    }

    public MixinBuilder title(String title) {
        this.title = title;
        return this;
    }

    public MixinBuilder addAttribute(Attribute attribute) {
        this.attributes.add(attribute);
        return this;
    }

    public MixinBuilder addAction(Action action) {
        this.actions.add(action);
        return this;
    }

    public MixinBuilder addDepend(Mixin depend) {
        depends.add(depend);
        return this;
    }

    public MixinBuilder addApply(Kind apply) {
        this.applies.add(apply);
        return this;
    }

    public MixinBuilder addEntity(Entity entity) {
        this.entities.add(entity);
        return this;
    }


    /**
     * Build the instance of a mixin according to its scheme and term
     *
     * @return a mixin instance
     */
    public Mixin build() {
        return new Mixin(scheme, term, title, attributes, actions, depends, applies, entities);
    }

    public Mixin build(Map attributesMap) throws ClientException {
        return new Mixin(scheme, term, title, attributes, actions, depends, applies, entities);
    }

}
