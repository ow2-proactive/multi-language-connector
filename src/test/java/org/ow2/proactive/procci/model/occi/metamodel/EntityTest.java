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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.ow2.proactive.procci.model.occi.infrastructure.mixin.Contextualization;
import org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelKinds;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.EntityRendering;


/**
 * Created by the Activeeon team  on 2/25/16.
 */
public class EntityTest {

    @Test
    public void constructorTest() {
        Kind k = MetamodelKinds.ENTITY;
        List<Mixin> mixins = new ArrayList<>();
        Contextualization contextualization = new Contextualization("contextualizationTest",
                                                                    new ArrayList<>(),
                                                                    new ArrayList<>(),
                                                                    "userDataTest");
        mixins.add(contextualization);
        EntityImplemented implemented = new EntityImplemented("url", k, "titleTest2", mixins);

        assertThat(implemented.getId().toString()).startsWith("url");
        assertThat(implemented.getKind()).isEqualTo(k);
        assertThat(implemented.getTitle().get()).isEqualTo("titleTest2");
        assertThat(implemented.getMixins()).containsExactly(contextualization);
    }

    private class EntityImplemented extends Entity {

        EntityImplemented(String url, Kind k, String title, List<Mixin> mixins) {
            super(Optional.of(url), k, Optional.of(title), mixins);
        }

        @Override
        public EntityRendering getRendering() {
            return null;
        }
    }
}
