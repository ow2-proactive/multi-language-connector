package org.ow2.proactive.procci.request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.CloudAutomationException;
import org.ow2.proactive.procci.model.exception.SyntaxException;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.Identifiers;
import org.ow2.proactive.procci.model.occi.infrastructure.mixin.VMImage;
import org.ow2.proactive.procci.model.occi.metamodel.Entity;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import org.ow2.proactive.procci.model.occi.metamodel.MixinBuilder;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.MixinRendering;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by the Activeeon Team on 12/10/16.
 */
@Component
public class ProviderMixin {

    private final Logger logger = LogManager.getLogger(this);

    private final ImmutableMap<String, Supplier<MixinBuilder>> providerMixin;

    @Autowired
    private CloudAutomationVariables cloudAutomationVariables;

    public ProviderMixin() {
        providerMixin = new ImmutableMap.Builder<String, Supplier<MixinBuilder>>()
                .put(Identifiers.VM_IMAGE, (() -> new VMImage.Builder(this)))
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
     * @throws IOException     if the response cannot be read
     * @throws ClientException if there is an error in the cloud automation service response
     */
    public Mixin getMixinByTitle(String title) throws IOException, ClientException {

        String mixinString = null;
        try {
            mixinString = cloudAutomationVariables.get(title);
        } catch (CloudAutomationException ex) {
            throw new SyntaxException(title);
        }

        MixinRendering mixinRendering = MixinRendering.convertMixinFromString(mixinString);
        return new MixinBuilder(mixinRendering).build();
    }

    /**
     * Give a list of mixin from their titles
     *
     * @param titles is the mixins titles
     * @return a list of Mixin
     * @throws IOException     if the response cannot be read
     * @throws ClientException if there is an error in the cloud automation service response
     */
    public List<Mixin> getMixinsByTitles(List<String> titles) throws IOException, ClientException {
        List<Mixin> mixins = new ArrayList<>();
        for (String title : titles) {
            mixins.add(getMixinByTitle(title));
        }
        return mixins;
    }

    /**
     * Give the list of the mixin name of an entity
     *
     * @param entityId is an occi entity
     * @return the list of the mixin related to the entity instance
     * @throws CloudAutomationException if there is an error in the cloud automation service response
     * @throws IOException              if the response cannot be read
     */
    public Set<String> getEntityMixinNames(String entityId) throws CloudAutomationException, IOException {
        String references = cloudAutomationVariables.get(entityId);
        System.out.println("objectid : " + entityId + " references : " + references);
        TypeReference<Set<String>> mapType = new TypeReference<Set<String>>() {
        };
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(references, mapType);
    }

    /**
     * Add the entity to the database and update the mixins references
     *
     * @param entity is an occi entity
     * @throws IOException     is thrown if the database reponse is not correctly parse
     * @throws ClientException if there is issue with cloud automation service response
     */
    public void addEntity(Entity entity) throws IOException, ClientException {
        Set<String> mixinId = entity.getMixins()
                .stream()
                .map(mixin -> mixin.getTitle())
                .collect(Collectors.toSet());
        try {
            logger.debug("update " + entity.getTitle().orElse(entity.getId()) + " references");
            cloudAutomationVariables.update(entity.getId(), new ObjectMapper().writeValueAsString(mixinId));
        } catch (CloudAutomationException ex) {
            logger.debug("post " + entity.getTitle().orElse(entity.getId()) + " references");
            cloudAutomationVariables.post(entity.getId(), new ObjectMapper()
                    .writeValueAsString(mixinId));
        }

        for (Mixin mixin : entity.getMixins()) {
            mixin.addEntity(entity);
            try {
                logger.debug("update " + entity.getTitle().orElse(entity.getId()) + " references");
                cloudAutomationVariables.update(mixin.getTitle(),
                        new ObjectMapper().writeValueAsString(mixin.getRendering()));
            } catch (CloudAutomationException ex) {
                logger.debug("post " + entity.getTitle().orElse(entity.getId()) + " references");
                cloudAutomationVariables.post(mixin.getTitle(),
                        new ObjectMapper().writeValueAsString(mixin.getRendering()));
            }
        }
    }

}
