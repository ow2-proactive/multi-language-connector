package org.ow2.proactive.procci.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.ow2.proactive.procci.model.exception.CloudAutomationException;
import org.ow2.proactive.procci.model.exception.ServerException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by the Activeeon Team on 06/10/16.
 */
@Service
public class RequestUtils {

    private final Logger logger = LoggerFactory.getLogger(RequestUtils.class);

    /**
     * Read an http respond and convert it into a string
     *
     * @param response is the http response
     * @return a string containing the information from response
     * @throws IOException occur if problem occur with the buffer
     */
    public String readHttpResponse(
            HttpResponse response) throws IOException, CloudAutomationException {
        StringBuffer serverOutput = new StringBuffer();
        if (response.getStatusLine().getStatusCode() >= 300) {

            throw new CloudAutomationException("Send Request Failed: HTTP error code : "
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
     * Get the property from the configuration file config.properties
     *
     * @param propertyKey is the key of the variable
     * @return the value of the variable
     */
    public String getProperty(String propertyKey) {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));

            // return the property value
            return prop.getProperty(propertyKey);

        } catch (IOException ex) {
            logger.error("Unable to get the cloud automation service url from config.properties", ex);
            throw new ServerException();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    logger.error("Unable to get the cloud automation service url from config.properties", e);
                    throw new ServerException();
                }
            }
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
        final String SCHEDULER_REQUEST = "username=" + getProperty("login.name") + "&password=" + getProperty(
                "login.password");

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
            logger.error("Unable to get the the session id", ex);
        }
        return result.toString();
    }
}
