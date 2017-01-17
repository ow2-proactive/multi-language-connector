package org.ow2.proactive.procci.model.exception;

import org.json.simple.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


/**
 * Created by the Activeeon Team on 21/06/16.
 */

/**
 * The Exception occur when an http service fail with an other Cloud Automation microservice
 */
@Getter
@AllArgsConstructor
@ToString
public class CloudAutomationServerException extends ServerException {
    private JSONObject jsonError;

    public CloudAutomationServerException(String exception, String url, String request) {
        jsonError = new JSONObject();
        jsonError.put("error", exception);
        jsonError.put("url", url);
        jsonError.put("request", request);
    }

    @Override
    public String getJsonError() {
        return jsonError.toJSONString();
    }
}
