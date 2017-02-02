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
import static org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureIdentifiers.VM_IMAGE;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.ow2.proactive.procci.model.exception.UnknownAttributeException;
import org.ow2.proactive.procci.model.occi.infrastructure.Compute;
import org.ow2.proactive.procci.service.occi.MixinService;


public class ResourceRenderingTest {

    private MixinService mixinService = new MixinService();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void checkAttributesFailTest() {

        UnknownAttributeException e = null;

        ResourceRendering computeRendering = new ResourceRendering.Builder("http://schemas.ogf.org/occi/infrastructure#compute",
                                                                           "urn:uuid:996ad860−2a9a−504f−886−aeafd0b2ae29").addAttribute("occi.compute.speed",
                                                                                                                                        2)
                                                                                                                          .addAttribute("occi.compute.memory",
                                                                                                                                        4.0)
                                                                                                                          .addAttribute("occi.compute.cores",
                                                                                                                                        2)
                                                                                                                          .addAttribute("occi.compute.hostname",
                                                                                                                                        "80.200.35.140")
                                                                                                                          .addAttribute("occi.entity.title",
                                                                                                                                        "titleTest")
                                                                                                                          .addAttribute("occi.compute.architecture",
                                                                                                                                        "x86")
                                                                                                                          .addAttribute("occi.compute.state",
                                                                                                                                        "ACTIVE")
                                                                                                                          .addAttribute("occi.core.summary",
                                                                                                                                        "summaryTest")
                                                                                                                          .addAttribute("falseAttribute",
                                                                                                                                        "nothing")
                                                                                                                          .build();
        try {
            computeRendering.checkAttributes(Compute.getAttributes(), "Compute", mixinService);
        } catch (UnknownAttributeException ex) {
            e = ex;
        }

        assertThat(e).isNotNull();
        assertThat(e.getUnknownAttribute()).contains("occi.compute.speed");
        assertThat(e.getUnknownAttribute()).contains("falseAttribute");
    }

    @Test
    public void checkAttributesTest() {

        Map attributes = new HashMap();
        attributes.put("occi.compute.userdata", "userdataTest");
        attributes.put("occi.category.title", "titleTest");

        Map vmImageAttribute = new HashMap();
        vmImageAttribute.put("imagename", "imageId");

        ResourceRendering computeRendering = new ResourceRendering.Builder("http://schemas.ogf.org/occi/infrastructure#compute",
                                                                           "urn:uuid:996ad860−2a9a−504f−886−aeafd0b2ae29").addAttribute("occi.entity.title",
                                                                                                                                        "titleTest")
                                                                                                                          .addAttribute(VM_IMAGE,
                                                                                                                                        vmImageAttribute)
                                                                                                                          .build();

        Exception exception = null;

        try {
            computeRendering.checkAttributes(Compute.getAttributes(), "Compute", mixinService);
        } catch (Exception e) {
            e.printStackTrace();
            exception = e;
        }
        assertThat(exception).isNull();
    }
}
