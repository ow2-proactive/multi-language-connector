package org.ow2.proactive.procci.model.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.json.simple.JSONObject;

/**
 * Created by the Activeeon Team on 21/06/16.
 */

/**
 * The Exception occur when an http service fail with an other Cloud Automation microservice
 */
@Getter
@AllArgsConstructor
@ToString
public class CloudAutomationException extends ClientException {
    private JSONObject jsonError;

    public CloudAutomationException(String exception) {
        jsonError = new JSONObject();
        jsonError.put("error", exception);
    }

    @Override
    public String getJsonError() {
        return jsonError.toJSONString();
    }
}
