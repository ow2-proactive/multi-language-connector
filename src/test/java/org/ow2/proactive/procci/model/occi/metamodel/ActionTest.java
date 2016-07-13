package org.ow2.proactive.procci.model.occi.metamodel;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 2/25/16.
 */
public class ActionTest {

    private class ActionImplemented extends Action {

        ActionImplemented(String scheme, String term, String title, Set<Attribute> attributes) {
            super(scheme, term, title, attributes);
        }
    }

    @Test
    public void maximalConstructor() {
        String uri = "http://schemas.ogf.org/occi/metamodel#actiontest";
        Set<Attribute> attributes = new HashSet<>();
        Attribute a = new Attribute.Builder("none", Type.HASH, false, false).build();
        attributes.add(a);
        ActionImplemented actionImplemented = new ActionImplemented(uri, "actiontest", "actiontest",
                attributes);

        assertThat(actionImplemented.getScheme().toString()).isEqualTo(uri);
        assertThat(actionImplemented.getTerm()).isEqualTo("actiontest");
        assertThat(actionImplemented.getAttributes()).contains(a);


        //integration test
        assertThat(actionImplemented.getAttributes()).containsAnyOf(
                new Attribute.Builder("occi.category.scheme", Type.HASH, false, false).build(),
                new Attribute.Builder("occi.category.term", Type.HASH, false, false).build(),
                new Attribute.Builder("occi.category.title", Type.HASH, false, false).build());
    }
}
