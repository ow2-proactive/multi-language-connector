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
package org.ow2.proactive.procci.model.cloudautomation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Service {

    @Getter
    private final Action action;
    @Getter
    private final String type;
    @Getter
    private final String infrastructure;
    @Getter
    private final String endpoint;
    @Getter
    private final String model;
    @Getter
    private final String name;
    @Getter
    private final String stateName;
    @Getter
    private final String stateType;
    @Getter
    private final String description;

    public  static class Builder{

        private final Action action;
        private final String type;
        private final String infrastructure;
        private final String endpoint;
        private  String model;
        private  String name;
        private  String stateName;
        private  String stateType;
        private  String description;

        public Builder(String type,String infrastructure,String endpoint, Action action){
            this.type = type;
            this.infrastructure = infrastructure;
            this.endpoint = endpoint;
            this.action = action;
            this.model = "";
            this.name = "";
            this.stateName = "";
            this.stateType = "";
            this.description = "";
        }

        public Builder(String type,String infrastructure,String endpoint, String actionType){
            this.type = type;
            this.infrastructure = infrastructure;
            this.endpoint = endpoint;
            this.action = new Action.Builder(actionType).build();
            this.model = "";
            this.name = "";
            this.stateName = "";
            this.stateType = "";
            this.description = "";
        }

        public Builder model(String model){
            this.model = model;
            return this;
        }

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder stateName(String stateName){
            this.stateName = stateName;
            return this;
        }

        public Builder stateType(String stateType){
            this.stateType = stateType;
            return this;
        }

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Service build(){
            return new Service(action,type,infrastructure,endpoint,model,name,stateName,stateType
                    ,description);
        }

    }


}
