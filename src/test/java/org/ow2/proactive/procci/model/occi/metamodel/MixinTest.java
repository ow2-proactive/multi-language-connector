package org.ow2.proactive.procci.model.occi.metamodel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.MixinRendering;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 2/25/16.
 */
public class MixinTest {

    @Test
    public void maximalConstructorTest() {

        Attribute attribute = new Attribute.Builder("test").type(Type.HASH).mutable(false).required(
                false).build();
        List<Entity> entities = new ArrayList<>();
        List<Action> actions = new ArrayList<>();
        List<Mixin> depends = new ArrayList<>();
        List<Kind> applies = new ArrayList<>();
        HashSet<Attribute> attributes = new HashSet<>();
        attributes.add(attribute);

        Mixin mixin = new Mixin("http://schemas.ogf.org/occi/metamodel#mixin", "mixinTest", "mixinTest",
                attributes, actions, depends, applies, entities);

        assertThat(mixin.getScheme().toString()).isEqualTo("http://schemas.ogf.org/occi/metamodel#mixin");
        assertThat(mixin.getEntities()).isEqualTo(entities);
        assertThat(mixin.getActions()).isEqualTo(actions);
        assertThat(mixin.getApplies()).isEqualTo(applies);
        assertThat(mixin.getDepends()).isEqualTo(depends);
        assertThat(mixin.getAttributes()).contains(attribute);
    }

    @Test
    public void mixinBuilderTest() {
        Attribute attribute = new Attribute.Builder("attributeName").build();

        Mixin dependMixin = new MixinBuilder().build();


        Mixin mixin = new MixinBuilder()
                .scheme("schemeTest")
                .term("termTest")
                .title("titleTest")
                .addAttribute(attribute)
                .addDepend(dependMixin)
                .addApply(InfrastructureKinds.COMPUTE)
                .build();

        assertThat(mixin.getScheme()).matches("schemeTest");
        assertThat(mixin.getTerm()).matches("termTest");
        assertThat(mixin.getTitle()).matches("titleTest");
        assertThat(mixin.getAttributes()).contains(attribute);
        assertThat(mixin.getDepends()).containsExactly(dependMixin);
        assertThat(mixin.getApplies()).containsExactly(InfrastructureKinds.COMPUTE);
    }

    @Test
    public void getRenderingTest() {

        Attribute attribute = new Attribute.Builder("attributeName").build();

        Mixin dependMixin = new MixinBuilder().build();


        Mixin mixin = new MixinBuilder()
                .scheme("schemeTest")
                .term("termTest")
                .title("titleTest")
                .addAttribute(attribute)
                .addDepend(dependMixin)
                .addApply(InfrastructureKinds.COMPUTE)
                .build();

        MixinRendering rendering = mixin.getRendering();

        assertThat(rendering.getScheme()).matches("schemeTest");
        assertThat(rendering.getTerm()).matches("termTest");
        assertThat(rendering.getTitle()).matches("titleTest");
        assertThat(rendering.getLocation()).matches("/");
        assertThat(rendering.getActions()).isEmpty();
        assertThat(rendering.getAttributes()).containsEntry("attributeName", attribute.getRendering());
        assertThat(rendering.getDepends()).containsExactly(dependMixin.getTerm());
        assertThat(rendering.getApplies()).containsExactly(InfrastructureKinds.COMPUTE.getTerm());

    }
}
