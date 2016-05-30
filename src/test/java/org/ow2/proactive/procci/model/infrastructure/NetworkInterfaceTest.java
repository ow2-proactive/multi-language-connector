package org.ow2.proactive.procci.model.infrastructure;

import org.ow2.proactive.procci.model.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.infrastructure.state.NetworkState;
import com.google.common.truth.Truth;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 2/25/16.
 */

public class NetworkInterfaceTest {

    @Test
    public void maximalConstructorTest() {
        Compute compute = new Compute.Builder("url:compute").build();
        NetworkInterface networkInterface = new NetworkInterface.Builder("url:networkinterface", compute,
                "url:target", "mac", "linktarget")
                .title("titleTest")
                .state(NetworkState.ACTIVE)
                .targetKind(InfrastructureKinds.STORAGE)
                .build();

        Truth.assertThat(networkInterface.getTitle()).isEqualTo("titleTest");
        Truth.assertThat(networkInterface.getState()).isEquivalentAccordingToCompareTo(NetworkState.ACTIVE);
        Truth.assertThat(networkInterface.getTarget().toString()).isEqualTo("url:target");
        assertThat(networkInterface.getMac()).isEqualTo("mac");
        Truth.assertThat(networkInterface.getSource()).isEqualTo(compute);
        Truth.assertThat(networkInterface.getKind()).isEqualTo(InfrastructureKinds.NETWORK_INTERFACE);
        Truth.assertThat(networkInterface.getId().toString()).contains("url:networkinterface");
        assertThat(networkInterface.getLinkInterface()).isEqualTo("linktarget");
    }
}