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

        //create minimal and maximal constructor


        List<Entity> entities = new ArrayList<>();
        List<Action> actions = new ArrayList<>();
        List<Mixin> depends = new ArrayList<>();
        List<Kind> applies = new ArrayList<>();
        HashSet<Attribute> attributes = new HashSet<>();


        Mixin mixin = new Mixin("http://schemas.ogf.org/occi/metamodel#mixin", "termTest", "titleTest",
                attributes, actions, depends, applies, entities);

        assertThat(mixin.getScheme()).matches("http://schemas.ogf.org/occi/metamodel#mixin");
        assertThat(mixin.getTerm()).matches("termTest");
        assertThat(mixin.getTitle()).matches("titleTest");
        assertThat(mixin.getEntities()).isEmpty();
        assertThat(mixin.getActions()).isEmpty();
        assertThat(mixin.getApplies()).isEmpty();
        assertThat(mixin.getDepends()).isEmpty();
        assertThat(mixin.getAttributes()).isNotEmpty();


        Attribute attribute = new Attribute.Builder("test").build();
        Mixin depend = new MixinBuilder("dependScheme", "dependTerm").build();

        attributes.add(attribute);
        depends.add(depend);
        applies.add(InfrastructureKinds.COMPUTE);

        Mixin mixin2 = new Mixin("http://schemas.ogf.org/occi/metamodel#mixin", "mixinTest", "mixinTest",
                attributes, actions, depends, applies, entities);

        assertThat(mixin2.getEntities()).isEmpty();
        assertThat(mixin2.getActions()).isEmpty();
        assertThat(mixin2.getApplies()).contains(InfrastructureKinds.COMPUTE);
        assertThat(mixin2.getDepends()).contains(depend);
        assertThat(mixin2.getAttributes()).contains(attribute);


    }

    @Test
    public void mixinBuilderTest() {
        Attribute attribute = new Attribute.Builder("attributeName").build();

        Mixin dependMixin = new MixinBuilder("dependScheme", "dependTerm").build();

        Mixin mixin = new MixinBuilder("schemeTest", "termTest")
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

        Mixin mixin2 = new MixinBuilder("schemeTest", "termTest")
                .build();

        assertThat(mixin2.getScheme()).matches("schemeTest");
        assertThat(mixin2.getTerm()).matches("termTest");
        assertThat(mixin2.getTitle()).matches("termTest");
        assertThat(mixin2.getAttributes()).isNotNull();
        assertThat(mixin2.getDepends()).isEmpty();
        assertThat(mixin2.getApplies()).isEmpty();
    }

    @Test
    public void getRenderingTest() {

        Attribute attribute = new Attribute.Builder("attributeName").build();

        Mixin dependMixin = new MixinBuilder("dependScheme", "dependTerm").build();


        Mixin mixin = new MixinBuilder("schemeTest", "termTest")
                .title("titleTest")
                .addAttribute(attribute)
                .addDepend(dependMixin)
                .addApply(InfrastructureKinds.COMPUTE)
                .build();

        MixinRendering rendering = mixin.getRendering();

        assertThat(rendering.getScheme()).matches("schemeTest");
        assertThat(rendering.getTerm()).matches("termTest");
        assertThat(rendering.getTitle()).matches("titleTest");
        assertThat(rendering.getLocation()).matches("/titleTest");
        assertThat(rendering.getActions()).isEmpty();
        assertThat(rendering.getAttributes()).containsEntry("attributeName", attribute.getRendering());
        assertThat(rendering.getDepends()).containsExactly(dependMixin.getTerm());
        assertThat(rendering.getApplies()).containsExactly(InfrastructureKinds.COMPUTE.getTerm());
    }
}
