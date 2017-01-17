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
package org.ow2.proactive.procci.model.occi.metamodel;

import static com.google.common.truth.Truth.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.ow2.proactive.procci.model.occi.infrastructure.action.StartCompute;
import org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelKinds;


/**
 * Created by the Activeeon team  on 2/24/16.
 */
public class KindTest {

    @Test
    public void minimalConstructorTest() {
        String uri = "http://schemas.ogf.org/occi/infrastructure#compute";
        Kind k = new Kind.Builder(uri, "compute").build();
        assertThat(k.getTerm()).isEqualTo("compute");
        assertThat(k.getScheme().toString()).isEqualTo(uri);
    }

    @Test
    public void maximalConstructorTest() {
        String uri = "http://schemas.ogf.org/occi/infrastructure#compute";

        Attribute a = new Attribute.Builder("test").type(Type.LIST).mutable(false).required(false).build();

        Set<Attribute> attributes = new HashSet<>();
        attributes.add(a);

        Kind parent = MetamodelKinds.RESOURCE;
        Kind k = new Kind.Builder(uri, "compute").addAttribute(attributes)
                                                 .addAction(StartCompute.getInstance())
                                                 .addParent(parent)
                                                 .build();

        assertThat(k.getTerm()).isEqualTo("compute");
        assertThat(k.getScheme().toString()).isEqualTo(uri);

        assertThat(k.getActions()).containsKey("Action.start");
        assertThat(k.getActions().get("Action.start")).isEqualTo(StartCompute.getInstance());
        assertThat(k.getEntities()).isEmpty();
        assertThat(k.getParent()).isEqualTo(parent);
        assertThat(k.getAttributes()).containsAnyOf(a,
                                                    new Attribute.Builder("occi.kind.actions").type(Type.OBJECT)
                                                                                              .mutable(true)
                                                                                              .required(true)
                                                                                              .build());
    }
}
