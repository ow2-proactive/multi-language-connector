package org.ow2.proactive.procci.model.exception;

import org.json.simple.JSONObject;

public class CloudAutomationClientException extends ClientException{

    private JSONObject jsonError;

    public CloudAutomationClientException(String exception) {
        jsonError = new JSONObject();
        jsonError.put("error", exception);
    }

    @Override
    public String getJsonError() {
        return jsonError.toJSONString();
    }
}
