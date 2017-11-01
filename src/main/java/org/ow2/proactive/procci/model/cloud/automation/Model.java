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
package org.ow2.proactive.procci.model.cloud.automation;

import static org.ow2.proactive.procci.model.ModelConstant.ACTION_DESCRIPTION;
import static org.ow2.proactive.procci.model.ModelConstant.ACTION_ICON;
import static org.ow2.proactive.procci.model.ModelConstant.ACTION_NAME;
import static org.ow2.proactive.procci.model.ModelConstant.ACTION_ORIGIN_STATES;
import static org.ow2.proactive.procci.model.ModelConstant.ACTION_TYPE;
import static org.ow2.proactive.procci.model.ModelConstant.GENERIC_INFORMATION;
import static org.ow2.proactive.procci.model.ModelConstant.SERVICE_DESCRIPTION;
import static org.ow2.proactive.procci.model.ModelConstant.SERVICE_MODEL;
import static org.ow2.proactive.procci.model.ModelConstant.SERVICE_NAME;
import static org.ow2.proactive.procci.model.ModelConstant.SERVICE_TYPE;
import static org.ow2.proactive.procci.model.ModelConstant.VARIABLES;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


/**
 * Cloud Automation serviceModel
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@EqualsAndHashCode
public class Model {

    private final String serviceModel;

    private final String serviceType;

    private final String serviceName;

    private final String serviceDescription;

    private final String actionType;

    private final String actionName;

    private final String actionDescription;

    private final String actionOriginStates;

    private final String actionIcon;

    private final Map<String, String> variables;

    /**
     * Create the cloud automation serviceModel from the Cloud Automation Service response
     *
     * @param CloudAutomationJson is the Cloud Automation Service Response
     */
    public Model(JSONObject CloudAutomationJson) {

        JSONObject variables = (JSONObject) CloudAutomationJson.getOrDefault(VARIABLES, new JSONObject());
        JSONObject genericInfo = (JSONObject) CloudAutomationJson.getOrDefault(GENERIC_INFORMATION, new JSONObject());

        this.serviceModel = (String) genericInfo.getOrDefault(SERVICE_MODEL, "");
        this.serviceType = (String) genericInfo.getOrDefault(SERVICE_TYPE, "");
        this.serviceName = (String) genericInfo.getOrDefault(SERVICE_NAME, "");
        this.serviceDescription = (String) genericInfo.getOrDefault(SERVICE_DESCRIPTION, "");

        this.actionType = (String) genericInfo.getOrDefault(ACTION_TYPE, "");
        this.actionName = (String) genericInfo.getOrDefault(ACTION_NAME, "");
        this.actionDescription = (String) genericInfo.getOrDefault(ACTION_DESCRIPTION, "");
        this.actionOriginStates = (String) genericInfo.getOrDefault(ACTION_ORIGIN_STATES, "");
        this.actionIcon = (String) genericInfo.getOrDefault(ACTION_ICON, "");

        this.variables = new HashMap<>();
        for (Object key : variables.keySet()) {
            this.variables.put((String) key, (String) variables.get(key));
        }
    }

    /**
     * Create a json object which contains the cloud automation serviceModel and its values
     *
     * @return a json representation of the class serviceModel
     */
    public JSONObject getJson() {
        JSONObject jsonService = new JSONObject();
        jsonService.put(SERVICE_MODEL, serviceModel);
        jsonService.put(SERVICE_TYPE, serviceType);
        jsonService.put(SERVICE_NAME, serviceName);
        jsonService.put(SERVICE_DESCRIPTION, serviceDescription);

        jsonService.put(ACTION_TYPE, actionType);
        jsonService.put(ACTION_NAME, actionName);
        jsonService.put(ACTION_DESCRIPTION, actionDescription);
        jsonService.put(ACTION_ORIGIN_STATES, actionOriginStates);
        jsonService.put(ACTION_ICON, actionIcon);

        JSONObject jsonVariables = new JSONObject();
        jsonVariables.putAll(variables);

        JSONObject query = new JSONObject();
        query.put(GENERIC_INFORMATION, jsonService);
        query.put(VARIABLES, jsonVariables);

        return query;
    }

    public String getVariableValue(String variableName) {
        return variables.get(variableName);
    }

    public static class Builder {

        private final String serviceModel;

        private final String actionType;

        private String serviceType;

        private String serviceName;

        private String serviceDescription;

        private String actionName;

        private String actionDescription;

        private String actionOriginStates;

        private String actionIcon;

        private Map<String, String> variables;

        public Builder(String model, String actionType) {
            this.serviceModel = model;
            this.serviceType = "";
            this.serviceName = "";
            this.serviceDescription = "";
            this.actionType = actionType;
            this.actionName = "";
            this.actionDescription = "";
            this.actionOriginStates = "";
            this.actionIcon = "";
            this.variables = new HashMap<>();
        }

        public Builder serviceType(String serviceType) {
            this.serviceType = serviceType;
            return this;
        }

        public Builder serviceName(String serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        public Builder serviceDescription(String serviceDescription) {
            this.serviceDescription = serviceDescription;
            return this;
        }

        public Builder actionName(String actionName) {
            this.actionName = actionName;
            return this;
        }

        public Builder actionDescription(String actionDescription) {
            this.actionDescription = actionDescription;
            return this;
        }

        public Builder actionOriginStates(String actionOriginStates) {
            this.actionOriginStates = actionOriginStates;
            return this;
        }

        public Builder actionIcon(String actionIcon) {
            this.actionIcon = actionIcon;
            return this;
        }

        public Builder addVariable(String variableKey, String variableValue) {
            this.variables.put(variableKey, variableValue);
            return this;
        }

        public Builder addVariable(String variableKey, Object variableValue) {
            if (variableValue != null) {
                this.variables.put(variableKey, variableValue.toString());
            }
            return this;
        }

        public Model build() {
            return new Model(serviceModel,
                             serviceType,
                             serviceName,
                             serviceDescription,
                             actionType,
                             actionName,
                             actionDescription,
                             actionOriginStates,
                             actionIcon,
                             variables);
        }
    }

}
