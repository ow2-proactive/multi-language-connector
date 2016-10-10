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

import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.AttributeRendering;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.MixinRendering;
import org.ow2.proactive.procci.request.CloudAutomationException;


/**
 * Created by mael on 22/09/16.
 */
public class MixinBuilder {

    private String scheme;
    private String term;
    private String title;
    private Set<Attribute> attributes;
    private List<Action> actions;
    private List<Mixin> depends;
    private List<Kind> applies;
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
    public MixinBuilder(MixinRendering mixinRendering) throws CloudAutomationException, IOException {
        this.scheme = mixinRendering.getScheme();
        this.term = mixinRendering.getTerm();
        this.title = Optional.ofNullable(mixinRendering.getTitle()).orElse(this.term);
        this.attributes = convertAttributesMap(Optional.ofNullable(mixinRendering.getAttributes()).orElse(
                new HashMap<>()));
        this.actions = new ArrayList<>();
        this.depends = new ArrayList<>();
        for (String depends : Optional.ofNullable(mixinRendering.getDepends()).orElse(new ArrayList<>())) {
            this.depends.add(Mixin.getMixinByTitle(depends));
        }
        this.applies = Optional.ofNullable(
                mixinRendering.getApplies()
                        .stream()
                        .map(apply -> InfrastructureKinds.getKind(apply))
                        .collect(Collectors.toList()))
                .orElse(new ArrayList<>());
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

}
