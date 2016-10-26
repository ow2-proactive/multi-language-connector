package org.ow2.proactive.procci.model.occi.metamodel;

import java.util.Optional;

import org.ow2.proactive.procci.model.occi.metamodel.rendering.AttributeRendering;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by the Activeeon team  on 2/24/16.
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
    public void maximalConstructorDescriptionTest() {
        Attribute attribute = new Attribute.Builder("test").type(Type.OBJECT).mutable(true).required(
                false).description(
                "testSuit").build();
        assertThat(attribute.getName()).isEqualTo("test");
        assertThat(attribute.getType().get()).isEqualTo(Type.OBJECT);
        assertThat(attribute.getRequired().get()).isFalse();
        assertThat(attribute.getMutable().get()).isTrue();
        assertThat(attribute.getPattern()).isEqualTo(Optional.empty());
        assertThat(attribute.getDefaultValue()).isEqualTo(Optional.empty());
        assertThat(attribute.getDescription().get()).isEqualTo("testSuit");
    }

    @Test
    public void getRenderingTest() {
        Attribute attribute = new Attribute.Builder("nameTest")
                .type(Type.OBJECT)
                .mutable(false)
                .required(true)
                .defaultValue("defaultTest")
                .description("descriptionTest")
                .pattern("patternTest")
                .build();

        AttributeRendering rendering = attribute.getRendering();

        assertThat(rendering.getType()).matches("OBJECT");
        assertThat(rendering.isMutable()).isFalse();
        assertThat(rendering.isRequired()).isTrue();
        assertThat(rendering.getDefaultValue()).isEqualTo("defaultTest");
        assertThat(rendering.getDescription()).matches("descriptionTest");
        assertThat(rendering.getPattern()).isEqualTo("patternTest");

    }
}
