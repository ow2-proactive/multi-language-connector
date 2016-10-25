package org.ow2.proactive.procci.model.occi.infrastructure;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.CloudAutomationException;
import org.ow2.proactive.procci.model.occi.infrastructure.state.ComputeState;
import org.ow2.proactive.procci.model.occi.metamodel.MixinBuilder;
import org.ow2.proactive.procci.model.occi.metamodel.ProviderMixin;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.ResourceRendering;
import org.ow2.proactive.procci.request.DataServices;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by mael on 2/24/16.
 */
public class ComputeTest {

    @InjectMocks
    private ComputeBuilder computeBuilder;

    @Mock
    private ProviderMixin providerMixin;

    @Mock
    private DataServices dataServices;

    @Before
    public void setUp() {
        computeBuilder = new ComputeBuilder(providerMixin, dataServices).url("url")
                .architecture(Compute.Architecture.X64)
                .cores(5)
                .hostame("hostnameTest")
                .memory(new Float(3))
                .state(ComputeState.SUSPENDED)
                .share(2)
                .summary("summaryTest")
                .title("titleTest");

        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void computeConstructorTest() throws IOException, CloudAutomationException {

        Compute compute = computeBuilder.build();

        assertThat(compute.getArchitecture().get()).isEquivalentAccordingToCompareTo(
                Compute.Architecture.X64);
        assertThat(compute.getCores().get()).isEqualTo(new Integer(5));
        assertThat(compute.getHostname().get()).isEqualTo("hostnameTest");
        assertThat(compute.getMemory().get()).isWithin(new Float(0.0001).compareTo(new Float(3)));
        assertThat(compute.getState().get()).isEquivalentAccordingToCompareTo(ComputeState.SUSPENDED);
        assertThat(compute.getSummary().get()).isEqualTo("summaryTest");
        assertThat(compute.getShare().get()).isEqualTo(new Integer(2));
        assertThat(compute.getTitle().get()).isEqualTo("titleTest");
    }

    @Test
    public void ComputeBuilderUpdateTest() {

        Model model = new Model.Builder("modelTest", "actionTypeTest")
                .addVariable("occi.compute.memory", "2.0")
                .addVariable("occi.compute.cores", "4")
                .addVariable("endpoint", "10.0.0.1")
                .addVariable("status", "RUNNING")
                .addVariable("occi.compute.architecture", "x86")
                .addVariable("occi.entity.title", "titleTest")
                .build();
        try {
            ComputeBuilder computeBuilder = new ComputeBuilder(providerMixin,
                    dataServices).cloudAutomationModel(model);
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
    public void toCloudAutomationModelTest() throws IOException, CloudAutomationException {
        Model model = computeBuilder.build().toCloudAutomationModel("create");
        assertThat(model.getServiceModel()).isEqualTo("occi.infrastructure.compute");
        assertThat(model.getActionType()).isEqualTo("create");
        assertThat(model.getVariables()).containsEntry("occi.compute.cores", "5");
        assertThat(model.getVariables()).containsEntry("occi.compute.architecture", "X64");
        assertThat(model.getVariables()).containsEntry("occi.compute.memory", "3.0");
        assertThat(model.getVariables()).containsEntry("occi.entity.title", "titleTest");
    }

    @Test
    public void constructFromRenderingTest() throws ClientException, IOException {

        when(providerMixin.getInstance("occi.compute.speed")).thenReturn(Optional.empty());
        when(providerMixin.getInstance("occi.compute.memory")).thenReturn(Optional.empty());
        when(providerMixin.getInstance("occi.compute.cores")).thenReturn(Optional.empty());
        when(providerMixin.getInstance("occi.compute.hostname")).thenReturn(Optional.empty());
        when(providerMixin.getInstance("occi.entity.title")).thenReturn(Optional.empty());
        when(providerMixin.getInstance("occi.compute.architecture")).thenReturn(Optional.empty());
        when(providerMixin.getInstance("occi.compute.state")).thenReturn(Optional.empty());
        when(providerMixin.getInstance("occi.core.summary")).thenReturn(Optional.empty());

        ResourceRendering computeRendering = new ResourceRendering
                .Builder("http://schemas.ogf.org/occi/infrastructure#compute",
                "urn:uuid:996ad860−2a9a−504f−886−aeafd0b2ae29")
                .addAttribute("occi.compute.speed", 2)
                .addAttribute("occi.compute.memory", 4.0)
                .addAttribute("occi.compute.cores", 2)
                .addAttribute("occi.compute.hostname", "80.200.35.140")
                .addAttribute("occi.entity.title", "titleTest")
                .addAttribute("occi.compute.architecture", "x86")
                .addAttribute("occi.compute.state", "ACTIVE")
                .addAttribute("occi.core.summary", "summaryTest")
                .build();


        Compute compute = computeBuilder.rendering(computeRendering).build();

        assertThat(compute.getRenderingId()).matches("urn:uuid:996ad860−2a9a−504f−886−aeafd0b2ae29");
        assertThat(compute.getId()).matches("urn:uuid:996ad860-2a9a-504f-886-aeafd0b2ae29");
        assertThat(compute.getKind().getTitle()).matches(
                "compute");
        assertThat(compute.getCores().get()).isEqualTo(new Integer(2));
        assertThat(compute.getMemory().get()).isWithin(new Float(0.001)).of(new Float(4.0));
        assertThat(compute.getHostname().get()).matches("80.200.35.140");
        assertThat(compute.getTitle().get()).matches("titleTest");
        assertThat(compute.getArchitecture().get()).isEqualTo(Compute.Architecture.X86);
        assertThat(compute.getState().get()).isEqualTo(ComputeState.ACTIVE);
        assertThat(compute.getMixins()).isEmpty();
        assertThat(compute.getSummary().get()).matches("summaryTest");

        ResourceRendering noArgsRendering = new ResourceRendering();
        Compute defaultCompute = new ComputeBuilder(providerMixin, dataServices).rendering(
                noArgsRendering).build();

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
    public void getRenderingTest() throws IOException, CloudAutomationException {

        when(providerMixin.getInstance("titleTest")).thenReturn(
                Optional.of(new MixinBuilder(providerMixin, "schemeMixinTest", "termMixinTest")));
        ResourceRendering rendering = computeBuilder.build().getRendering();
        assertThat(rendering.getId()).matches("url");
        assertThat(rendering.getKind()).matches("compute");
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

    @Test
    public void associateProviderMixin() throws IOException, ClientException {
        when(providerMixin.getInstance("title")).thenReturn(Optional.empty());
        when(providerMixin.getInstance("title2")).thenReturn(
                Optional.of(new MixinBuilder(providerMixin, "scheme", "term")));
        Map<String, Object> attributes = new HashMap<>();
        Map<String, String> attribute1 = new HashMap<>();
        Map<String, String> attribute2 = new HashMap<>();
        attribute1.put("key", "value");
        attribute2.put("key2", "value2");
        attributes.put("title", attribute1);
        attributes.put("title2", attribute2);

        computeBuilder.associateProviderMixin(attributes);
    }

}
