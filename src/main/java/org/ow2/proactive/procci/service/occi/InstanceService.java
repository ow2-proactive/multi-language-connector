package org.ow2.proactive.procci.service.occi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    /**
     * Give a compute from the data stored in Cloud-automation-service
     *
     * @param id is the id of compute
     * @return a compute
     * @throws IOException
     * @throws ClientException
     */
    public Optional<Entity> getEntity(String id) throws IOException, ClientException {
        Optional<Model> computeModel = cloudAutomationInstanceClient.getInstanceByVariable(ID_NAME,
                ConvertUtils.formatURL(id));
        if (!computeModel.isPresent()) {
            return Optional.empty();
        } else {
            return Optional.of(new ComputeBuilder(computeModel.get()).addMixins(
                    pullMixinFromCloudAutomation(id)).build());
        }
    }

    /**
     * Give a compute without mixins in order to avoid infinite loop
     *
     * @param id is the id of the compute
     * @return a compute without the object references set
     * @throws IOException
     * @throws ClientException
     */
    public Optional<Entity> getMockedEntity(String id) throws IOException, ClientException {
        Optional<Model> computeModel = cloudAutomationInstanceClient.getInstanceByVariable(ID_NAME,
                ConvertUtils.formatURL(id));
        if (!computeModel.isPresent()) {
            return Optional.empty();
        } else {
            return Optional.of(new ComputeBuilder(computeModel.get()).build());
        }
    }

    /**
     * Create a list of entity rendering  containing the rendering of all entity created
     *
     * @return a list of entity rendering
     * @throws IOException
     * @throws ClientException
     */
    public List<EntityRendering> getInstancesRendering() throws IOException, ClientException {
        JSONObject resources = cloudAutomationInstanceClient.getRequest();

        List<Model> models = (List<Model>) resources.keySet()
                .stream()
                .map(key -> new Model((JSONObject) resources.get(key)))
                .collect(Collectors.toList());

        List<EntityRendering> results = new ArrayList<>(models.size());
        for (Model model : models) {
            ComputeBuilder computeBuilder = new ComputeBuilder(model);
            computeBuilder.addMixins(pullMixinFromCloudAutomation(computeBuilder.getUrl().get()));
            results.add(computeBuilder.build().getRendering());
        }

        return results;
    }

    /**
     * Send the service to cloud automation in order to create the instance and update the data
     *
     * @param compute is the compute that will be created
     * @return a compute created from the server response
     * @throws IOException     if the response was not parsable
     * @throws ClientException if there is an error in the service sent to the server
     */
    public Compute create(Compute compute)
            throws IOException, ClientException {

        //add the compute reference in all his mixins
        compute.getMixins().stream().forEach(mixin -> mixin.addEntity(compute));

        //update the references between mixin and compute
        mixinService.addEntity(compute);

        //create a new compute from the response to the compute creation service sent to cloud-automation-service
        Compute computeResult = new ComputeBuilder(
                new Model(cloudAutomationInstanceClient.postRequest(
                        compute.toCloudAutomationModel("create").getJson())))
                .addMixins(pullMixinFromCloudAutomation(compute.getId()))
                .build();

        return compute;
    }

    /**
     * Give the mixins of a compute
     *
     * @param computeId the id of the compute
     * @return a list of mixin realated to the compute
     * @throws IOException
     * @throws ClientException
     */
    private List<Mixin> pullMixinFromCloudAutomation(String computeId) throws IOException, ClientException {

        Set<String> mixinsName = mixinService.getEntityMixinNames(computeId);
        List<Mixin> mixins = new ArrayList<>(mixinsName.size());
        for (String mixin : mixinsName) {
            mixins.add(mixinService.getMixinMockByTitle(mixin));
        }
        return mixins;
    }
}
