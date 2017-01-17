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

import java.io.IOException;
import java.util.Optional;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.ow2.proactive.procci.model.ModelConstant;
import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.exception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final String INSTANCE_ENDPOINT = "cloud-automation-service.instances.endpoint";

    private final Logger logger = LoggerFactory.getLogger(CloudAutomationInstanceClient.class);

    @Autowired
    private RequestUtils requestUtils;

    /**
     * Get the deployed instances from Cloud Automation Model
     *
     * @return a json object containing the service results
     */
    public JSONObject getRequest() {
        final String url = requestUtils.getProperty(INSTANCE_ENDPOINT);
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet getRequest = new HttpGet(url);

            HttpResponse response = httpClient.execute(getRequest);
            String serverOutput = requestUtils.readHttpResponse(response, url, "GET");
            httpClient.close();
            return parseJSON(serverOutput);
        } catch (IOException ex) {
            logger.error(" IO exception in CloudAutomationInstanceClient::getRequest " + ", exception : " +
                         ex.getMessage());
            throw new ServerException();
        }
    }

    /**
     * Get the instance of cloud automation service and return the first occurance with variableValue matching variableName
     *
     * @param variableName  a key in variables
     * @param variableValue the value matching with the variableName key
     * @return the first occurance which match with variablename and variableValue
     */
    public Optional<Model> getInstanceByVariable(String variableName, String variableValue) {

        JSONObject instances = getRequest();

        return instances.keySet()
                        .stream()
                        .map(key -> ((JSONObject) instances.get(key)).get(ModelConstant.VARIABLES))
                        .filter(vars -> ((JSONObject) vars).containsKey(variableName))
                        .filter(vars -> ((JSONObject) vars).get(variableName).equals(variableValue))
                        .findFirst()
                        .map(vars -> ((JSONObject) vars).get(ModelConstant.INSTANCE_ID))
                        .map(id -> new Model((JSONObject) instances.get(id)));
    }

    /**
     * Send a service to pca service with a header containing the session id and sending content
     *
     * @param content is which is send to the cloud automation service
     * @return the information about gathered from cloud automation service
     */
    public JSONObject postRequest(JSONObject content) {

        final String PCA_SERVICE_SESSIONID = "sessionid";
        final String url = requestUtils.getProperty(INSTANCE_ENDPOINT);
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost postRequest = new HttpPost(url);
            postRequest.addHeader(PCA_SERVICE_SESSIONID, requestUtils.getSessionId());
            StringEntity input = new StringEntity(content.toJSONString());
            input.setContentType("application/json");
            postRequest.setEntity(input);

            HttpResponse response = httpClient.execute(postRequest);

            String serverOutput = requestUtils.readHttpResponse(response, url, "POST " + content.toJSONString());
            httpClient.close();
            return parseJSON(serverOutput);
        } catch (IOException ex) {
            logger.error(" IO exception in CloudAutomationInstanceClient::postRequest " + ", exception : " +
                         ex.getMessage());
            throw new ServerException();
        }
    }

    private JSONObject parseJSON(String jsonString) {
        try {
            return (JSONObject) new JSONParser().parse(jsonString);
        } catch (ParseException ex) {
            logger.error(" Parse exception in CloudAutomationInstanceClient::parseJSON " + ", exception : " +
                         ex.getMessage());
            throw new ServerException();
        }

    }

}
