/*
 * ProActive Parallel Suite(TM): The Java(TM) library for
 *    Parallel, Distributed, Multi-Core Computing for
 *    Enterprise Grids & Clouds
 *
 * Copyright (C) 2013-2015 ActiveEon
 * 
 * Contact: proactive@ow2.org or contact@activeeon.com
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; version 3 of
 * the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 *
 *  Initial developer(s):               The ProActive Team
 *                        http://proactive.inria.fr/team_members.htm
 *  Contributor(s):
 *
 * $$ACTIVEEON_INITIAL_DEV$$
 */

package org.ow2.proactive.procci.model.occi.metamodel;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.exception.CloudAutomationException;
import org.ow2.proactive.procci.model.exception.MissingAttributesException;
import org.ow2.proactive.procci.model.exception.SyntaxException;
import org.ow2.proactive.procci.model.occi.metamodel.constants.Attributes;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.AttributeRendering;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.MixinRendering;
import org.ow2.proactive.procci.request.CloudAutomationVariables;
import com.google.common.collect.ImmutableList;
import lombok.Getter;

import static org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes.OS_TEMPLATE;

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
    public Mixin(String scheme, String term, String title, Set<Attribute> attributes,
            List<Action> actions, List<Mixin> depends, List<Kind> applies,
            List<Entity> entities) {
        super(scheme, term, title, setAttributes(attributes));
        this.actions = new ImmutableList.Builder<Action>().addAll(actions).build();
        this.depends = new ImmutableList.Builder<Mixin>().addAll(depends).build();
        this.applies = new ImmutableList.Builder<Kind>().addAll(applies).build();
        this.entities = entities;
    }

    private static Set<Attribute> setAttributes(Set<Attribute> paramAttributes) {
        Set<Attribute> attributes = new HashSet<>();
        attributes.addAll(paramAttributes);
        attributes.add(Attributes.DEPENDS);
        attributes.add(Attributes.APPLIES);
        attributes.add(Attributes.MIXIN_ENTITIES);
        attributes.add(Attributes.MIXIN_ACTIONS);
        return attributes;
    }

    public static Mixin getMixinByTitle(
            String title) throws CloudAutomationException, IOException, MissingAttributesException, SyntaxException {
        String mixinString = null;
        try {
            mixinString = CloudAutomationVariables.get(title);
        } catch (CloudAutomationException ex) {
            throw new SyntaxException(title);
        }

        MixinRendering mixinRendering = MixinRendering.convertMixinFromString(mixinString);
        return new MixinBuilder(mixinRendering).build();
    }

    public Model.Builder toCloudAutomationModel(Model.Builder cloudAutomation) {
        cloudAutomation.addVariable(OS_TEMPLATE, this.getTerm());
        return cloudAutomation;
    }

    public MixinRendering getRendering() {
        return MixinRendering.builder()
                .scheme(this.getScheme())
                .term(this.getTerm())
                .title(this.getTitle())
                .attributes(generateAttributeMap())
                .actions(this.actions
                        .stream()
                        .map(action -> action.getTitle())
                        .collect(Collectors.toList()))
                .depends(this.depends
                        .stream()
                        .map(depend -> depend.getTitle())
                        .collect(Collectors.toList()))
                .applies(this.applies
                        .stream()
                        .map(apply -> apply.getTitle())
                        .collect(Collectors.toList()))
                .location("/" + this.getTitle())
                .build();
    }

    private Map<String, AttributeRendering> generateAttributeMap() {
        Map<String, AttributeRendering> map = new HashMap();
        this.getAttributes().forEach(attribute -> map.put(attribute.getName(), attribute.getRendering()));
        return map;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void deleteEntity(Entity entity) {
        entities.remove(entity);
    }

}
