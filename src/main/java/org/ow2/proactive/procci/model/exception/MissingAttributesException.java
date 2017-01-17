package org.ow2.proactive.procci.model.exception;

import lombok.AllArgsConstructor;


/**
 * Created by the Activeeon Team on 11/10/16.
 */
@AllArgsConstructor
public class MissingAttributesException extends ClientException {
    private String attributeMissing;

    private String objectRepresentation;

    @Override
    public String getJsonError() {
        return "{\"error\" : \"" + attributeMissing + " is missing for the " + objectRepresentation +
               " construction.\"}";
    }
}
