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
package org.ow2.proactive.procci.model.occi.metamodel.rendering;

import static com.google.common.truth.Truth.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;


/**
 * Created by the Activeeon team  on 10/10/16.
 */
public class MixinRenderingTest {

    @Test
    public void getJsonTest() {

        MixinRendering rendering = MixinRendering.builder().scheme("schemeTest").term("termTest").build();
        String renderingJson = MixinRendering.convertStringFromMixin(rendering);
        assertThat(renderingJson).isNotEmpty();
    }

    @Test
    public void convertMixinFromStringTest() {

        List<String> depends = new ArrayList<>();
        List<String> applies = new ArrayList<>();
        Map<String, AttributeRendering> attributes = new HashMap<>();

        AttributeRendering attributeRendering = new AttributeRendering();

        depends.add("dependMixin");
        applies.add("compute");
        attributes.put("attributeTest", attributeRendering);

        MixinRendering rendering = null;

        rendering = MixinRendering.convertMixinFromString(MixinRendering.convertStringFromMixin(MixinRendering.builder()
                                                                                                              .scheme("schemeTest")
                                                                                                              .term("termTest")
                                                                                                              .title("titleTest")
                                                                                                              .depends(depends)
                                                                                                              .applies(applies)
                                                                                                              .attributes(attributes)
                                                                                                              .build()));

        assertThat(rendering.getScheme()).matches("schemeTest");
        assertThat(rendering.getTerm()).matches("termTest");
        assertThat(rendering.getTitle()).matches("titleTest");
        assertThat(rendering.getDepends()).contains("dependMixin");
        assertThat(rendering.getApplies()).contains("compute");
        assertThat(rendering.getAttributes()).containsKey("attributeTest");

    }
}
