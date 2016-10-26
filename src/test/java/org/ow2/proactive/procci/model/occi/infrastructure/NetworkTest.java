package org.ow2.proactive.procci.model.occi.infrastructure;

import org.ow2.proactive.procci.model.occi.infrastructure.state.NetworkState;
import com.google.common.truth.Truth;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by the Activeeon team on 2/25/16.
 */

public class NetworkTest {

    @Test
    public void constructorTest() {
        Network network = new Network.Builder()
                .url("url")
                .label("label")
                .state(NetworkState.ACTIVE)
                .title("title")
                .vlan(2)
                .summary("summary")
                .build();

        assertThat(network.getSummary().get()).isEqualTo("summary");
        assertThat(network.getTitle().get()).isEqualTo("title");
        assertThat(network.getLabel().get()).isEqualTo("label");
        assertThat(network.getVlan().get()).isEqualTo(new Integer(2));
        Truth.assertThat(network.getState().get()).isEqualTo(NetworkState.ACTIVE);
    }

}
