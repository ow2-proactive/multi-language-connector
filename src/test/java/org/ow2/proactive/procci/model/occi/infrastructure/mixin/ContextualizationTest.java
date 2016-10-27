package org.ow2.proactive.procci.model.occi.infrastructure.mixin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.MissingAttributesException;
import org.ow2.proactive.procci.model.occi.metamodel.Attribute;
import org.ow2.proactive.procci.model.occi.metamodel.Type;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by the Activeeon team on 2/25/16.
 */
public class ContextualizationTest {


    @Test
    public void constructorTest() {
        Contextualization contextualization = new Contextualization("contextualizationTest",
                new ArrayList<>(), new ArrayList<>(), "userdataTest");
        assertThat(contextualization.getUserdata()).isEqualTo("userdataTest");
        assertThat(contextualization.getAttributes()).contains(
                new Attribute.Builder("occi.compute.userdata").type(Type.OBJECT).mutable(false).required(
                        false).build());
    }

    @Test
    public void buildTest() throws ClientException {
        Exception exception = null;
        try {
            new Contextualization.Builder().attributes(new HashMap()).build();
        } catch (MissingAttributesException e) {
            exception = e;
        }

        assertThat(exception).isInstanceOf(MissingAttributesException.class);

        Map attributes = new HashMap();
        attributes.put("occi.compute.userdata", "userdataTest");
        attributes.put("occi.category.title", "titleTest");

        Contextualization contextualization = new Contextualization.Builder().attributes(attributes).build();


        assertThat(contextualization.getUserdata()).matches("userdataTest");
        assertThat(contextualization.getTerm()).matches("user_data");
        assertThat(contextualization.getScheme()).matches(
                "http://schemas.ogf.org/occi/infrastructure/compute#");
        assertThat(contextualization.getTitle()).matches("titleTest");

    }


}
