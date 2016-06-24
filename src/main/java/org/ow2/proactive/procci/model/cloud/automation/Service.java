/*
 *  *
 * ProActive Parallel Suite(TM): The Java(TM) library for
 *    Parallel, Distributed, Multi-Core Computing for
 *    Enterprise Grids & Clouds
 *
 * Copyright (C) 1997-2015 INRIA/University of
 *                 Nice-Sophia Antipolis/ActiveEon
 * Contact: proactive@ow2.org or contact@activeeon.com
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; version 3 of
 * the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 *
 *  Initial developer(s):               The ProActive Team
 *                        http://proactive.inria.fr/team_members.htm
 *  Contributor(s):
 *
 *  * $$ACTIVEEON_INITIAL_DEV$$
 */
package org.ow2.proactive.procci.model.cloud.automation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.json.simple.JSONObject;
import org.ow2.proactive.procci.model.occi.infrastructure.StorageLink;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Service {

    private final Action action;
    private final String type;
    private final String endpoint;
    private final String model;
    private final String name;
    private final String stateName;
    private final String stateType;
    private final String description;
    private final JSONObject jsonService;
    private final JSONObject jsonVariables;

    /**
     * Create a json object which contains the service data according to the cloud automation model
     * @return
     */
    public JSONObject getCloudAutomationModel(){
        JSONObject query = new JSONObject();
        query.put("service",jsonService);
        query.put("action",action.getJsonAction());
        query.put("variables",jsonVariables);
        return query;
    }

    //PCA service should be improved in order to be able to receive the upper request
    /**
     * create a valid request for the cloud automation service
      * @return return a valid json which contains the current instance data
     */
    public JSONObject getCloudAutomationServiceRequest(){
        JSONObject query = new JSONObject();
        query.put("service_model",model);
        query.put("service_name","");
        JSONObject json = (JSONObject) jsonVariables.clone();
        json.put("infrastructure_name","");
        query.put("variables",json);
        return query;
    }

    public static class Builder{

        private final Action action;
        private final String model;
        private String type;
        private String name;
        private String description;
        private String endpoint;
        private String stateName;
        private String stateType;
        private JSONObject jsonService;
        private JSONObject jsonVariables;

        public Builder(String model, Action action){
            this.type = "";
            this.endpoint = "";
            this.action = action;
            this.model = model;
            this.name = "";
            this.stateName = "";
            this.stateType = "";
            this.description = "";
            this.jsonService = new JSONObject();
            this.jsonService.put("model",model);
            this.jsonVariables = new JSONObject();
        }

        public Builder(String model, String actionType){
            this.type = "";
            this.endpoint = "";
            this.action = new Action.Builder(actionType).build();
            this.model = model;
            this.name = "";
            this.stateName = "";
            this.stateType = "";
            this.description = "";
            this.jsonService = new JSONObject();
            this.jsonService.put("model",model);
            this.jsonVariables = new JSONObject();
        }

        public Builder name(String name){
            this.name = name;
            jsonService.put("name",name);
            return this;
        }

        public Builder type(String type){
            this.type = type;
            jsonService.putIfAbsent("type",type);
            return this;
        }

        public Builder endpoint(String endpoint){
            this.endpoint = endpoint;
            jsonService.putIfAbsent("endpoint",endpoint);
            return this;
        }

        public Builder stateName(String stateName){
            this.stateName = stateName;
            jsonService.putIfAbsent("state_name",stateName);
            return this;
        }

        public Builder stateType(String stateType){
            this.stateType = stateType;
            jsonService.putIfAbsent("state_type",stateType);
            return this;
        }

        public Builder description(String description){
            this.description = description;
            jsonService.putIfAbsent("description",description);
            return this;
        }

        public Builder addVariable(String variableKey, String variableValue){
            this.jsonVariables.put(variableKey,variableValue);
            return this;
        }

        public Service build(){
            return new Service(action,type,endpoint,model,name,stateName,stateType
                    ,description,jsonService,jsonVariables);
        }
    }


}
