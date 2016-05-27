package org.ow2.proactive.procci.model.infrastructure.mixin;

import java.util.ArrayList;

import org.ow2.proactive.procci.model.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.metamodel.Attribute;
import org.ow2.proactive.procci.model.metamodel.Entity;
import org.ow2.proactive.procci.model.metamodel.Type;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 2/25/16.
 */
public class CredentialsTest {

    @Test
    public void maximalConstructorTest() {

        Credentials credentials = new Credentials("publickeyTest", new ArrayList<Entity>());
        assertThat(credentials.getPublickey()).isEqualTo("publickeyTest");
        assertThat(credentials.getApplies().contains(InfrastructureKinds.COMPUTE));
        assertThat(credentials.getAttributes().contains(
                new Attribute.Builder("occi.credentials.ssh.publickey", Type.OBJECT, false, false).build()));
    }
}
