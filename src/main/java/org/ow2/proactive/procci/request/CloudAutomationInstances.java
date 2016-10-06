package org.ow2.proactive.procci.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Properties;

import org.ow2.proactive.procci.model.ModelConstant;
import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by mael on 02/06/16.
 */

/**
 * Manage the connection and the request with Cloud Automation Microservices
 */

public class CloudAutomationInstances {

    private final Logger logger = LogManager.getRootLogger();

    /**
     * Get the deployed instances from Cloud Automation Model
     *
     * @return a json object containing the request results
     */
    public JSONObject getRequest() throws CloudAutomationException {
        final String url = RequestUtils.getInstance().getProperty("cloud-automation-service.instances.endpoint");
        JSONObject result = new JSONObject();
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet getRequest = new HttpGet(url);

            HttpResponse response = httpClient.execute(getRequest);
            String serverOutput = readHttpResponse(response);
            httpClient.close();
            result = (JSONObject) new JSONParser().parse(serverOutput);
        }
        catch (Exception ex){
            raiseException(ex);
        }

        return result;
    }


    /**
     * Get the instance of cloud automation service and return the first occurance with variableValue matching variableName
     *
     * @param variableName  a key in variables
     * @param variableValue the value matching with the variableName key
     * @return the first occurance which match with variablename and variableValue
     */
    public Optional<Model> getInstanceByVariable(String variableName,
            String variableValue) throws CloudAutomationException {
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
     * Send a request to pca service with a header containing the session id and sending content
     *
     * @param content is which is send to the cloud automation service
     * @return the information about gathered from cloud automation service
     * @throws CloudAutomationException is thrown if something failed during the connection
     */
    public JSONObject postRequest(JSONObject content) throws CloudAutomationException {

        final String PCA_SERVICE_SESSIONID = "sessionid";
        final String url = RequestUtils.getInstance().getProperty("cloud-automation-service.instances.endpoint");
        JSONObject result = new JSONObject();
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost postRequest = new HttpPost(url);
            postRequest.addHeader(PCA_SERVICE_SESSIONID, RequestUtils.getInstance().getSessionId());
            StringEntity input = new StringEntity(content.toJSONString());
            input.setContentType("application/json");
            postRequest.setEntity(input);

            HttpResponse response = httpClient.execute(postRequest);

            String serverOutput = readHttpResponse(response);
            httpClient.close();
            result = (JSONObject) new JSONParser().parse(serverOutput);
        } catch (Exception ex) {
            raiseException(ex);
        }
        return result;
    }


    /**
     * Read an http respond and convert it into a string
     *
     * @param response is the http response
     * @return a string containing the information from response
     * @throws IOException occur if problem occur with the buffer
     */
    private String readHttpResponse(HttpResponse response) throws IOException {
        StringBuffer serverOutput = new StringBuffer();
        if (response.getStatusLine().getStatusCode() != 200) {

            throw new RuntimeException("Send Request Failed: HTTP error code : "
                    + response.getStatusLine().getStatusCode());
        }

        BufferedReader br = new BufferedReader(
                new InputStreamReader((response.getEntity().getContent())));

        String output;
        while ((output = br.readLine()) != null) {
            serverOutput.append(output);
        }
        return serverOutput.toString();
    }

    /**
     * Raise an CloudAutomation exception and log it
     *
     * @param ex the exception to be logged
     * @throws CloudAutomationException is an exception which occur during the connecton with cloud automation service
     */
    private void raiseException(Exception ex) throws CloudAutomationException {
        logger.error(this.getClass(), ex);
        throw new CloudAutomationException(ex.getMessage());
    }
}
