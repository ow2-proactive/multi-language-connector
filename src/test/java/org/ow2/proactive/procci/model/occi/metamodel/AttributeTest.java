/*
 * ProActive Parallel Suite(TM):
 * The Open Source library for parallel and distributed
 * Workflows & Scheduling, Orchestration, Cloud Automation
 * and Big Data Analysis on Enterprise Grids & Clouds.
 *
 * Copyright (c) 2007 - 2017 ActiveEon
 * Contact: contact@activeeon.com
 *
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation: version 3 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 */
package org.ow2.proactive.procci.model.occi.metamodel;

import static com.google.common.truth.Truth.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.AttributeRendering;


/**
 * Created by the Activeeon team  on 2/24/16.
 */
public class AttributeTest {

    @Test
    public void minimalConstructorTest() {
        Attribute attribute = new Attribute.Builder("test").type(Type.OBJECT).mutable(true).required(false).build();
        assertThat(attribute.getName()).isEqualTo("test");
        assertThat(attribute.getType().get()).isEqualTo(Type.OBJECT);
        assertThat(attribute.getRequired().get()).isFalse();
        assertThat(attribute.getMutable().get()).isTrue();
    }

    @Test
    public void maximalConstructorTest() {
        Object o = new Object();
        Attribute attribute = new Attribute.Builder("test").type(Type.OBJECT)
                                                           .mutable(true)
                                                           .required(false)
                                                           .pattern(o)
                                                           .defaultValue(1)
                                                           .description("testSuit")
                                                           .build();
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
        Attribute attribute = new Attribute.Builder("test").type(Type.OBJECT)
                                                           .mutable(true)
                                                           .required(false)
                                                           .description("testSuit")
                                                           .build();
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
        Attribute attribute = new Attribute.Builder("nameTest").type(Type.OBJECT)
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
