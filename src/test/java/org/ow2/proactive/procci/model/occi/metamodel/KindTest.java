package org.ow2.proactive.procci.model.occi.metamodel;

import java.util.HashSet;
import java.util.Set;

import org.ow2.proactive.procci.model.occi.infrastructure.action.StartCompute;
import org.ow2.proactive.procci.model.occi.metamodel.constants.Kinds;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 2/24/16.
 */
public class KindTest {

    @Test
    public void minimalConstructorTest() {
        String uri = "http://schemas.ogf.org/occi/infrastructure#compute";
        Kind k = new Kind.Builder(uri, "compute").build();
        assertThat(k.getTerm()).isEqualTo("compute");
        assertThat(k.getScheme().toString()).isEqualTo(uri);
    }

    @Test
    public void maximalConstructorTest() {
        String uri = "http://schemas.ogf.org/occi/infrastructure#compute";

        Attribute a = new Attribute.Builder("test", Type.LIST, false, false).build();

        Set<Attribute> attributes = new HashSet<>();
        attributes.add(a);

        Kind parent = Kinds.RESOURCE;
        Kind k = new Kind.Builder(uri, "compute")
                .addAttribute(attributes)
                .addAction(StartCompute.getInstance())
                .addParent(parent)
                .build();

        assertThat(k.getTerm()).isEqualTo("compute");
        assertThat(k.getScheme().toString()).isEqualTo(uri);

        assertThat(k.getActions()).containsKey("Action.start");
        assertThat(k.getActions().get("Action.start")).isEqualTo(StartCompute.getInstance());
        assertThat(k.getEntities()).isEmpty();
        assertThat(k.getParent()).isEqualTo(parent);
        assertThat(k.getAttributes()).containsAnyOf(a,
                new Attribute.Builder("occi.kind.actions", Type.OBJECT, true, true).build());
    }
}
