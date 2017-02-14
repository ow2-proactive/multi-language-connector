/*
 * ProActive Parallel Suite(TM):
 * The Open Source library for parallel and distributed
 * Workflows & Scheduling, Orchestration, Cloud Automation
 * and Big Data Analysis on Enterprise Grids & Clouds.
 *
 * Copyright (c) 2007 - 2017 ActiveEon
 * Contact: contact@activeeon.com
 *
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation: version 3 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 */
package org.ow2.proactive.procci.model.occi.metamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.MissingAttributesException;
import org.ow2.proactive.procci.model.exception.SyntaxException;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelAttributes;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.AttributeRendering;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.MixinRendering;
import org.ow2.proactive.procci.service.occi.InstanceService;
import org.ow2.proactive.procci.service.occi.MixinService;

import lombok.AccessLevel;
import lombok.Getter;


/**
 * Created by the Activeeon Team on 22/09/16.
 * <p>
 * Mixin Builder enables to easily create a mixin
 * <p>
 * In order to avoid infinite loop construction the entities and depends are mock during construction and doesn't have mixins references
 */
public class MixinBuilder {

    @Getter(value = AccessLevel.PROTECTED)
    private final String scheme;

    @Getter(value = AccessLevel.PROTECTED)
    private final String term;

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
     * @param instanceService is the instances manager
     * @param mixinRendering  is the rendering mixin
     */
    public MixinBuilder(MixinService mixinService, InstanceService instanceService, MixinRendering mixinRendering)
            throws ClientException {
        this.scheme = Optional.ofNullable(mixinRendering.getScheme())
                              .orElseThrow(() -> new MissingAttributesException("scheme", "mixin"));
        this.term = Optional.ofNullable(mixinRendering.getTerm())
                            .orElseThrow(() -> new MissingAttributesException("term", "mixin"));
        this.title = Optional.ofNullable(mixinRendering.getTitle()).orElse(this.term);
        this.attributes = convertAttributesMap(Optional.ofNullable(mixinRendering.getAttributes())
                                                       .orElse(new HashMap()));
        //action are not manage yet
        this.actions = new ArrayList<>();

        this.depends = Optional.ofNullable(mixinRendering.getDepends())
                               .orElse(new ArrayList<>())
                               .stream()
                               .map(depend -> mixinService.getEntitiesFreeMixinByTitle(depend))
                               .collect(Collectors.toList());

        this.applies = Optional.ofNullable(mixinRendering.getApplies())
                               .orElse(new ArrayList<>())
                               .stream()
                               .map(apply -> InfrastructureKinds.getKind(apply)
                                                                .orElseThrow(() -> new SyntaxException(apply, "Kind")))
                               .collect(Collectors.toList());

        this.entities = Optional.ofNullable(mixinRendering.getEntities())
                                .map(entitiesId -> new ArrayList<>(entitiesId))
                                .orElse(new ArrayList<>())
                                .stream()
                                .map(entityId -> instanceService.getMixinsFreeEntity(entityId))
                                .filter(entity -> entity.isPresent())
                                .map(entity -> entity.get())
                                .collect(Collectors.toList());

    }

    private Set<Attribute> convertAttributesMap(Map<String, AttributeRendering> attributeMap) {
        return attributeMap.keySet()
                           .stream()
                           .map(key -> new Attribute.Builder(key, attributeMap.get(key)).build())
                           .collect(Collectors.toSet());
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

    public MixinBuilder attributes(Map attributes) throws ClientException {
        this.title = readAttributeAsString(attributes, MetamodelAttributes.CATEGORY_TITLE_NAME).orElse(this.title);
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

    /**
     * Construct a mixin without entities in order to avoid cycle loop
     *
     * @return a mixin witout entities
     * @throws ClientException
     */
    public Mixin entitiesFreeMixinBuild() {
        this.entities = new ArrayList<>();
        return build();
    }

    protected Optional<String> readAttributeAsString(Map attributes, String key) throws SyntaxException {
        return Optional.ofNullable(attributes.get(key)).map(attribute -> {
            if (attribute instanceof String) {
                return (String) attribute;
            } else {
                throw new SyntaxException(key, "String");
            }
        });
    }

}
