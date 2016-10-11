package org.ow2.proactive.procci.model.exception;

import lombok.AllArgsConstructor;

/**
 * Created by mael on 11/10/16.
 */
@AllArgsConstructor
public class MissingAttributesException extends Exception{
    private String attributeMissing;
    private String objectRepresentation;

    public String getJsonMessage(){
        return "{\"error\" : \""+attributeMissing+" is missing for the "+objectRepresentation+" construction.\"}";
    }
}
