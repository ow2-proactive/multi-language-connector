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
package org.ow2.proactive.procci.model.occi.infrastructure;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.CloudAutomationServerException;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureIdentifiers;
import org.ow2.proactive.procci.model.occi.infrastructure.state.ComputeState;
import org.ow2.proactive.procci.model.occi.metamodel.MixinBuilder;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.ResourceRendering;
import org.ow2.proactive.procci.service.occi.MixinService;


/**
 * Created by the Activeeon team on 2/24/16.
 */
public class ComputeTest {

    @Mock
    private MixinService mixinService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void computeConstructorTest() throws IOException, CloudAutomationServerException {

        ComputeBuilder computeBuilder = new ComputeBuilder().url("url")
                                                            .architecture(Compute.Architecture.X64)
                                                            .cores(5)
                                                            .hostame("hostnameTest")
                                                            .memory(new Float(3))
                                                            .state(ComputeState.SUSPENDED)
                                                            .share(2)
                                                            .summary("summaryTest")
                                                            .title("titleTest");

        Compute compute = computeBuilder.build();

        assertThat(compute.getArchitecture().get()).isEquivalentAccordingToCompareTo(Compute.Architecture.X64);
        assertThat(compute.getCores().get()).isEqualTo(new Integer(5));
        assertThat(compute.getHostname().get()).isEqualTo("hostnameTest");
        assertThat(compute.getMemory().get()).isWithin(new Float(0.0001).compareTo(new Float(3)));
        assertThat(compute.getState().get()).isEquivalentAccordingToCompareTo(ComputeState.SUSPENDED);
        assertThat(compute.getSummary().get()).isEqualTo("summaryTest");
        assertThat(compute.getShare().get()).isEqualTo(new Integer(2));
        assertThat(compute.getTitle().get()).isEqualTo("titleTest");
    }

    @Test
    public void constructFromModelTest() {

        ComputeBuilder computeBuilder;

        Model model = new Model.Builder("modelTest", "actionTypeTest").addVariable("occi.compute.memory", "2.0")
                                                                      .addVariable("occi.compute.cores", "4")
                                                                      .addVariable("endpoint", "10.0.0.1")
                                                                      .addVariable("status", "RUNNING")
                                                                      .addVariable("occi.compute.architecture", "x86")
                                                                      .addVariable("occi.entity.title", "titleTest")
                                                                      .build();
        try {
            computeBuilder = new ComputeBuilder(model);
            assertThat(computeBuilder.getArchitecture().get()).isEqualTo(Compute.Architecture.X86);
            assertThat(computeBuilder.getCores().get()).isEqualTo(new Integer(4));
            assertThat(computeBuilder.getMemory().get()).isWithin(new Float(0.0001)).of(new Float(2));
            assertThat(computeBuilder.getHostname().get()).matches("10.0.0.1");
            assertThat(computeBuilder.getTitle().get()).matches("titleTest");
            assertThat(computeBuilder.getState().get()).isEqualTo(ComputeState.ACTIVE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void constructFromRenderingTest() throws ClientException, IOException {

        when(mixinService.getMixinBuilder("occi.compute.speed")).thenReturn(Optional.empty());
        when(mixinService.getMixinBuilder("occi.compute.memory")).thenReturn(Optional.empty());
        when(mixinService.getMixinBuilder("occi.compute.cores")).thenReturn(Optional.empty());
        when(mixinService.getMixinBuilder("occi.compute.hostname")).thenReturn(Optional.empty());
        when(mixinService.getMixinBuilder("occi.entity.title")).thenReturn(Optional.empty());
        when(mixinService.getMixinBuilder("occi.compute.architecture")).thenReturn(Optional.empty());
        when(mixinService.getMixinBuilder("occi.compute.state")).thenReturn(Optional.empty());
        when(mixinService.getMixinBuilder("occi.core.summary")).thenReturn(Optional.empty());

        ResourceRendering computeRendering = new ResourceRendering.Builder("http://schemas.ogf.org/occi/infrastructure#compute",
                                                                           "urn:uuid:996ad860−2a9a−504f−886−aeafd0b2ae29").addAttribute("occi.compute.speed",
                                                                                                                                        2)
                                                                                                                          .addAttribute("occi.compute.memory",
                                                                                                                                        4.0)
                                                                                                                          .addAttribute("occi.compute.cores",
                                                                                                                                        2)
                                                                                                                          .addAttribute("occi.compute.hostname",
                                                                                                                                        "80.200.35.140")
                                                                                                                          .addAttribute("occi.entity.title",
                                                                                                                                        "titleTest")
                                                                                                                          .addAttribute("occi.compute.architecture",
                                                                                                                                        "x86")
                                                                                                                          .addAttribute("occi.compute.state",
                                                                                                                                        "ACTIVE")
                                                                                                                          .addAttribute("occi.core.summary",
                                                                                                                                        "summaryTest")
                                                                                                                          .build();

        Compute compute = new ComputeBuilder(mixinService, computeRendering).build();

        assertThat(compute.getRenderingId()).matches("urn:uuid:996ad860−2a9a−504f−886−aeafd0b2ae29");
        assertThat(compute.getId()).matches("urn:uuid:996ad860-2a9a-504f-886-aeafd0b2ae29");
        assertThat(compute.getKind().getTitle()).matches(InfrastructureIdentifiers.INFRASTRUCTURE_SCHEME +
                                                         InfrastructureIdentifiers.COMPUTE);
        assertThat(compute.getCores().get()).isEqualTo(new Integer(2));
        assertThat(compute.getMemory().get()).isWithin(new Float(0.001)).of(new Float(4.0));
        assertThat(compute.getHostname().get()).matches("80.200.35.140");
        assertThat(compute.getTitle().get()).matches("titleTest");
        assertThat(compute.getArchitecture().get()).isEqualTo(Compute.Architecture.X86);
        assertThat(compute.getState().get()).isEqualTo(ComputeState.ACTIVE);
        assertThat(compute.getMixins()).isEmpty();
        assertThat(compute.getSummary().get()).matches("summaryTest");

        ResourceRendering noArgsRendering = new ResourceRendering();
        Compute defaultCompute = new ComputeBuilder(mixinService, noArgsRendering).build();

        assertThat(defaultCompute.getShare().isPresent()).isFalse();
        assertThat(defaultCompute.getSummary().isPresent()).isFalse();
        assertThat(defaultCompute.getMemory().isPresent()).isFalse();
        assertThat(defaultCompute.getCores().isPresent()).isFalse();
        assertThat(defaultCompute.getArchitecture().isPresent()).isFalse();
        assertThat(defaultCompute.getState().isPresent()).isFalse();
        assertThat(defaultCompute.getTitle().isPresent()).isFalse();
        assertThat(defaultCompute.getId()).startsWith("urn:uuid:");
        assertThat(defaultCompute.getKind()).isNotNull();

    }

    @Test
    public void getRenderingTest() throws IOException, CloudAutomationServerException {

        ComputeBuilder computeBuilder = new ComputeBuilder().url("url")
                                                            .architecture(Compute.Architecture.X64)
                                                            .cores(5)
                                                            .hostame("hostnameTest")
                                                            .memory(new Float(3))
                                                            .state(ComputeState.SUSPENDED)
                                                            .share(2)
                                                            .summary("summaryTest")
                                                            .title("titleTest");

        when(mixinService.getMixinBuilder("titleTest")).thenReturn(Optional.of(new MixinBuilder("schemeMixinTest",
                                                                                                "termMixinTest")));
        ResourceRendering rendering = computeBuilder.build().getRendering();
        assertThat(rendering.getId()).matches("url");
        assertThat(rendering.getKind()).matches(InfrastructureIdentifiers.INFRASTRUCTURE_SCHEME +
                                                InfrastructureIdentifiers.COMPUTE);
        assertThat(rendering.getAttributes()).containsEntry("occi.compute.hostname", "hostnameTest");
        assertThat(rendering.getAttributes()).containsEntry("occi.compute.memory", new Float(3));
        assertThat(rendering.getAttributes()).containsEntry("occi.compute.cores", new Integer(5));
        assertThat(rendering.getAttributes()).containsEntry("occi.compute.architecture", "X64");
        assertThat(rendering.getAttributes()).containsEntry("occi.entity.title", "titleTest");
        assertThat(rendering.getAttributes()).containsEntry("occi.compute.state", "SUSPENDED");
        assertThat(rendering.getAttributes()).containsEntry("occi.core.summary", "summaryTest");

        assertThat(rendering.getLinks()).isEmpty();
        assertThat(rendering.getActions()).isEmpty();
        assertThat(rendering.getMixins()).isEmpty();
    }
}
