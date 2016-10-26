package org.ow2.proactive.procci.model.occi.infrastructure.mixin;

import java.util.ArrayList;

import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.occi.metamodel.Attribute;
import org.ow2.proactive.procci.model.occi.metamodel.Entity;
import org.ow2.proactive.procci.model.occi.metamodel.Type;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by the Activeeon team on 2/25/16.
 */
public class CredentialsTest {

    @Test
    public void maximalConstructorTest() {

        Credentials credentials = new Credentials("publickeyTest", new ArrayList<Entity>());
        assertThat(credentials.getPublickey()).isEqualTo("publickeyTest");
        assertThat(credentials.getApplies().contains(InfrastructureKinds.COMPUTE));
        assertThat(credentials.getAttributes().contains(
                new Attribute.Builder("occi.credentials.ssh.publickey").type(Type.OBJECT).mutable(
                        false).required(false).build()));
    }
}
