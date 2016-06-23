package org.ow2.proactive.procci.request;

import org.apache.http.HttpResponse;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.Properties;

/**
 * Created by mael on 02/06/16.
 */

public class CloudAutomationRequest {


    /**
     * Send a request to pca service with a header containing the session id and sending content
     * @param content is which is send to the cloud automation service
     * @return the information about gathered from cloud automation service
     * @throws HTTPException is thrown if something failed during the connection
     */
    public String sendRequest(JSONObject content) throws HTTPException{

        final String PCA_SERVICE_SESSIONID = "sessionid";
        final String url = getProperty("server.endpoint") + "/cloud-automation-service/serviceInstances";
        StringBuffer result = new StringBuffer();
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost postRequest = new HttpPost(url);
            postRequest.addHeader(PCA_SERVICE_SESSIONID,getSessionId());
            StringEntity input = new StringEntity(content.toJSONString());
            input.setContentType("application/json");
            postRequest.setEntity(input);

            HttpResponse response = httpClient.execute(postRequest);

            if (response.getStatusLine().getStatusCode() != 200) {

                throw new RuntimeException("Send Request Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                result.append(output);
            }
            httpClient.close();
        } catch (Exception ex) {
            throw new HTTPException(ex.getMessage());
        }

        return result.toString();
    }

    /**
     * Get the property from the configuration file config.properties
     * @param propertyKey is the key of the variable
     * @return the value of the variable
     */
    String getProperty(String propertyKey){
        Properties prop = new Properties();
        InputStream input = null;

        try {

            prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));

            // return the property value
            return prop.getProperty(propertyKey);

        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Unable to get the cloud automation service url from config.properties");
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     *  Send a request to the scheduler with the name and the password from the configuration file in order to get the
     *  session id
     * @return the session id
     */
     String getSessionId(){
        final String SCHEDULER_LOGIN_URL = getProperty("login.endpoint");
        final String SCHEDULER_REQUEST = "username="+ getProperty("login.name")+"&password="+getProperty("login.password");

        StringBuffer result = new StringBuffer();
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost postRequest = new HttpPost(SCHEDULER_LOGIN_URL);
            StringEntity input = new StringEntity(SCHEDULER_REQUEST, ContentType.APPLICATION_FORM_URLENCODED);
            postRequest.setEntity(input);

            HttpResponse response = httpClient.execute(postRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Get Session Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                result.append(output);
            }
            httpClient.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result.toString();
    }
}
