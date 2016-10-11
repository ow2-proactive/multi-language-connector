package org.ow2.proactive.procci.model.exception;

/**
 * Created by mael on 11/10/16.
 */
public abstract class ClientException extends Exception{

    public abstract String getJsonError();
}
