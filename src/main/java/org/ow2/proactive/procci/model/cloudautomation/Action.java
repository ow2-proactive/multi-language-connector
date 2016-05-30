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
public class Action {
    @Getter
    private String type;
    @Getter
    private String name;
    @Getter
    private String description;
    @Getter
    private String originStates;
    @Getter
    private String icon;

    public static class Builder{

        private final String type;
        private String name;
        private String originStates;
        private String description;
        private String icon;

        public Builder(String type){
            this.type = type;
            this.name = "";
            this.originStates = "";
            this.description = "";
            this.icon = "";
        }

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder originStates(String originStates){
            this.originStates = originStates;
            return this;
        }

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder icon(String icon){
            this.icon = icon;
            return this;
        }

        public Action build(){
            return new Action(type,name,originStates,description,icon);
        }


    }

}
