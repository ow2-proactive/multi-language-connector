package org.ow2.proactive.procci.model.occi.platform;

import org.ow2.proactive.procci.model.exception.SyntaxException;


public enum Status {
    ACTIVE("The component is running"),
    INACTIVE("The component is not running"),
    ERROR("Error : the component is not working correctly");

    private String message;

    Status(String message) {
        this.message = message;
    }

    public static Status getStatusFromString(String value) throws SyntaxException {
        if (ACTIVE.name().equalsIgnoreCase(value)) {
            return ACTIVE;
        } else if (INACTIVE.name().equalsIgnoreCase(value)) {
            return INACTIVE;
        } else if (ERROR.name().equalsIgnoreCase(value)) {
            return ERROR;
        } else {
            throw new SyntaxException(value, ACTIVE.name() + ", " + INACTIVE.name() + " or " + ERROR.name());
        }
    }
}
