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

import static com.google.common.truth.Truth.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.MissingAttributesException;
import org.ow2.proactive.procci.model.occi.metamodel.Attribute;
import org.ow2.proactive.procci.model.occi.metamodel.Type;


/**
 * Created by the Activeeon team on 2/25/16.
 */
public class ContextualizationTest {

    @Test
    public void constructorTest() {
        Contextualization contextualization = new Contextualization("contextualizationTest",
                                                                    new ArrayList<>(),
                                                                    new ArrayList<>(),
                                                                    "userdataTest");
        assertThat(contextualization.getUserdata()).isEqualTo("userdataTest");
        assertThat(contextualization.getAttributes()).contains(new Attribute.Builder("occi.compute.userdata").type(Type.OBJECT)
                                                                                                             .mutable(false)
                                                                                                             .required(false)
                                                                                                             .build());
    }

    @Test
    public void buildTest() throws ClientException {
        Exception exception = null;
        try {
            new Contextualization.Builder().attributes(new HashMap()).build();
        } catch (MissingAttributesException e) {
            exception = e;
        }

        assertThat(exception).isInstanceOf(MissingAttributesException.class);

        Map attributes = new HashMap();
        attributes.put("occi.compute.userdata", "userdataTest");
        attributes.put("occi.category.title", "titleTest");

        Contextualization contextualization = new Contextualization.Builder().attributes(attributes).build();

        assertThat(contextualization.getUserdata()).matches("userdataTest");
        assertThat(contextualization.getTerm()).matches("user_data");
        assertThat(contextualization.getScheme()).matches("http://schemas.ogf.org/occi/infrastructure/compute#");
        assertThat(contextualization.getTitle()).matches("titleTest");

    }

}
