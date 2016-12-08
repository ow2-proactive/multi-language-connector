package org.ow2.proactive.procci.model.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by the Activeeon Team on 29/09/16.
 */
@Getter
@AllArgsConstructor
public class SyntaxException extends ClientException {
    private String stringException;
    private String expected;

    @Override
    public String getJsonError() {
        return "{\"error\" : \" Invalid content type received '" + stringException + "', expected " + expected + " \"}";
    }
}
