package org.ow2.proactive.procci.service.occi;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.occi.infrastructure.Compute;
import org.ow2.proactive.procci.model.occi.infrastructure.ComputeBuilder;
import org.ow2.proactive.procci.model.occi.metamodel.Entity;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.EntityRendering;
import org.ow2.proactive.procci.model.utils.ConvertUtils;
import org.ow2.proactive.procci.service.CloudAutomationInstanceClient;
import org.ow2.proactive.procci.service.transformer.TransformerManager;
import org.ow2.proactive.procci.service.transformer.TransformerType;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.ow2.proactive.procci.model.occi.metamodel.constants.Attributes.ID_NAME;

/**
 * This class enable the user to get and create instances
 */
@Component
public class InstanceService {

    @Autowired
    private CloudAutomationInstanceClient cloudAutomationInstanceClient;

    @Autowired
    private MixinService mixinService;

    @Autowired
    private TransformerManager transformerManager;

    /**
     * Give a compute from the data stored in Cloud-automation-service
     *
     * @param id is the id of compute
     * @return a compute
     * @throws ClientException
     */
    public Optional<Entity> getEntity(String id) throws ClientException {
        return cloudAutomationInstanceClient.getInstanceByVariable(ID_NAME, ConvertUtils.formatURL(id))
                .map(model -> new ComputeBuilder(model)
                        .addMixins(pullMixinFromCloudAutomation(id))
                        .build());

    }

    /**
     * Give a compute without mixins in order to avoid infinite loop
     *
     * @param id is the id of the compute
     * @return a compute without the object references set
     * @throws ClientException
     */
    public Optional<Entity> getMockedEntity(String id) throws ClientException {
        return cloudAutomationInstanceClient.getInstanceByVariable(ID_NAME, ConvertUtils.formatURL(id))
                .map(model -> new ComputeBuilder(model)
                        .build());
    }

    /**
     * Create a list of entity rendering  containing the rendering of all entity created
     *
     * @return a list of entity rendering
     * @throws ClientException
     */
    public List<EntityRendering> getInstancesRendering() throws ClientException {
        JSONObject resources = cloudAutomationInstanceClient.getRequest();

        return ((List) resources.keySet()
                .stream()
                .map(key -> new Model((JSONObject) resources.get(key)))
                .map(model -> new ComputeBuilder((Model) model))
                .map(computeBuilder -> ((ComputeBuilder) computeBuilder)
                        .addMixins(pullMixinFromCloudAutomation(((ComputeBuilder) computeBuilder).getUrl()
                                .orElse(""))))
                .map(computeBuilder -> ((ComputeBuilder) computeBuilder).build().getRendering())
                .collect(Collectors.toList()));
    }

    /**
     * Send the service to cloud automation in order to create the instance and update the data
     *
     * @param compute is the compute that will be created
     * @return a compute created from the server response
     * @throws ClientException if there is an error in the service sent to the server
     */
    public Compute create(Compute compute)
            throws ClientException {

        //add the compute reference in all his mixins
        compute.getMixins().stream().forEach(mixin -> mixin.addEntity(compute));

        //update the references between mixin and compute
        mixinService.addEntity(compute);

        //create a new compute from the response to the compute creation service sent to cloud-automation-service
        Compute computeResult = new ComputeBuilder(
                new Model(cloudAutomationInstanceClient.postRequest(
                        transformerManager.getTransformerProvider(TransformerType.COMPUTE)
                                .toCloudAutomationModel(compute,"create").getJson())))
                .addMixins(pullMixinFromCloudAutomation(compute.getId()))
                .build();

        return compute;
    }

    /**
     * Give the mixins of a compute
     *
     * @param computeId the id of the compute
     * @return a list of mixin realated to the compute
     * @throws ClientException
     */
    private List<Mixin> pullMixinFromCloudAutomation(String computeId) throws ClientException {

        return mixinService.getEntityMixinNames(computeId)
                .stream()
                .map(mixin -> mixinService.getMixinMockByTitle(mixin))
                .collect(Collectors.toList());

    }
}
