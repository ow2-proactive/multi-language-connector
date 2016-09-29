package org.ow2.proactive.procci.model.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by mael on 29/09/16.
 */
@Getter
@AllArgsConstructor
public class SyntaxException extends Exception {
    private String stringException;

    public String getUserException() {
        return "Exception : Unable to parse correctly " + stringException;
    }
}
