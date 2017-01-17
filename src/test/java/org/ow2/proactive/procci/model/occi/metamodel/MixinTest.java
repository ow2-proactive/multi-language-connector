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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mock;
import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.MissingAttributesException;
import org.ow2.proactive.procci.model.exception.SyntaxException;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureIdentifiers;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.AttributeRendering;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.MixinRendering;
import org.ow2.proactive.procci.service.occi.InstanceService;
import org.ow2.proactive.procci.service.occi.MixinService;


/**
 * Created by the Activeeon team  on 2/25/16.
 */
public class MixinTest {

    @Mock
    InstanceService instanceService;

    @Mock
    private MixinService mixinService;

    @Test
    public void constructorTest() {

        //create minimal and maximal constructor

        List<Entity> entities = new ArrayList<>();
        List<Action> actions = new ArrayList<>();
        List<Mixin> depends = new ArrayList<>();
        List<Kind> applies = new ArrayList<>();
        HashSet<Attribute> attributes = new HashSet<>();

        Mixin mixin = new Mixin("http://schemas.ogf.org/occi/metamodel#mixin",
                                "termTest",
                                "titleTest",
                                attributes,
                                actions,
                                depends,
                                applies,
                                entities);

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

        Mixin mixin2 = new Mixin("http://schemas.ogf.org/occi/metamodel#mixin",
                                 "mixinTest",
                                 "mixinTest",
                                 attributes,
                                 actions,
                                 depends,
                                 applies,
                                 entities);

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

        //maximum builder configuration

        Mixin mixin = new MixinBuilder("schemeTest", "termTest").title("titleTest")
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

        //minimum builder configuration

        Mixin mixin2 = new MixinBuilder("schemeTest2", "termTest2").build();

        assertThat(mixin2.getScheme()).matches("schemeTest2");
        assertThat(mixin2.getTerm()).matches("termTest2");
        assertThat(mixin2.getTitle()).matches("termTest2");
        assertThat(mixin2.getAttributes()).isNotNull();
        assertThat(mixin2.getDepends()).isEmpty();
        assertThat(mixin2.getApplies()).isEmpty();

        //appliying attributes
        MixinBuilder mixinBuilder = new MixinBuilder("schemeTest3", "termTest3");
        Map attributes = new HashMap();
        attributes.put("occi.category.title", "titleTest3");
        try {
            mixinBuilder.attributes(attributes);
        } catch (ClientException ex) {
            ex.printStackTrace();
        }

        Mixin mixin3 = mixinBuilder.build();

        assertThat(mixin3.getScheme()).matches("schemeTest3");
        assertThat(mixin3.getTerm()).matches("termTest3");
        assertThat(mixin3.getTitle()).matches("titleTest3");

    }

    @Test
    public void renderingBuilderTest() throws ClientException, IOException {

        try {
            new MixinBuilder(mixinService, instanceService, MixinRendering.builder().build()).build();
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(MissingAttributesException.class);
        }

        try {
            List<String> applies = new ArrayList<>();
            applies.add("notAKnownTerm");
            new MixinBuilder(mixinService,
                             instanceService,
                             MixinRendering.builder()
                                           .scheme("schemeTest")
                                           .term("termTest")
                                           .applies(applies)
                                           .build()).build();
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(SyntaxException.class);
        }

        Mixin minRendering = new MixinBuilder(mixinService,
                                              instanceService,

                                              MixinRendering.builder()
                                                            .scheme("schemeTest")
                                                            .term("termTest")
                                                            .build()).build();

        Mixin allAttributesRendering = new MixinBuilder(mixinService,
                                                        instanceService,
                                                        MixinRendering.builder()
                                                                      .scheme("schemeTest")
                                                                      .term("termTest")
                                                                      .title("titleTest")
                                                                      .actions(new ArrayList<>())
                                                                      .applies(new ArrayList<>())
                                                                      .attributes(new HashMap<>())
                                                                      .depends(new ArrayList<>())
                                                                      .location("locationTest")
                                                                      .build()).build();

        List actions = new ArrayList();
        actions.add("actionTest");
        List applies = new ArrayList();
        applies.add(InfrastructureIdentifiers.INFRASTRUCTURE_SCHEME + "compute");
        Map attributes = new HashMap();
        AttributeRendering attributeRendering = new AttributeRendering();
        attributes.put("attributesName", attributeRendering);
        List depend = new ArrayList();
        //cannot test depend because it will send service to cloud-automation-service

        Mixin allAttributesFilledRendering = new MixinBuilder(mixinService,
                                                              instanceService,
                                                              MixinRendering.builder()
                                                                            .scheme("schemeTest")
                                                                            .term("termTest")
                                                                            .title("titleTest")
                                                                            .actions(actions)
                                                                            .applies(applies)
                                                                            .attributes(attributes)
                                                                            .depends(depend)
                                                                            .location("locationTest")
                                                                            .build()).build();

        assertThat(minRendering.getScheme()).matches("schemeTest");
        assertThat(minRendering.getTerm()).matches("termTest");
        assertThat(minRendering.getTitle()).matches("termTest");
        assertThat(minRendering.getAttributes()).isNotNull();
        assertThat(minRendering.getActions()).isEmpty();
        assertThat(minRendering.getDepends()).isEmpty();
        assertThat(minRendering.getApplies()).isEmpty();

        assertThat(allAttributesRendering.getScheme()).matches("schemeTest");
        assertThat(allAttributesRendering.getTerm()).matches("termTest");
        assertThat(allAttributesRendering.getTitle()).matches("titleTest");
        assertThat(allAttributesRendering.getAttributes()).isNotNull();
        assertThat(allAttributesRendering.getDepends().isEmpty());
        assertThat(allAttributesRendering.getActions().isEmpty());
        assertThat(allAttributesRendering.getApplies().isEmpty());

        assertThat(allAttributesFilledRendering.getScheme()).matches("schemeTest");
        assertThat(allAttributesFilledRendering.getTerm()).matches("termTest");
        assertThat(allAttributesFilledRendering.getTitle()).matches("titleTest");
        assertThat(allAttributesFilledRendering.getAttributes()).hasSize(allAttributesRendering.getAttributes().size() +
                                                                         1);
        assertThat(allAttributesFilledRendering.getDepends().isEmpty());
        assertThat(allAttributesFilledRendering.getActions().isEmpty());
        assertThat(allAttributesFilledRendering.getApplies().contains(InfrastructureKinds.COMPUTE));

    }

    @Test
    public void getRenderingTest() {

        Attribute attribute = new Attribute.Builder("attributeName").build();

        Mixin dependMixin = new MixinBuilder("dependScheme", "dependTerm").build();

        Mixin mixin = new MixinBuilder("schemeTest", "termTest").title("titleTest")
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
        assertThat(rendering.getApplies()).containsExactly(InfrastructureIdentifiers.INFRASTRUCTURE_SCHEME +
                                                           InfrastructureIdentifiers.COMPUTE);
    }
}
