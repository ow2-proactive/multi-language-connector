package org.ow2.proactive.papi.model.metamodel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.ow2.proactive.papi.model.infrastructure.Compute;
import org.ow2.proactive.papi.model.metamodel.constants.Kinds;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 2/25/16.
 */
public class ResourceTest {


    @Test
    public void constructorTest() {

        Kind kind = Kinds.RESOURCE;
        List<Mixin> mixins = new ArrayList<>();
        List<Link> links = new ArrayList<>();
        mixins.add(new Mixin("http://schemas.ogf.org/occi/metamodel#mixin", "mixin", "mixin",
                new HashSet<Attribute>(),
                new ArrayList<Action>(), new ArrayList<Mixin>(), new ArrayList<Kind>(),
                new ArrayList<Entity>()));
        Resource resource = new Resource("url", kind, "titleTest", mixins, "summaryTest", links);

        assertThat(resource.getSummary()).isEqualTo("summaryTest");
        assertThat(resource.getLinks()).isEqualTo(links);
        assertThat(resource.getMixins()).isEqualTo(mixins);
        assertThat(resource.getKind()).isEqualTo(kind);
        assertThat(resource.getId().toString()).startsWith("urn:url:");
    }

    @Test
    public void builderTest() {
        Compute compute = new Compute.Builder("compute").build();
        Link link = new Link.Builder("link", compute, "target").build();
        Resource resource = new Resource.Builder("resource")
                .summary("summary")
                .title("title")
                .addLink(link)
                .build();
        assertThat(resource.getSummary()).isEqualTo("summary");
        assertThat(resource.getTitle()).isEqualTo("title");
        assertThat(resource.getId().toString()).contains("resource");
        assertThat(resource.getLinks()).containsExactly(link);
        assertThat(resource.getMixins()).isEmpty();
    }
}
