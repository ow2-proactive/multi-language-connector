package org.ow2.proactive.procci.model.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class UnknownAttributeException extends ClientException {

    private String unknownAttribute;

    private String objectRepresentation;

    @Override
    public String getJsonError() {
        return "{\"error\" : \" unknown attribute " + unknownAttribute + "have been found for the " +
               objectRepresentation + " construction .\"}";
    }
}
