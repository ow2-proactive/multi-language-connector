package org.ow2.proactive.procci.model.occi.metamodel;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.ow2.proactive.procci.model.exception.CloudAutomationException;
import org.ow2.proactive.procci.model.exception.SyntaxException;
import org.ow2.proactive.procci.model.occi.infrastructure.Compute;
import org.ow2.proactive.procci.model.occi.infrastructure.ComputeBuilder;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.occi.metamodel.constants.Kinds;
import org.ow2.proactive.procci.request.CloudAutomationVariablesClient;
import org.ow2.proactive.procci.request.MixinService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by the Activeeon team  on 2/24/16.
 */
public class LinkTest {

    @Mock
    private MixinService mixinService;

    @Mock
    private CloudAutomationVariablesClient cloudAutomationVariablesClient;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void constructorTest() {

        Resource r1 = new Compute.Builder().url("url").build();
        Kind kind = Kinds.LINK;
        List<Mixin> mixins = new ArrayList<>();
        try {
            Link link = new Link(Optional.of("user"), kind, Optional.of("title"), mixins, r1, "r2sfg",
                    Optional.of(InfrastructureKinds.COMPUTE));
            assertThat(link.getSource()).isEqualTo(r1);
            assertThat(link.getKind()).isEqualTo(kind);
            assertThat(link.getId().toString()).startsWith("user");
            assertThat(link.getTitle().equals("title"));
            assertThat(link.getTargetKind().get()).isEqualTo(InfrastructureKinds.COMPUTE);
        } catch (SyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void builderTest() throws IOException, CloudAutomationException {
        Compute compute = new ComputeBuilder().url("compute").build();

        try {
            Link link = new Link.Builder(compute, "target")
                    .url("url")
                    .targetKind(InfrastructureKinds.COMPUTE)
                    .title("titleTest")
                    .build();


            assertThat(link.getId().toString()).contains("url");
            assertThat(link.getSource()).isEqualTo(compute);
            assertThat(link.getTarget().toString()).contains("target");
            assertThat(link.getTitle().get()).isEqualTo("titleTest");
            assertThat(link.getId().toString()).contains("url");
            assertThat(link.getTargetKind().get()).isEqualTo(InfrastructureKinds.COMPUTE);
        } catch (SyntaxException e) {
            e.printStackTrace();
        }
    }
}
