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
package org.ow2.proactive.procci.service.occi;

import static org.ow2.proactive.procci.model.utils.ConvertUtils.mapObject;
import static org.ow2.proactive.procci.model.utils.ConvertUtils.readMappedObject;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.codehaus.jackson.type.TypeReference;
import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.CloudAutomationClientException;
import org.ow2.proactive.procci.model.exception.CloudAutomationServerException;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureIdentifiers;
import org.ow2.proactive.procci.model.occi.infrastructure.mixin.Contextualization;
import org.ow2.proactive.procci.model.occi.infrastructure.mixin.VMImage;
import org.ow2.proactive.procci.model.occi.metamodel.Entity;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import org.ow2.proactive.procci.model.occi.metamodel.MixinBuilder;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.MixinRendering;
import org.ow2.proactive.procci.service.CloudAutomationVariablesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;


/**
 * Created by the Activeeon Team on 12/10/16.
 */

/**
 * This class manage the mixins, it give access to the mixin references and enables to access to the provider defined mixin
 */
@Component
public class MixinService {

    private final Map<String, Supplier<MixinBuilder>> providerMixin;

    @Autowired
    private InstanceService instanceService;

    @Autowired
    private CloudAutomationVariablesClient cloudAutomationVariablesClient;

    public MixinService() {
        providerMixin = new ImmutableMap.Builder<String, Supplier<MixinBuilder>>().put(InfrastructureIdentifiers.VM_IMAGE,
                                                                                       (() -> new VMImage.Builder()))
                                                                                  .put(InfrastructureIdentifiers.CONTEXTUALIZATION,
                                                                                       (() -> new Contextualization.Builder()))
                                                                                  .build();
    }

    /**
     * Give a mixin created by the provider
     *
     * @param mixinName the name of the mixin
     * @return a optional instance of the provider mixin or an empty optional according to the name
     */
    public Optional<MixinBuilder> getMixinBuilder(String mixinName) {
        return Optional.ofNullable(providerMixin.get(mixinName)).map(supplier -> supplier.get());
    }

    /**
     * Give a mixin from his title
     *
     * @param title is the mixin title
     * @return a mixin
     * @throws ClientException if there is an error in the cloud automation service response
     */
    public Mixin getMixinByTitle(String title) {

        MixinRendering mixinRendering = getMixinRenderingByTitle(title);
        return new MixinBuilder(this, instanceService, mixinRendering).build();
    }

    /**
     * Give a mixin without entities from his title
     *
     * @param title is the mixin title
     * @return a mixin without entities
     * @throws ClientException if there is an error in the cloud automation service response
     */
    public Mixin getMixinMockByTitle(String title) {

        MixinRendering mixinRendering = getMixinRenderingByTitle(title);
        return new MixinBuilder(this, instanceService, mixinRendering).buildMock();
    }

    /**
     * Give the mixins of an entity
     *
     * @param entityId the id of the entity
     * @return a list of mixin realated to the compute
     * @throws ClientException
     */
    public List<Mixin> getMixinsByEntityId(String entityId) {

        return this.getMixinNamesFromEntity(entityId).stream().map(mixin -> {
            System.out.println("mixin " + mixin);
            return this.getMixinMockByTitle(mixin);
        }).collect(Collectors.toList());
    }

    /**
     * Add the entity to the database and update the mixins references
     *
     * @param entity is an occi entity
     * @throws ClientException if there is issue with cloud automation service response
     */
    public void addEntity(Entity entity) {

        Set<String> entityMixinsTitle = entity.getMixins()
                                              .stream()
                                              .map(mixin -> mixin.getTitle())
                                              .collect(Collectors.toSet());
        //add entity references
        cloudAutomationVariablesClient.post(entity.getId(), mapObject(entityMixinsTitle));

        //add entity to mixin references
        entity.getMixins().forEach(mixin -> setMixinReferences(mixin.getRendering()));

    }

    /**
     * Add a mixin in the database and update the references
     * @param mixin
     */
    public void addMixin(Mixin mixin) {
        //add the new entity references
        cloudAutomationVariablesClient.post(mixin.getTitle(), mapObject(mixin.getRendering()));

        //add mixin to entity references
        for (String entityId : mixin.getEntities().stream().map(entity -> entity.getId()).collect(Collectors.toSet())) {
            addEntityReferences(entityId, mixin.getTitle());
        }
    }

    /**
     * Remove a mixin from the database and update the references
     * @param mixinTitle the title of the mixin to remove
     */
    public void removeMixin(String mixinTitle) {
        Mixin mixinToRemove = getMixinByTitle(mixinTitle);
        List<Entity> entities = mixinToRemove.getEntities();

        cloudAutomationVariablesClient.delete(mixinTitle);

        entities.forEach(entity -> {
            getMixinNamesFromEntity(entity.getId()).remove(mixinTitle);
        });

        entities.forEach(entity -> {
            Set<String> entityMixins = getMixinNamesFromEntity(entity.getId());
            entityMixins.remove(mixinTitle);
            cloudAutomationVariablesClient.update(entity.getId(), mapObject(entityMixins));
        });

    }

    /**
     *  Give a the name of each mixin
     *
     * @return a set containing the name of each mixin
     */
    public Set<String> getProviderMixinsName() {
        return providerMixin.keySet();
    }

    /**
     * Give the list of the mixin name of an entity
     *
     * @param entityId is an occi entity
     * @return the list of the mixin related to the entity instance
     * @throws CloudAutomationServerException if there is an error in the cloud automation service response
     */
    Set<String> getMixinNamesFromEntity(String entityId) {
        String references = cloudAutomationVariablesClient.get(entityId);

        TypeReference<Set<String>> mapType = new TypeReference<Set<String>>() {
        };
        return readMappedObject(references, mapType);
    }

    private MixinRendering getMixinRenderingByTitle(String title) throws ClientException {
        return MixinRendering.convertMixinFromString(cloudAutomationVariablesClient.get(title));
    }

    /**
     * if the mixin is already defined update the references however catch the not found excpetion thrown
     * and create the mixin references
     *
     * @param mixinRendering the mixin to add references
     * @throws ClientException
     */
    private void setMixinReferences(MixinRendering mixinRendering) {
        try {
            Set<String> entitiesId = getMixinRenderingByTitle(mixinRendering.getTitle()).getEntities();
            mixinRendering.getEntities().addAll(entitiesId);
            cloudAutomationVariablesClient.update(mixinRendering.getTitle(), mapObject(mixinRendering));
        } catch (CloudAutomationClientException ex) {
            cloudAutomationVariablesClient.post(mixinRendering.getTitle(), mapObject(mixinRendering));
        }
    }

    /**
     * if the entity id is already defined update the references however catch the not found excpetion thrown
     * and create the mixin references
     *
     * @param entityId is the entity to change the references
     * @param mixinsId is the reference to add
     * @throws ClientException
     */
    private void addEntityReferences(String entityId, String mixinsId) {

        Set<String> entityReferences = new HashSet<>();
        entityReferences.add(mixinsId);
        try {
            entityReferences.addAll(getMixinNamesFromEntity(entityId));
            cloudAutomationVariablesClient.update(entityId, mapObject(entityReferences));
        } catch (CloudAutomationServerException ex) {
            cloudAutomationVariablesClient.post(entityId, mapObject(entityReferences));
        }
    }

}
