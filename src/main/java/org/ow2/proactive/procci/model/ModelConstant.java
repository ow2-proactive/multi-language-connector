package org.ow2.proactive.procci.model;

/**
 * Created by mael on 20/07/16.
 */
public class ModelConstant {

    //cloud automation generic information
    public static final String GENERIC_INFORMATION = "genericInfo";
    public static final String VARIABLES = "variables";

    public static final String SERVICE_MODEL = "pca.service.model";
    public static final String SERVICE_TYPE = "pca.service.type";
    public static final String SERVICE_NAME = "pca.service.name";
    public static final String SERVICE_DESCRIPTION = "pca.service.description";
    public static final String ACTION_TYPE = "pca.action.type";
    public static final String ACTION_NAME = "pca.action.name";
    public static final String ACTION_DESCRIPTION = "pca.action.description";
    public static final String ACTION_ORIGIN_STATES = "pca.action.origin.states";
    public static final String ACTION_ICON = "pca.action.icon";

    //cloud automation variables
    public static final String INSTANCE_ID = "pca.instance.id";
    public static final String INSTANCE_STATUS = "status";
    public static final String INSTANCE_ENDPOINT = "endpoint";
    public static final String INSTANCE_INFRASTRUCTURE_ID = "id";

    //cloud automation state status
    public static final String RUNNING_STATE = "RUNNING";
    public static final String STOPPED_STATE = "STOPPED";
    public static final String PENDING_STATE = "Deploying";
    public static final String TERMINATED_STATE = "TERMINATED";
    public static final String ERROR_STATE = "Error";

    // infrastructure variables
    public static final String TITLE = "occi.infrastructure.title";
    public static final String SUMMARY = "occi.infrastructure.summary";
    public static final String HOSTNAME = "occi.infrastructure.hostname";
    public static final String ARCHITECTURE = "occi.infrastructure.architecture";
    public static final String CORES = "occi.infrastructure.cores";
    public static final String MEMORY = "occi.infrastructure.memory";
    public static final String SHARE = "occi.infrastructure.share";
    public static final String STATE = "occi.infrastructure.state";


}
