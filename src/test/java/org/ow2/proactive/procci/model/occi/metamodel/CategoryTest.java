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

import org.junit.Test;


/**
 * Created by the Activeeon team  on 2/24/16.
 */

public class CategoryTest {

    @Test
    public void constructorTest() {

        String uri = "http://schemas.ogf.org/occi/infrastructure#compute";
        Category c = new Category(uri, "compute", "compute", new HashSet<Attribute>());

        assertThat(c.getTerm()).isEqualTo("compute");
        assertThat(c.getScheme().toString()).isEqualTo(uri);
    }

    @Test
    public void getAttributesTest() {
        String uri = "http://schemas.ogf.org/occi/infrastructure#compute";
        Category c = new Category(uri, "compute", "test", new HashSet<Attribute>());

        Attribute a = new Attribute.Builder("occi.category.term").type(Type.OBJECT)
                                                                 .mutable(false)
                                                                 .required(false)
                                                                 .build();
        Attribute a1 = new Attribute.Builder("term2").type(Type.OBJECT).mutable(false).required(false).build();
        assertThat(c.getAttributes()).containsAnyOf(a, a1);
    }

}
