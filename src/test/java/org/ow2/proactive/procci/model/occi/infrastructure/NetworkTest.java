package org.ow2.proactive.procci.model.occi.infrastructure;

import org.ow2.proactive.procci.model.occi.infrastructure.action.DownNetwork;
import org.ow2.proactive.procci.model.occi.infrastructure.action.UpNetwork;
import org.ow2.proactive.procci.model.occi.infrastructure.state.NetworkState;
import com.google.common.truth.Truth;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 2/25/16.
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

        assertThat(network.getSummary()).isEqualTo("summary");
        assertThat(network.getTitle()).isEqualTo("title");
        assertThat(network.getLabel()).isEqualTo("label");
        assertThat(network.getVlan()).isEqualTo(new Integer(2));
        Truth.assertThat(network.getState()).isEqualTo(NetworkState.ACTIVE);
    }

}
