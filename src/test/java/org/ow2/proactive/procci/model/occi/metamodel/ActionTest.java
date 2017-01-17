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


/**
 * Created by the Activeeon team  on 2/25/16.
 */
public class ActionTest {

    @Test
    public void constructor() {
        String uri = "http://schemas.ogf.org/occi/metamodel#actiontest";
        Set<Attribute> attributes = new HashSet<>();
        Attribute a = new Attribute.Builder("none").type(Type.HASH).mutable(false).required(false).build();
        attributes.add(a);
        ActionImplemented actionImplemented = new ActionImplemented(uri, "actiontest", "actiontest", attributes);

        assertThat(actionImplemented.getScheme().toString()).isEqualTo(uri);
        assertThat(actionImplemented.getTerm()).isEqualTo("actiontest");
        assertThat(actionImplemented.getAttributes()).contains(a);

        //integration test
        assertThat(actionImplemented.getAttributes()).containsAnyOf(new Attribute.Builder("occi.category.scheme").type(Type.HASH)
                                                                                                                 .mutable(false)
                                                                                                                 .required(false)
                                                                                                                 .build(),
                                                                    new Attribute.Builder("occi.category.term").type(Type.HASH)
                                                                                                               .mutable(false)
                                                                                                               .required(false)
                                                                                                               .build(),
                                                                    new Attribute.Builder("occi.category.title").type(Type.HASH)
                                                                                                                .mutable(false)
                                                                                                                .required(false)
                                                                                                                .build());
    }

    private class ActionImplemented extends Action {

        ActionImplemented(String scheme, String term, String title, Set<Attribute> attributes) {
            super(scheme, term, title, attributes);
        }
    }
}
