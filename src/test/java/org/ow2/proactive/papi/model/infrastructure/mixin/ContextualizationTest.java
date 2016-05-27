package org.ow2.proactive.papi.model.infrastructure.mixin;

import java.util.ArrayList;

import org.ow2.proactive.papi.model.metamodel.Attribute;
import org.ow2.proactive.papi.model.metamodel.Entity;
import org.ow2.proactive.papi.model.metamodel.Type;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 2/25/16.
 */
public class ContextualizationTest {


    @Test
    public void maximalConstructorTest() {
        Contextualization contextualization = new Contextualization("userdataTest", new ArrayList<Entity>());
        assertThat(contextualization.getUserdata()).isEqualTo("userdataTest");
        assertThat(contextualization.getAttributes()).contains(
                new Attribute.Builder("occi.compute.userdata", Type.OBJECT, false, false).build());
    }
}
