package org.ow2.proactive.procci.model.occi.infrastructure.mixin;

import java.net.UnknownHostException;
import java.util.ArrayList;

import org.ow2.proactive.procci.model.occi.metamodel.Attribute;
import org.ow2.proactive.procci.model.occi.metamodel.Entity;
import org.ow2.proactive.procci.model.occi.metamodel.Type;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by the Activeeon team  on 2/25/16.
 */
public class IPNetworkInterfaceTest {

    @Test
    public void maximalConstructorTest() {
        IPNetworkInterface ipNetworkInterface;
        try {
            ipNetworkInterface = new IPNetworkInterface("google.com", "openstack.com", true,
                    new ArrayList<Entity>());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        assertThat(ipNetworkInterface.getAddress()).isEqualTo("google.com");
        assertThat(ipNetworkInterface.getGateway()).isEqualTo("openstack.com");
        assertThat(ipNetworkInterface.isDynamic()).isTrue();
        assertThat(ipNetworkInterface.getAttributes()).contains(
                new Attribute.Builder("occi.networkinterface.address").type(Type.OBJECT).mutable(
                        false).required(false).build());
    }
}
