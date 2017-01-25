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
package org.ow2.proactive.procci.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.ow2.proactive.procci.model.InstanceModel;
import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.service.transformer.TransformerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by the Activeeon Team on 02/06/16.
 */

/**
 * Manage the connection and the service with Cloud Automation Microservices
 */
@Service
public class CloudAutomationInstanceClient {

    static final String PCA_INSTANCES_ENDPOINT = "cloud-automation-service.instances.endpoint";

    @Autowired
    private RequestUtils requestUtils;

    /**
     *  Give the list of models saved in cloud-automation
     * @return a list of Model
     */
    public List<Model> getModels() {
        JSONObject jsonModels = requestUtils.getRequest(requestUtils.getProperty(PCA_INSTANCES_ENDPOINT));
        return (List) jsonModels.keySet()
                                .stream()
                                .map(key -> jsonModels.get(key))
                                .map(jsonModel -> new Model((JSONObject) jsonModel))
                                .collect(Collectors.toList());
    }

    /**
     * Get the cloud automation model from the database which matches with the parameters
     *
     * @param variableName  a key in variables
     * @param variableValue the value matching with the variableName key
     * @return the first occurance which match with variableName and variableValue
     */
    public Optional<Model> getInstanceByVariable(String variableName, String variableValue) {

        return getModels().stream()
                          .filter(model -> variableValue.equals(model.getVariables().get(variableName)))
                          .findFirst();
    }

    /**
     *  Give an optional containing a instance model if the parameters match with an instance in cloud automation
     * @param variableName is a key in the variables for the cloud automation model
     * @param variableValue is a value in the variables for the cloud automation model
     * @param transformerProvider is a transformer for converting the cloud automation model into a instance model
     * @return an instance model if the parameters match otherwise return an empty optional
     */
    public Optional<InstanceModel> getInstanceModel(String variableName, String variableValue,
            TransformerProvider transformerProvider) {
        return getInstanceByVariable(variableName,
                                     variableValue).map(model -> transformerProvider.toInstanceModel(model));
    }

    /**
     * Create an instance in cloud automation from instanceModel
     * @param instanceModel is the model that is used to create the instance
     * @param actionType is the action to apply on the instance
     * @param transformerProvider is the transformer to apply on the instance model
     * @return a instance model return by cloud automation
     */
    public InstanceModel postInstanceModel(InstanceModel instanceModel, String actionType,
            TransformerProvider transformerProvider) {
        return transformerProvider.toInstanceModel(new Model(requestUtils.postRequest(transformerProvider.toCloudAutomationModel(instanceModel,
                                                                                                                                 actionType)
                                                                                                         .getJson(),
                                                                                      requestUtils.getProperty(PCA_INSTANCES_ENDPOINT))));
    }

}
