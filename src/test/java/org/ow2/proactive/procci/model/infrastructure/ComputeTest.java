package org.ow2.proactive.procci.model.infrastructure;


import org.ow2.proactive.procci.model.infrastructure.action.RestartCompute;
import org.ow2.proactive.procci.model.infrastructure.action.SaveCompute;
import org.ow2.proactive.procci.model.infrastructure.action.StartCompute;
import org.ow2.proactive.procci.model.infrastructure.action.StopCompute;
import org.ow2.proactive.procci.model.infrastructure.action.SuspendCompute;
import org.ow2.proactive.procci.model.infrastructure.state.ComputeState;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 2/24/16.
 */

public class ComputeTest {

    @Test
    public void computeActionTest() {
        Compute compute = new Compute.Builder("url")
                .addAction(StartCompute.getInstance())
                .addAction(StopCompute.getInstance())
                .addAction(RestartCompute.getInstance())
                .addAction(SaveCompute.getInstance())
                .addAction(SuspendCompute.getInstance())
                .build();
        assertThat(compute.getActions()).containsExactly(StartCompute.getInstance(),
                StopCompute.getInstance(),
                RestartCompute.getInstance(),
                SaveCompute.getInstance(),
                SuspendCompute.getInstance());
    }

    @Test
    public void computeConstructorTest() {
        Compute compute = new Compute.Builder("url")
                .architecture(Compute.Architecture.X64)
                .cores(5)
                .hostame("hostname")
                .memory(new Float(3))
                .state(ComputeState.SUSPENDED)
                .share(2)
                .summary("summary")
                .title("title")
                .build();

        assertThat(compute.getArchitecture()).isEquivalentAccordingToCompareTo(Compute.Architecture.X64);
        assertThat(compute.getCores()).isEqualTo(new Integer(5));
        assertThat(compute.getHostname()).isEqualTo("hostname");
        assertThat(compute.getMemory()).isWithin(new Float(0.0001).compareTo(new Float(3)));
        assertThat(compute.getState()).isEquivalentAccordingToCompareTo(ComputeState.SUSPENDED);
        assertThat(compute.getSummary()).isEqualTo("summary");
        assertThat(compute.getShare()).isEqualTo(new Integer(2));
        assertThat(compute.getTitle()).isEqualTo("title");
    }
}
