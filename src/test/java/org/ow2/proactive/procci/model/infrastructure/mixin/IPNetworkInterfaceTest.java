package org.ow2.proactive.procci.model.infrastructure.mixin;

import java.net.UnknownHostException;
import java.util.ArrayList;

import org.ow2.proactive.procci.model.metamodel.Attribute;
import org.ow2.proactive.procci.model.metamodel.Entity;
import org.ow2.proactive.procci.model.metamodel.Type;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 2/25/16.
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
                new Attribute.Builder("occi.networkinterface.address", Type.OBJECT, false, false).build());
    }
}
