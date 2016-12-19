package org.ow2.proactive.procci.service.occi;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.CloudAutomationException;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.Identifiers;
import org.ow2.proactive.procci.model.occi.infrastructure.mixin.Contextualization;
import org.ow2.proactive.procci.model.occi.infrastructure.mixin.VMImage;
import org.ow2.proactive.procci.model.occi.metamodel.Entity;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import org.ow2.proactive.procci.model.occi.metamodel.MixinBuilder;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.MixinRendering;
import org.ow2.proactive.procci.service.CloudAutomationVariablesClient;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.ow2.proactive.procci.model.utils.ConvertUtils.mapObject;
import static org.ow2.proactive.procci.model.utils.ConvertUtils.readMappedObject;

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
        providerMixin = new ImmutableMap.Builder<String, Supplier<MixinBuilder>>()
                .put(Identifiers.VM_IMAGE, (() -> new VMImage.Builder()))
                .put(Identifiers.CONTEXTUALIZATION, (() -> new Contextualization.Builder()))
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
    public Mixin getMixinByTitle(String title) throws ClientException {

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
    public Mixin getMixinMockByTitle(String title) throws ClientException {

        MixinRendering mixinRendering = getMixinRenderingByTitle(title);
        return new MixinBuilder(this, instanceService, mixinRendering).buildMock();
    }

    /**
     * Give a list of mixin from their titles
     *
     * @param titles is the mixins titles
     * @return a list of Mixin
     * @throws ClientException if there is an error in the cloud automation service response
     */
    public List<Mixin> getMixinsByTitles(List<String> titles) throws ClientException {
        return titles.stream()
                .map(title -> getMixinByTitle(title))
                .collect(Collectors.toList());
    }

    /**
     * Give the list of the mixin name of an entity
     *
     * @param entityId is an occi entity
     * @return the list of the mixin related to the entity instance
     * @throws CloudAutomationException if there is an error in the cloud automation service response
     */
    public Set<String> getEntityMixinNames(String entityId) throws CloudAutomationException {
        String references = cloudAutomationVariablesClient.get(entityId);
        TypeReference<Set<String>> mapType = new TypeReference<Set<String>>() {
        };
        return readMappedObject(references, mapType);
    }

    /**
     * Add the entity to the database and update the mixins references
     *
     * @param entity is an occi entity
     * @throws ClientException if there is issue with cloud automation service response
     */
    public void addEntity(Entity entity) throws ClientException {

        Set<String> entityMixinsTitle = entity.getMixins().stream().map(mixin -> mixin.getTitle()).collect(
                Collectors.toSet());
        //add entity references
        cloudAutomationVariablesClient.post(entity.getId(), mapObject(entityMixinsTitle));


        //add entity to mixin references
        entity.getMixins().forEach(
                mixin -> setMixinReferences(mixin.getRendering())
        );

    }

    public void addMixin(Mixin mixin) throws ClientException {
        //add the new entity references
        cloudAutomationVariablesClient.post(mixin.getTitle(),
                mapObject(mixin.getRendering()));

        //add mixin to entity references
        for (String entityId : mixin.getEntities().stream().map(entity -> entity.getId()).collect(
                Collectors.toSet())) {
            addEntityReferences(entityId, mixin.getTitle());
        }
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
    private void setMixinReferences(MixinRendering mixinRendering) throws ClientException {
        try {
            Set<String> entitiesId = getMixinRenderingByTitle(mixinRendering.getTitle()).getEntities();
            mixinRendering.getEntities().addAll(entitiesId);
            cloudAutomationVariablesClient.update(mixinRendering.getTitle(),
                    mapObject(mixinRendering));
        } catch (CloudAutomationException ex) {
            cloudAutomationVariablesClient.post(mixinRendering.getTitle(),
                    mapObject(mixinRendering));
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
    private void addEntityReferences(String entityId, String mixinsId) throws ClientException {

        Set<String> entityReferences = new HashSet<>();
        entityReferences.add(mixinsId);
        try {
            entityReferences.addAll(getEntityMixinNames(entityId));
            cloudAutomationVariablesClient.update(entityId, mapObject(entityReferences));
        } catch (CloudAutomationException ex) {
            cloudAutomationVariablesClient.post(entityId, mapObject(entityReferences));
        }
    }

}
