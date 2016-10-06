package org.ow2.proactive.procci.request;

import java.io.IOException;

import lombok.AccessLevel;
import lombok.Getter;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by mael on 06/10/16.
 */

/**
 * Send crud request on cloud-automation-service/variables
 */
public class CloudAutomationVariables {

    private static final Logger logger = LogManager.getRootLogger();

    @Getter(AccessLevel.PACKAGE)
    private static String variablesUrl = RequestUtils.getInstance().getProperty("cloud-automation-service.variables.endpoint");

    public static String get(String key) throws CloudAutomationException{

        try {
            Response response = Request.Get(getResourceUrl(key))
                    .version(HttpVersion.HTTP_1_1)
                    .execute();

            checkStatus(response.returnResponse());

            return response.returnContent().asString();

        }catch (IOException ex){
            logger.error(CloudAutomationVariables.class, ex);
            throw new RuntimeException(
                    "Unable to get on "+getResourceUrl(key)+ ", exception : "+ex);
        }
    }

    public static void post(String key,String value) throws CloudAutomationException{

        try{
            HttpResponse response = Request.Post(getQueryUrl(key))
                    .useExpectContinue()
                    .version(HttpVersion.HTTP_1_1)
                    .bodyString(value, ContentType.DEFAULT_TEXT)
                    .execute()
                    .returnResponse();

            checkStatus(response);

        }catch (IOException ex){
            logger.error(CloudAutomationVariables.class, ex);
            throw new RuntimeException(
                    "Unable to post on "+getQueryUrl(key)+", exception : "+ex);
        }

    }

    public static void update(String key,String value) throws CloudAutomationException {

        try {
            HttpResponse response = Request.Put(getResourceUrl(key))
                    .useExpectContinue()
                    .version(HttpVersion.HTTP_1_1)
                    .bodyString(value, ContentType.DEFAULT_TEXT)
                    .execute()
                    .returnResponse();

            checkStatus(response);

        }catch (IOException ex){
            logger.error(CloudAutomationVariables.class, ex);
            throw new RuntimeException(
                    "Unable to put on "+getResourceUrl(key)+" ,exception : "+ex);
        }
    }

    public static void delete(String key) throws CloudAutomationException {

        try {
            HttpResponse response = Request.Delete(getResourceUrl(key))
                    .version(HttpVersion.HTTP_1_1)
                    .execute()
                    .returnResponse();

            checkStatus(response);

        }catch (IOException ex){
            logger.error(CloudAutomationVariables.class, ex);
            throw new RuntimeException(
                    "Unable to delete on "+getQueryUrl(key)+", exception : "+ex);
        }
    }

    private static String getResourceUrl(String key){
        return variablesUrl+"/"+key;
    }

    private static String getQueryUrl(String key){
        return variablesUrl+"?key="+key;
    }

    private static void checkStatus(HttpResponse response) throws CloudAutomationException {
        if(response.getStatusLine().getStatusCode()>=300) {
            throw new CloudAutomationException(response.getStatusLine().getReasonPhrase());
        }
    }

}
