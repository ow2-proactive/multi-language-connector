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

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelAttributes;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.AttributeRendering;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.MixinRendering;

import com.google.common.collect.ImmutableList;

import lombok.Getter;


/**
 * Mixin is an extension mecanism which enables to new resource capablilities
 */
@Getter
public class Mixin extends Category {

    private final ImmutableList<Action> actions;

    private final ImmutableList<Mixin> depends;

    private final ImmutableList<Kind> applies;

    private List<Entity> entities;

    /**
     * Mixin constructor
     *
     * @param scheme     is the categorisation scheme
     * @param term       is the unique identifier of the category instance
     * @param title      is the display name of the instance
     * @param attributes is the set of resource attribute name
     * @param actions    are the actions defined by this mixin instances
     * @param depends    are the depends mixin instances
     * @param applies    is the list of kind this mixin instance applies to
     * @param entities   is the set of resource instances
     */
    public Mixin(String scheme, String term, String title, Set<Attribute> attributes, List<Action> actions,
            List<Mixin> depends, List<Kind> applies, List<Entity> entities) {
        super(scheme, term, title, createAttributeSet(attributes));
        this.actions = new ImmutableList.Builder<Action>().addAll(actions).build();
        this.depends = new ImmutableList.Builder<Mixin>().addAll(depends).build();
        this.applies = new ImmutableList.Builder<Kind>().addAll(applies).build();
        this.entities = entities;
    }

    private static Set<Attribute> createAttributeSet(Set<Attribute> paramAttributes) {
        Set<Attribute> attributes = new HashSet<>();
        attributes.addAll(paramAttributes);
        attributes.add(MetamodelAttributes.DEPENDS);
        attributes.add(MetamodelAttributes.APPLIES);
        attributes.add(MetamodelAttributes.MIXIN_ENTITIES);
        attributes.add(MetamodelAttributes.MIXIN_ACTIONS);
        return attributes;
    }

    public Model.Builder toCloudAutomationModel(Model.Builder cloudAutomation) {
        return cloudAutomation;
    }

    public MixinRendering getRendering() {

        return MixinRendering.builder()
                             .scheme(this.getScheme())
                             .term(this.getTerm())
                             .title(this.getTitle())
                             .attributes(generateAttributeMap())
                             .actions(mapTitle(this.actions))
                             .depends(mapTitle(this.depends))
                             .applies(mapTitle(this.applies))
                             .entities(this.entities.stream().map(entity -> entity.getId()).collect(Collectors.toSet()))
                             .location("/" + this.getTitle())
                             .build();
    }

    private List<String> mapTitle(List<? extends Category> input) {
        return input.stream().map(action -> action.getTitle()).collect(Collectors.toList());
    }

    private Map<String, AttributeRendering> generateAttributeMap() {
        Map<String, AttributeRendering> map = new HashMap();
        this.getAttributes().forEach(attribute -> map.put(attribute.getName(), attribute.getRendering()));
        return map;
    }

    /**
     * Add to the mixin the related entity
     *
     * @param entity is an entity which is related to the mixin
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void deleteEntity(Entity entity) {
        entities.remove(entity);
    }

}
