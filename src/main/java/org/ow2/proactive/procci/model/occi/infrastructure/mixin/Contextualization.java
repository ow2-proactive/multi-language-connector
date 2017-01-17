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
package org.ow2.proactive.procci.model.occi.infrastructure.mixin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.MissingAttributesException;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureAttributes;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureIdentifiers;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.occi.metamodel.Attribute;
import org.ow2.proactive.procci.model.occi.metamodel.Entity;
import org.ow2.proactive.procci.model.occi.metamodel.Kind;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import org.ow2.proactive.procci.model.occi.metamodel.MixinBuilder;

import lombok.Getter;


/**
 * Created by the Activeeon Team on 2/25/16.
 */

/**
 * Indicated the data that will be supplied to the compute
 */
@Getter
public class Contextualization extends Mixin {

    private String userdata;

    /**
     * Create a Contextualization mixin
     *
     * @param title    is the mixin name
     * @param depends  is a list of mixin related to this instance
     * @param entities entities is the set of resource instances
     * @param userdata userdata Contextualization data(e.g., script executable) that the client supplies once and only once. It cannot be updated
     */
    public Contextualization(String title, List<Mixin> depends, List<Entity> entities, String userdata) {
        super(InfrastructureIdentifiers.COMPUTE_SCHEME,
              InfrastructureIdentifiers.CONTEXTUALIZATION,
              title,
              createAttributeSet(),
              new ArrayList<>(),
              depends,
              setApplies(),
              entities);
        this.userdata = userdata;
    }

    private static List<Kind> setApplies() {
        List<Kind> applies = new ArrayList<>();
        applies.add(InfrastructureKinds.COMPUTE);
        return applies;
    }

    private static Set<Attribute> createAttributeSet() {
        Set<Attribute> attributes = new HashSet<>();
        attributes.add(InfrastructureAttributes.USERDATA);
        return attributes;
    }

    public String getUserdata() {
        return userdata;
    }

    @Override
    public Model.Builder toCloudAutomationModel(Model.Builder cloudAutomation) {
        cloudAutomation.addVariable(InfrastructureAttributes.USERDATA_NAME, this.userdata);
        return cloudAutomation;
    }

    public static class Builder extends MixinBuilder {

        private String userdata;

        public Builder() {
            super(InfrastructureIdentifiers.COMPUTE_SCHEME, InfrastructureIdentifiers.CONTEXTUALIZATION);
        }

        @Override
        public Contextualization.Builder attributes(Map attributesMap) throws ClientException {
            super.attributes(attributesMap);
            this.userdata = readAttributeAsString(attributesMap,
                                                  InfrastructureAttributes.USERDATA_NAME).orElseThrow(() -> new MissingAttributesException(InfrastructureAttributes.USERDATA_NAME,
                                                                                                                                           InfrastructureAttributes.USERDATA.getName()));
            return this;
        }

        @Override
        public Contextualization build() {
            return new Contextualization(this.getTitle(), this.getDepends(), this.getEntities(), this.userdata);
        }

    }
}
