package org.ow2.proactive.procci.request;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.springframework.http.HttpHeaders.USER_AGENT;

/**
 * Created by mael on 02/06/16.
 */

public class CloudAutomationRequest {

    private static final String PCA_SERVICE = "http://localhost:4444";

    /*public String sendRequest(JSONObject content){
        String result;
        try{
            URL obj = new URL(PCA_SERVICE);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add request header
            con.setRequestMethod("POST");

            con.setRequestProperty("Content-Type", "application.json");
            //con.setRequestProperty("Accept", "application/json");


            con.setRequestProperty("sessionid","467ec32b15538a75ab1651a3a81ecdf634e39ec467ec32b15538a75ab18000");

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(content.toJSONString());
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + PCA_SERVICE);
            //System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            result = response.toString();
        }catch (Exception e){
            return "Exception in Compute : "+e.toString();
        }

        return result;
    }
*/
    private String getCloudAutomationURL(){
        Properties prop = new Properties();
        InputStream input = null;

        try {

            prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));

            // return the property value
            return prop.getProperty("cloud-automation-service.url");

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

    public String sendRequest(JSONObject content) throws HTTPException{
        //this shoud be given trough a mixin this is a temporary solution
        String sessionid = (String) ((JSONObject) content.get("variables")).get("sessionid");
        String url = getCloudAutomationURL() + "/serviceInstances";
        StringBuffer result = new StringBuffer();
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost postRequest = new HttpPost(url);
            postRequest.addHeader("sessionid",sessionid);
            StringEntity input = new StringEntity(content.toJSONString());
            input.setContentType("application/json");
            postRequest.setEntity(input);

            HttpResponse response = httpClient.execute(postRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
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

}
