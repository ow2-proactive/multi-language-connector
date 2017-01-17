package org.ow2.proactive.procci.model.exception;

import org.json.simple.JSONObject;


public class ServerException extends RuntimeException {

    private JSONObject jsonError;

    public ServerException() {
        jsonError = new JSONObject();
        jsonError.put("error", "500 Internal Server Error");
    }

    public String getJsonError() {
        return jsonError.toJSONString();
    }
}
