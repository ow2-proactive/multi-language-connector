package org.ow2.proactive.procci.model.occi.metamodel;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;


/**
 * Created by the Activeeon team  on 2/25/16.
 */
public class ActionTest {

    @Test
    public void constructor() {
        String uri = "http://schemas.ogf.org/occi/metamodel#actiontest";
        Set<Attribute> attributes = new HashSet<>();
        Attribute a = new Attribute.Builder("none").type(Type.HASH).mutable(false).required(false).build();
        attributes.add(a);
        ActionImplemented actionImplemented = new ActionImplemented(uri, "actiontest", "actiontest",
                attributes);

        assertThat(actionImplemented.getScheme().toString()).isEqualTo(uri);
        assertThat(actionImplemented.getTerm()).isEqualTo("actiontest");
        assertThat(actionImplemented.getAttributes()).contains(a);


        //integration test
        assertThat(actionImplemented.getAttributes()).containsAnyOf(
                new Attribute.Builder("occi.category.scheme").type(Type.HASH).mutable(false).required(
                        false).build(),
                new Attribute.Builder("occi.category.term").type(Type.HASH).mutable(false).required(
                        false).build(),
                new Attribute.Builder("occi.category.title").type(Type.HASH).mutable(false).required(
                        false).build());
    }

    private class ActionImplemented extends Action {

        ActionImplemented(String scheme, String term, String title, Set<Attribute> attributes) {
            super(scheme, term, title, attributes);
        }
    }
}
