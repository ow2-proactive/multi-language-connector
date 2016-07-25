package org.ow2.proactive.procci.request;

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
import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by mael on 02/06/16.
 */

/**
 * Manage the connection and the request with Cloud Automation Microservices
 */

public class CloudAutomationRequest {

    private final Logger logger = LogManager.getRootLogger();

    /**
     * Get the deployed instances from Cloud Automation Model
     *
     * @return a json object containing the request results
     */
    @Autowired
    public JSONObject getRequest() throws CloudAutomationException {
        final String url = getProperty("cloud-automation-service.instances.endpoint");
        JSONObject result = new JSONObject();
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet getRequest = new HttpGet(url);

            HttpResponse response = httpClient.execute(getRequest);
            String serverOutput = readHttpResponse(response);
            httpClient.close();
            result = (JSONObject) new JSONParser().parse(serverOutput);
        } catch (IOException ex) {
            raiseException(ex);
        } catch (ParseException ex) {
            raiseException(ex);
        } catch (Exception ex) {
            raiseException(ex);
        }

        return result;
    }

    /**
     * Give the instance information thanks to its name
     *
     * @param id is the instance name
     * @return the instance information
     * @throws CloudAutomationException is thrown if an error occur during the connection with CAS or the login
     */
    @Autowired
    public Model getRequestByName(String id) throws CloudAutomationException {
        JSONObject jsonModel = (JSONObject) getRequest().get(id);
        if(jsonModel!=null){
            return new Model(jsonModel);
        }
        else{
            return null;
        }
    }


    /**
     * Send a request to pca service with a header containing the session id and sending content
     *
     * @param content is which is send to the cloud automation service
     * @return the information about gathered from cloud automation service
     * @throws CloudAutomationException is thrown if something failed during the connection
     */
    @Autowired
    public JSONObject postRequest(JSONObject content) throws CloudAutomationException {

        final String PCA_SERVICE_SESSIONID = "sessionid";
        final String url = getProperty("cloud-automation-service.instances.endpoint");
        JSONObject result = new JSONObject();
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost postRequest = new HttpPost(url);
            postRequest.addHeader(PCA_SERVICE_SESSIONID, getSessionId());
            StringEntity input = new StringEntity(content.toJSONString());
            input.setContentType("application/json");
            postRequest.setEntity(input);

            HttpResponse response = httpClient.execute(postRequest);

            String serverOutput = readHttpResponse(response);
            httpClient.close();
            result = (JSONObject) new JSONParser().parse(serverOutput);
        } catch (IOException ex) {
            raiseException(ex);
        } catch (ParseException ex) {
            raiseException(ex);
        } catch (Exception ex) {
            raiseException(ex);
        }
        return result;
    }


    /**
     * Get the property from the configuration file config.properties
     *
     * @param propertyKey is the key of the variable
     * @return the value of the variable
     */
    String getProperty(String propertyKey) {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));

            // return the property value
            return prop.getProperty(propertyKey);

        } catch (IOException ex) {
            logger.error(this.getClass(),ex);
            throw new RuntimeException("Unable to get the cloud automation service url from config.properties");
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    logger.error(this.getClass(),e);
                }
            }
        }

    }

    /**
     * Send a request to the scheduler with the name and the password from the configuration file in order to get the
     * session id
     *
     * @return the session id
     */
    String getSessionId() {
        final String SCHEDULER_LOGIN_URL = getProperty("scheduler.login.endpoint");
        final String SCHEDULER_REQUEST = "username=" + getProperty("login.name") + "&password=" + getProperty("login.password");

        StringBuffer result = new StringBuffer();
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost postRequest = new HttpPost(SCHEDULER_LOGIN_URL);
            StringEntity input = new StringEntity(SCHEDULER_REQUEST, ContentType.APPLICATION_FORM_URLENCODED);
            postRequest.setEntity(input);

            HttpResponse response = httpClient.execute(postRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Get Session Failed: HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            while ((output = br.readLine()) != null) {
                result.append(output);
            }
            httpClient.close();
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
        }
        return result.toString();
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
        logger.error(this.getClass(),ex);
        JSONObject result = new JSONObject();
        result.put("exception", ex.getMessage());
        throw new CloudAutomationException(result);
    }
}
