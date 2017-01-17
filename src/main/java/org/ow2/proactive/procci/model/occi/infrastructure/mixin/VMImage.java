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
 * Created by the Activeeon Team on 11/10/16.
 */
@Getter
public class VMImage extends Mixin {

    private String image;

    public VMImage(String title, List<Mixin> depends, List<Entity> entities, String image) {
        super(InfrastructureIdentifiers.OCCIWARE_SCHEME,
              InfrastructureIdentifiers.VM_IMAGE,
              title,
              createAttributesSet(),
              new ArrayList<>(),
              depends,
              initApplies(),
              entities);
        this.image = image;
    }

    private static List<Kind> initApplies() {
        List<Kind> applies = new ArrayList<>();
        applies.add(InfrastructureKinds.COMPUTE);
        return applies;
    }

    private static Set<Attribute> createAttributesSet() {
        Set<Attribute> attributes = new HashSet<>();
        attributes.add(InfrastructureAttributes.COMPUTE_IMAGE);
        return attributes;
    }

    @Override
    public Model.Builder toCloudAutomationModel(Model.Builder cloudAutomation) {
        cloudAutomation.addVariable(InfrastructureAttributes.COMPUTE_IMAGE_NAME, image);
        return cloudAutomation;
    }

    public static class Builder extends MixinBuilder {

        private String VMImage;

        public Builder() {
            super(InfrastructureIdentifiers.OCCIWARE_SCHEME, InfrastructureIdentifiers.VM_IMAGE);
        }

        @Override
        public MixinBuilder attributes(Map attributes) throws ClientException {
            super.attributes(attributes);
            this.VMImage = readAttributeAsString(attributes,
                                                 InfrastructureAttributes.COMPUTE_IMAGE_NAME).orElseThrow(() -> new MissingAttributesException(InfrastructureAttributes.COMPUTE_IMAGE_NAME,
                                                                                                                                               InfrastructureAttributes.COMPUTE_IMAGE.getName()));
            return this;
        }

        @Override
        public VMImage build() {
            return new VMImage(this.getTitle(), this.getDepends(), this.getEntities(), this.VMImage);
        }

    }
}
