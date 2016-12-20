package org.ow2.proactive.procci.service.transformer;

import java.io.IOException;

import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.exception.CloudAutomationException;
import org.ow2.proactive.procci.model.occi.infrastructure.Compute;
import org.ow2.proactive.procci.model.occi.infrastructure.ComputeBuilder;
import org.ow2.proactive.procci.model.occi.infrastructure.state.ComputeState;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class ComputeTransformerTest {


    @Test
    public void toCloudAutomationModelTest() throws CloudAutomationException {
        Compute compute = new ComputeBuilder().url("url")
                .architecture(Compute.Architecture.X64)
                .cores(5)
                .hostame("hostnameTest")
                .memory(new Float(3))
                .state(ComputeState.SUSPENDED)
                .share(2)
                .summary("summaryTest")
                .title("titleTest")
                .build();

        Model model = new ComputeTransformer().toCloudAutomationModel(compute,"create");
        assertThat(model.getServiceModel()).isEqualTo("occi.infrastructure.compute");
        assertThat(model.getActionType()).isEqualTo("create");
        assertThat(model.getVariables()).containsEntry("occi.compute.cores", "5");
        assertThat(model.getVariables()).containsEntry("occi.compute.architecture", "X64");
        assertThat(model.getVariables()).containsEntry("occi.compute.memory", "3.0");
        assertThat(model.getVariables()).containsEntry("occi.entity.title", "titleTest");
    }

}
