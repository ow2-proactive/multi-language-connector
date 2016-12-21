package org.ow2.proactive.procci.model.exception;

/**
 * Created by the Activeeon Team on 11/10/16.
 */
public abstract class ClientException extends RuntimeException {

    public abstract String getJsonError();
}
