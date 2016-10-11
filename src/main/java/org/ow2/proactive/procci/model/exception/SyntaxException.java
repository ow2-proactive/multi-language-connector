package org.ow2.proactive.procci.model.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by mael on 29/09/16.
 */
@Getter
@AllArgsConstructor
public class SyntaxException extends ClientException {
    private String stringException;

    @Override
    public String getJsonError() {
        return "{\"error\" : \""+stringException+" was not found\"}";
    }
}
