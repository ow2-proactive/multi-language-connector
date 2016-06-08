package org.ow2.proactive.procci.request;

import org.json.simple.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

/**
 * Created by mael on 02/06/16.
 */

public class CloudAutomationRequest {

    private static final String PCA_SERVICE = "http://localhost:4444";

    public String sendRequest(JSONObject content){
        String result;
        try{
            URL obj = new URL(PCA_SERVICE);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add request header
            con.setRequestMethod("POST");

            con.setRequestProperty("Content-Type", "application.json");
            con.setRequestProperty("Accept", "application/json");

            //String urlParameters = "";

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            //wr.writeBytes(urlParameters);
            content.put("url",getCloudAutomationURL());
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
            result = "Exception in Compute : "+e.getMessage();
        }

        return result;
    }

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

}
