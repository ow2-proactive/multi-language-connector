package org.ow2.proactive.procci.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.ow2.proactive.procci.model.exception.CloudAutomationClientException;
import org.ow2.proactive.procci.model.exception.CloudAutomationServerException;
import org.ow2.proactive.procci.model.exception.ServerException;
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
            logger.error("Unable to get the the session id", ex);
            logError(SCHEDULER_LOGIN_URL, SCHEDULER_REQUEST);
            throw new ServerException();
        }

    }

    /**
     *
     * @param response
     * @param url
     * @param request
     * @return
     */
    public String readHttpResponse(HttpResponse response, String url, String request) {
        int status = response.getStatusLine().getStatusCode();
        String responseOutput;
        try {
            responseOutput = getResponseString(response);
        } catch (IOException ex) {
            logger.error("Unable to read the the http response in RequestUtils::checkStatus", ex);
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
}
