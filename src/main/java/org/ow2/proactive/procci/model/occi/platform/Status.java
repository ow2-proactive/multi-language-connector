package org.ow2.proactive.procci.model.occi.platform;

public enum Status {
    ACTIVE("The component is running"),
    INACTIVE("The component is not running"),
    ERROR("Error : the component is not working correctly");

    private String message;

    Status(String message) {
        this.message = message;
    }
}
