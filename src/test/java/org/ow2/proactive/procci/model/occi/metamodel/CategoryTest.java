package org.ow2.proactive.procci.model.occi.metamodel;


import java.util.HashSet;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 2/24/16.
 */

public class CategoryTest {


    @Test
    public void maximalConstructorTest() {

        String uri = "http://schemas.ogf.org/occi/infrastructure#compute";
        Category c = new Category(uri, "compute", "compute", new HashSet<Attribute>());

        assertThat(c.getTerm()).isEqualTo("compute");
        assertThat(c.getScheme().toString()).isEqualTo(uri);
    }

    @Test
    public void getAttributesTest() {
        String uri = "http://schemas.ogf.org/occi/infrastructure#compute";
        Category c = new Category(uri, "compute", "test", new HashSet<Attribute>());

        Attribute a = new Attribute.Builder("occi.category.term").type(Type.OBJECT).mutable(false).required(
                false).build();
        Attribute a1 = new Attribute.Builder("term2").type(Type.OBJECT).mutable(false).required(
                false).build();
        assertThat(c.getAttributes()).containsAnyOf(a, a1);
    }

}
