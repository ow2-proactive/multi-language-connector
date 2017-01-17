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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.CloudAutomationServerException;
import org.ow2.proactive.procci.model.exception.SyntaxException;
import org.ow2.proactive.procci.model.occi.infrastructure.Compute;
import org.ow2.proactive.procci.model.occi.infrastructure.ComputeBuilder;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelKinds;
import org.ow2.proactive.procci.service.CloudAutomationVariablesClient;
import org.ow2.proactive.procci.service.occi.MixinService;


/**
 * Created by the Activeeon team  on 2/24/16.
 */
public class LinkTest {

    @Mock
    private MixinService mixinService;

    @Mock
    private CloudAutomationVariablesClient cloudAutomationVariablesClient;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void constructorTest() throws ClientException {

        Resource r1 = new ComputeBuilder().url("url").build();
        Kind kind = MetamodelKinds.LINK;
        List<Mixin> mixins = new ArrayList<>();
        try {
            Link link = new Link(Optional.of("user"),
                                 kind,
                                 Optional.of("title"),
                                 mixins,
                                 r1,
                                 "r2sfg",
                                 Optional.of(InfrastructureKinds.COMPUTE));
            assertThat(link.getSource()).isEqualTo(r1);
            assertThat(link.getKind()).isEqualTo(kind);
            assertThat(link.getId().toString()).startsWith("user");
            assertThat(link.getTitle().equals("title"));
            assertThat(link.getTargetKind().get()).isEqualTo(InfrastructureKinds.COMPUTE);
        } catch (SyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void builderTest() throws IOException, CloudAutomationServerException {
        Compute compute = new ComputeBuilder().url("compute").build();

        try {
            Link link = new Link.Builder(compute, "target").url("url")
                                                           .targetKind(InfrastructureKinds.COMPUTE)
                                                           .title("titleTest")
                                                           .build();

            assertThat(link.getId().toString()).contains("url");
            assertThat(link.getSource()).isEqualTo(compute);
            assertThat(link.getTarget().toString()).contains("target");
            assertThat(link.getTitle().get()).isEqualTo("titleTest");
            assertThat(link.getId().toString()).contains("url");
            assertThat(link.getTargetKind().get()).isEqualTo(InfrastructureKinds.COMPUTE);
        } catch (SyntaxException e) {
            e.printStackTrace();
        }
    }
}
