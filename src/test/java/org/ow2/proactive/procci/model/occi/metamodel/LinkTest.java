package org.ow2.proactive.procci.model.occi.metamodel;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.ow2.proactive.procci.model.occi.infrastructure.Compute;
import org.ow2.proactive.procci.model.occi.infrastructure.ComputeBuilder;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.occi.metamodel.constants.Kinds;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 2/24/16.
 */
public class LinkTest {


    @Test
    public void constructorTest() {

        Resource r1 = new Compute.Builder().url("url").build();
        Kind kind = Kinds.LINK;
        List<Mixin> mixins = new ArrayList<>();
        Link link = new Link(Optional.of("user"), kind, Optional.of("title"), mixins, r1, "r2sfg", Optional.of(InfrastructureKinds.COMPUTE));
        assertThat(link.getSource()).isEqualTo(r1);
        assertThat(link.getKind()).isEqualTo(kind);
        assertThat(link.getId().toString()).startsWith("user");
        assertThat(link.getTitle().equals("title"));
        assertThat(link.getTargetKind()).isEqualTo(InfrastructureKinds.COMPUTE);
    }

    @Test
    public void builderTest() {
        Compute compute = new ComputeBuilder().url("compute").build();
        Link link = new Link.Builder(compute, "target")
                .url("url")
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
