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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.ow2.proactive.procci.model.InstanceModel;
import org.ow2.proactive.procci.model.ModelConstant;
import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.exception.CloudAutomationClientException;
import org.ow2.proactive.procci.model.exception.CloudAutomationServerException;
import org.ow2.proactive.procci.model.exception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.swagger.client.api.ServiceInstanceRestApi;


/**
 * Created by the Activeeon Team on 06/10/16.
 */
@Service
public class RequestUtils {

    private final Logger logger = LoggerFactory.getLogger(RequestUtils.class);

    @Autowired
    private ServiceInstanceRestApi serviceInstanceRestApi;

    /**
     * Get the deployed instances from Cloud Automation Model
     *
     * @return a json object containing the service results
     */
    public JSONObject getRequest() {
        return parseJSON(serviceInstanceRestApi.getRunningServiceInstancesUsingGET());
    }

    /**
     * Send a service to pca service with a header containing the session id and sending content
     *
     * @param content is which is send to the cloud automation service
     * @return the information about gathered from cloud automation service
     */
    public JSONObject postRequest(JSONObject content) {
        return parseJSON(serviceInstanceRestApi.createRunningServiceInstancesUsingPOST(content.toJSONString(),
                                                                                       getSessionId()));
    }

    /**
     * @param serviceModel the model describing the service
     */
    public void deleteRequest(Model serviceModel) {
        serviceInstanceRestApi.deleteRunningServiceInstancesUsingDELETE(getSessionId(),
                                                                        serviceModel.getJson().toJSONString());
    }

    /**
     * Read an http respond and convert it into a string
     *
     * @param response is the http response
     * @return a string containing the information from response
     */
    private String getResponseString(HttpResponse response) throws IOException {
        StringBuffer serverOutput = new StringBuffer();

        BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

        String output;
        while ((output = br.readLine()) != null) {
            serverOutput.append(output);
        }
        return serverOutput.toString();
    }

    /**
     * Get the property from the configuration file config.properties
     *
     * @param propertyKey is the key of the variable
     * @return the value of the variable
     */
    public String getProperty(String propertyKey) {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
            return prop.getProperty(propertyKey);

        } catch (IOException ex) {
            logger.error("Unable to get the cloud automation service url from config.properties", ex);
            throw new ServerException();
        }
    }

    /**
     * Send a service to the scheduler with the name and the password from the configuration file in order to get the
     * session id
     *
     * @return the session id
     */
    public String getSessionId() {
        final String SCHEDULER_LOGIN_URL = getProperty("scheduler.login.endpoint");
        final String SCHEDULER_REQUEST = "username=" + getProperty("login.name") + "&password=" +
                                         getProperty("login.password");

        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost postRequest = new HttpPost(SCHEDULER_LOGIN_URL);
            StringEntity input = new StringEntity(SCHEDULER_REQUEST, ContentType.APPLICATION_FORM_URLENCODED);
            postRequest.setEntity(input);

            HttpResponse response = httpClient.execute(postRequest);

            return readHttpResponse(response, SCHEDULER_LOGIN_URL, SCHEDULER_REQUEST);
        } catch (IOException ex) {
            logger.error("Unable to get the session id", ex);
            logError(SCHEDULER_LOGIN_URL, SCHEDULER_REQUEST);
            throw new ServerException();
        }

    }

    /**
     * The method parse the server response
     * if the response is valid it returns the string value
     * if the response is not valid it throws an exception then it logs the error
     *
     * @param response is an HttpResponse containing the server response
     * @param url is where the request was sent
     * @param request is the string request sent to the server
     * @return a string which contains the server response
     */
    public String readHttpResponse(HttpResponse response, String url, String request) {
        int status = response.getStatusLine().getStatusCode();
        String responseOutput;
        try {
            responseOutput = getResponseString(response);
        } catch (IOException ex) {
            logger.error("Unable to read the http response in RequestUtils::checkStatus", ex);
            logError(url, request);
            throw new ServerException();
        }
        if (status >= 400 && status < 500) {
            logger.error("client error : " + responseOutput);
            logError(url, request);
            throw new CloudAutomationClientException(response.getStatusLine().getReasonPhrase());
        }
        if (status >= 300) {
            logger.error("server error: " + responseOutput);
            logError(url, request);
            throw new CloudAutomationServerException(response.getStatusLine().getReasonPhrase(), url, request);
        }
        return responseOutput;
    }

    private void logError(String url, String request) {
        logger.error("url : " + url);
        logger.error("request : " + request);
    }

    private JSONObject parseJSON(String jsonString) {
        try {
            return (JSONObject) new JSONParser().parse(jsonString);
        } catch (ParseException ex) {
            logger.error(" Parse exception in RequestUtils::parseJSON", ex);
            throw new ServerException();
        }
    }

}
