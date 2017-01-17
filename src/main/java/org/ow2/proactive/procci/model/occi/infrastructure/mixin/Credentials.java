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
import java.util.Set;

import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureAttributes;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureIdentifiers;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.occi.metamodel.Attribute;
import org.ow2.proactive.procci.model.occi.metamodel.Entity;
import org.ow2.proactive.procci.model.occi.metamodel.Kind;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;

import lombok.Getter;


/**
 * Created by the Activeeon Team on 2/25/16.
 */

/**
 * Protect the access of a compute thanks to an ssh public key
 */
@Getter
public class Credentials extends Mixin {

    private String publickey;

    /**
     * Constructor with all the parameters
     *
     * @param publickey The contents of the public key file to be injected into the Compute Resource
     * @param entities  is the set of resource instances
     */
    public Credentials(String publickey, List<Entity> entities) {
        super(InfrastructureIdentifiers.CREDENTIALS_SCHEME,
              InfrastructureIdentifiers.CREDENTIALS,
              InfrastructureIdentifiers.CREDENTIALS,
              createAttributesSet(),
              new ArrayList<>(),
              new ArrayList<>(),
              setApplies(),
              entities);
        this.publickey = publickey;
    }

    private static List<Kind> setApplies() {
        List<Kind> applies = new ArrayList<>();
        applies.add(InfrastructureKinds.COMPUTE);
        return applies;
    }

    private static Set<Attribute> createAttributesSet() {
        Set<Attribute> attributes = new HashSet<>();
        attributes.add(InfrastructureAttributes.SSH_PUBLICKEY);
        return attributes;
    }

}
