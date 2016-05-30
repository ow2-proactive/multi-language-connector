package org.ow2.proactive.procci.model.occi.metamodel;


import java.util.ArrayList;
import java.util.List;

import org.ow2.proactive.procci.model.infrastructure.Compute;
import org.ow2.proactive.procci.model.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.occi.metamodel.constants.Kinds;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 2/24/16.
 */
public class LinkTest {


    @Test
    public void constructorTest() {

        Resource r1 = new Compute.Builder("url").build();
        Kind kind = Kinds.LINK;
        List<Mixin> mixins = new ArrayList<>();
        Link link = new Link("user", kind, "title", mixins, r1, "r2", InfrastructureKinds.COMPUTE);
        assertThat(link.getSource()).isEqualTo(r1);
        assertThat(link.getKind()).isEqualTo(kind);
        assertThat(link.getId().toString()).startsWith("urn:user:");
        assertThat(link.getTitle().equals("title"));
        assertThat(link.getTargetKind()).isEqualTo(InfrastructureKinds.COMPUTE);
    }

    @Test
    public void builderTest() {
        Compute compute = new Compute.Builder("compute").build();
        Link link = new Link.Builder("url", compute, "target")
                .targetKind(InfrastructureKinds.COMPUTE)
                .title("titleTest")
                .build();

        assertThat(link.getId().toString()).contains("url");
        assertThat(link.getSource()).isEqualTo(compute);
        assertThat(link.getTarget().toString()).contains("target");
        assertThat(link.getTitle()).isEqualTo("titleTest");
        assertThat(link.getId().toString()).contains("url");
        assertThat(link.getTargetKind()).isEqualTo(InfrastructureKinds.COMPUTE);
    }
}