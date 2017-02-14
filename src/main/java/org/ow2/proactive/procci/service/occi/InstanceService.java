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

import static org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelAttributes.ID_NAME;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.occi.infrastructure.ComputeBuilder;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureIdentifiers;
import org.ow2.proactive.procci.model.occi.metamodel.Entity;
import org.ow2.proactive.procci.model.occi.metamodel.Resource;
import org.ow2.proactive.procci.model.occi.metamodel.ResourceBuilder;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.EntityRendering;
import org.ow2.proactive.procci.model.occi.platform.bigdata.SwarmBuilder;
import org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataIdentifiers;
import org.ow2.proactive.procci.model.utils.ConvertUtils;
import org.ow2.proactive.procci.service.CloudAutomationInstanceClient;
import org.ow2.proactive.procci.service.transformer.TransformerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * This class enable the user to get and create instances
 */
@Component
public class InstanceService {

    @Autowired
    private CloudAutomationInstanceClient cloudAutomationInstanceClient;

    /**
     * Get an entity from the data stored in Cloud-automation-service
     *
     * @param id is the id of the entity
     * @param transformerProvider the transformer provider for an entity inherited type
     * @return an entity
     * @throws ClientException
     */
    public Optional<Entity> getEntity(String id, TransformerProvider transformerProvider) {
        return cloudAutomationInstanceClient.getInstanceModel(ID_NAME, ConvertUtils.formatURL(id), transformerProvider)
                                            .filter(instanceModel -> transformerProvider.isInstanceOfType(instanceModel))
                                            .map(instanceModel -> (Entity) instanceModel);
    }

    /**
     * Get an entity without mixins in order to avoid infinite loop
     *
     * @param id is the id of the compute
     * @return a compute without the object references set
     * @throws ClientException
     */
    public Optional<Entity> getMixinsFreeEntity(String id) {
        return cloudAutomationInstanceClient.getInstanceByVariable(ID_NAME, ConvertUtils.formatURL(id))
                                            .map(model -> new ComputeBuilder(model).build());
    }

    /**
     * Get the list of entity rendering from all the entities created
     *
     * @return a list of entity rendering
     * @throws ClientException
     */
    public List<EntityRendering> getInstancesRendering(MixinService mixinService) {

        return cloudAutomationInstanceClient.getModels()
                                            .stream()
                                            .filter(model -> model.getVariables().containsKey(ID_NAME))
                                            .map(model -> getResourceBuilder(model))
                                            .map(resourceBuilder -> (resourceBuilder).addMixins(mixinService.getMixinsByEntityId((resourceBuilder).getUrl()
                                                                                                                                                  .orElse(""))))
                                            .map(resourceBuilder -> (resourceBuilder).build().getRendering())
                                            .collect(Collectors.toList());
    }

    /**
     * Send the request to cloud automation in order to create the instance and update the data
     *
     * @param resource the resource that will be created
     * @return a compute created from the server response
     * @throws ClientException if there is an error in the service sent to the server
     */
    public Resource create(Resource resource, TransformerProvider transformerProvider, MixinService mixinService) {

        final String CREATION_KEYWORD = "create";

        //update the references between mixin and compute
        mixinService.addEntity(resource);

        //create a resource according to the creation request sent to cloud-automation-service
        return (Resource) cloudAutomationInstanceClient.postInstanceModel(resource,
                                                                          CREATION_KEYWORD,
                                                                          transformerProvider);
    }

    private ResourceBuilder getResourceBuilder(Model model) {

        switch (model.getServiceModel()) {
            case BigDataIdentifiers.SWARM_MODEL:
                return new SwarmBuilder(model);
            case InfrastructureIdentifiers.COMPUTE_MODEL:
                return new ComputeBuilder(model);
            default:
                return new ResourceBuilder(model);
        }
    }
}
