package org.ow2.proactive.procci.model.occi.metamodel;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 2/24/16.
 */
public class AttributeTest {

    @Test
    public void minimalConstructorTest() {
        Attribute attribute = new Attribute.Builder("test").type(Type.OBJECT).mutable(true).required(
                false).build();
        assertThat(attribute.getName()).isEqualTo("test");
        assertThat(attribute.getType().get()).isEqualTo(Type.OBJECT);
        assertThat(attribute.getRequired().get()).isFalse();
        assertThat(attribute.getMutable().get()).isTrue();
    }

    @Test
    public void maximalConstructorTest() {
        Object o = new Object();
        Attribute attribute = new Attribute.Builder("test").type(Type.OBJECT).mutable(true).required(
                false).pattern(o).defaultValue(
                1).description("testSuit").build();
        assertThat(attribute.getName()).isEqualTo("test");
        assertThat(attribute.getType().get()).isEqualTo(Type.OBJECT);
        assertThat(attribute.getRequired().get()).isFalse();
        assertThat(attribute.getMutable().get()).isTrue();
        assertThat(attribute.getPattern().equals(o));
        assertThat(attribute.getDefaultValue().get()).isEqualTo(new Integer(1));
        assertThat(attribute.getDescription().get()).isEqualTo("testSuit");
    }

    @Test
    public void maximalConstructorDescritpionTest() {
        Attribute attribute = new Attribute.Builder("test").type(Type.OBJECT).mutable(true).required(
                false).description(
                "testSuit").build();
        assertThat(attribute.getName()).isEqualTo("test");
        assertThat(attribute.getType().get()).isEqualTo(Type.OBJECT);
        assertThat(attribute.getRequired().get()).isFalse();
        assertThat(attribute.getMutable().get()).isTrue();
        assertThat(attribute.getPattern()).isNull();
        assertThat(attribute.getDefaultValue()).isNull();
        assertThat(attribute.getDescription().get()).isEqualTo("testSuit");
    }
}
