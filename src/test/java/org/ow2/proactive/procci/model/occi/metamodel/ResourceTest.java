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
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.SyntaxException;
import org.ow2.proactive.procci.model.occi.infrastructure.Compute;
import org.ow2.proactive.procci.model.occi.infrastructure.ComputeBuilder;
import org.ow2.proactive.procci.model.occi.infrastructure.state.ComputeState;
import org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelKinds;
import org.ow2.proactive.procci.service.CloudAutomationVariablesClient;
import org.ow2.proactive.procci.service.occi.MixinService;


/**
 * Created by the Activeeon team  on 2/25/16.
 */
public class ResourceTest {

    @Mock
    CloudAutomationVariablesClient cloudAutomationVariablesClient;

    @Mock
    private MixinService mixinService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void constructorTest() {

        Kind kind = MetamodelKinds.RESOURCE;
        List<Mixin> mixins = new ArrayList<>();
        List<Link> links = new ArrayList<>();
        mixins.add(new Mixin("http://schemas.ogf.org/occi/metamodel#mixin",
                             "mixin",
                             "mixin",
                             new HashSet<Attribute>(),
                             new ArrayList<Action>(),
                             new ArrayList<Mixin>(),
                             new ArrayList<Kind>(),
                             new ArrayList<Entity>()));
        Resource resource = new Resource(Optional.of("url"),
                                         kind,
                                         Optional.of("titleTest"),
                                         mixins,
                                         Optional.of("summaryTest"),
                                         links);

        assertThat(resource.getSummary().get()).isEqualTo("summaryTest");
        assertThat(resource.getLinks()).isEqualTo(links);
        assertThat(resource.getMixins()).isEqualTo(mixins);
        assertThat(resource.getKind()).isEqualTo(kind);
        assertThat(resource.getId().toString()).startsWith("url");
    }

    @Test
    public void builderTest() throws IOException, ClientException {
        Compute compute = new ComputeBuilder().url("compute").build();
        try {
            Link link = new Link.Builder(compute, "target").url("link").build();
            Attribute mixinAttribute = new Attribute.Builder("attribute").type(Type.OBJECT)
                                                                         .mutable(false)
                                                                         .required(true)
                                                                         .build();
            Mixin mixin = new MixinBuilder("schemeTest", "termTest").addAttribute(mixinAttribute).build();
            Resource resource = new ResourceBuilder().url("resource")
                                                     .summary("summary")
                                                     .title("title")
                                                     .addLink(link)
                                                     .addMixin(mixin)
                                                     .build();
            assertThat(resource.getSummary().get()).isEqualTo("summary");
            assertThat(resource.getTitle().get()).isEqualTo("title");
            assertThat(resource.getId().toString()).contains("resource");
            assertThat(resource.getLinks()).containsExactly(link);
            assertThat(resource.getMixins()).contains(mixin);
        } catch (SyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void associateProviderMixinTest() throws IOException, ClientException {

        ResourceBuilder resourceBuilder = new ComputeBuilder().url("url")
                                                              .architecture(Compute.Architecture.X64)
                                                              .cores(5)
                                                              .hostame("hostnameTest")
                                                              .memory(new Float(3))
                                                              .state(ComputeState.SUSPENDED)
                                                              .share(2)
                                                              .summary("summaryTest")
                                                              .title("titleTest");

        when(mixinService.getMixinBuilder("title")).thenReturn(Optional.empty());
        when(mixinService.getMixinBuilder("title2")).thenReturn(Optional.of(new MixinBuilder("scheme", "term")));
        Map<String, Object> attributes = new HashMap<>();
        Map<String, String> attribute1 = new HashMap<>();
        Map<String, String> attribute2 = new HashMap<>();
        attribute1.put("key", "value");
        attribute2.put("key2", "value2");
        attributes.put("title", attribute1);
        attributes.put("title2", attribute2);

        resourceBuilder.associateProviderMixin(mixinService, attributes);
    }
}
