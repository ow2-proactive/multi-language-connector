/*
 * ProActive Parallel Suite(TM):
 * The Open Source library for parallel and distributed
 * Workflows & Scheduling, Orchestration, Cloud Automation
 * and Big Data Analysis on Enterprise Grids & Clouds.
 *
 * Copyright (c) 2007 - 2017 ActiveEon
 * Contact: contact@activeeon.com
 *
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation: version 3 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 */
package org.ow2.proactive.procci.model;

/**
 * Created by the Activeeon Team on 20/07/16.
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

    public static final String PENDING_STATE = "DEPLOYING";

    public static final String TERMINATED_STATE = "TERMINATED";

    public static final String ERROR_STATE = "ERROR";

}
