package org.ow2.proactive.procci.model.metamodel;

import java.util.ArrayList;
import java.util.List;

import org.ow2.proactive.procci.model.infrastructure.mixin.Contextualization;
import org.ow2.proactive.procci.model.metamodel.constants.Kinds;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 2/25/16.
 */
public class EntityTest {

    private class EntityImplemented extends Entity {

        EntityImplemented(String url, Kind k, String title, List<Mixin> mixins) {
            super(url, k, title, mixins);
        }
    }


    @Test
    public void maximalConstructorTest() {
        Kind k = Kinds.ENTITY;
        List<Mixin> mixins = new ArrayList<>();
        Contextualization contextualization = new Contextualization("userDataTest", null);
        mixins.add(contextualization);
        EntityImplemented implemented = new EntityImplemented("url", k, "titleTest2", mixins);

        assertThat(implemented.getId().toString()).startsWith("urn:url:");
        assertThat(implemented.getKind()).isEqualTo(k);
        assertThat(implemented.getTitle()).isEqualTo("titleTest2");
        assertThat(implemented.getMixins()).containsExactly(contextualization);
    }
}
