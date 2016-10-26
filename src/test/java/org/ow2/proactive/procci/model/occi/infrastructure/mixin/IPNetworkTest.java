package org.ow2.proactive.procci.model.occi.infrastructure.mixin;


import java.net.UnknownHostException;
import java.util.ArrayList;

import org.ow2.proactive.procci.model.occi.metamodel.Entity;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by the Activeeon team  on 2/25/16.
 */

public class IPNetworkTest {


    @Test
    public void maximalConstructorTest() {
        IPNetwork ipNetwork;
        try {
            ipNetwork = new IPNetwork("google.com", "amazon.com", true,
                    new ArrayList<Entity>());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        assertThat(ipNetwork.getAddress()).isEqualTo("google.com");
        assertThat(ipNetwork.getGateway()).isEqualTo("amazon.com");
        assertThat(ipNetwork.isDynamicAllocation()).isTrue();
    }
}
