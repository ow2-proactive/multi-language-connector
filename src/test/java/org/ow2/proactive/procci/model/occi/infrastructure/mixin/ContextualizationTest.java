package org.ow2.proactive.procci.model.occi.infrastructure.mixin;

import java.util.ArrayList;

import org.ow2.proactive.procci.model.occi.metamodel.Attribute;
import org.ow2.proactive.procci.model.occi.metamodel.Entity;
import org.ow2.proactive.procci.model.occi.metamodel.Type;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by the Activeeon team on 2/25/16.
 */
public class ContextualizationTest {


    @Test
    public void maximalConstructorTest() {
        Contextualization contextualization = new Contextualization("userdataTest", new ArrayList<Entity>());
        assertThat(contextualization.getUserdata()).isEqualTo("userdataTest");
        assertThat(contextualization.getAttributes()).contains(
                new Attribute.Builder("occi.compute.userdata").type(Type.OBJECT).mutable(false).required(
                        false).build());
    }
}
