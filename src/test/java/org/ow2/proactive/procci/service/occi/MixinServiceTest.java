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
package org.ow2.proactive.procci.service.occi;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.CloudAutomationException;
import org.ow2.proactive.procci.model.occi.infrastructure.Compute;
import org.ow2.proactive.procci.model.occi.infrastructure.ComputeBuilder;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import org.ow2.proactive.procci.model.occi.metamodel.MixinBuilder;
import org.ow2.proactive.procci.service.CloudAutomationVariablesClient;
import org.ow2.proactive.procci.service.occi.MixinService;


/**
 * Created by the Activeeon team on 20/10/16.
 */
public class MixinServiceTest {

    @InjectMocks
    private MixinService mixinService;

    @Mock
    private CloudAutomationVariablesClient cloudAutomationVariablesClient;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getEntityMixinNamesTest() throws CloudAutomationException, IOException {
        Set<String> references = new HashSet<>();
        references.add("ref1Test");
        references.add("ref2Test");
        when(cloudAutomationVariablesClient.get("idTest")).thenReturn(new ObjectMapper().writeValueAsString(references));
        Set<String> result = mixinService.getEntityMixinNames("idTest");
        assertThat(result).contains("ref1Test");
        assertThat(result).contains("ref2Test");

        when(cloudAutomationVariablesClient.get("idTest2")).thenReturn("[]");
        references = mixinService.getEntityMixinNames("idTest2");
        assertThat(references).isEmpty();

        when(cloudAutomationVariablesClient.get("idTest3")).thenThrow(new CloudAutomationException("idTest3"));
        Exception ex = null;
        try {
            mixinService.getEntityMixinNames("idTest3");
        } catch (Exception e) {
            ex = e;
        }

        assertThat(ex).isInstanceOf(CloudAutomationException.class);
    }

    @Test
    public void addReferenceTest() throws ClientException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        Set<String> objectsId, references, newReferences, oldObjectId;

        //test update object and update mixin

        Set<String> mixinsId = new HashSet<>();
        mixinsId.add("mixinTest");

        Mixin mixin = new MixinBuilder("schemeTest", "mixinTest").build();

        Compute compute = new ComputeBuilder().url("idTest").addMixin(mixin).build();

        when(cloudAutomationVariablesClient.get("mixinTest")).thenReturn(mapper.writeValueAsString(mixin.getRendering()));

        mixinService.addEntity(compute);

        mixin.addEntity(compute);

        verify(cloudAutomationVariablesClient).post("idTest", mapper.writeValueAsString(mixinsId));
        verify(cloudAutomationVariablesClient).get("mixinTest");
        verify(cloudAutomationVariablesClient).update("mixinTest", mapper.writeValueAsString(mixin.getRendering()));

        //test add new object with new mixin

        Set<String> mixinId2 = new HashSet<>();
        mixinId2.add("mixinTest2");

        Mixin mixin2 = new MixinBuilder("schemeTest2", "mixinTest2").build();

        Compute compute2 = new ComputeBuilder().url("idTest2").addMixin(mixin2).build();

        mixin2.addEntity(compute2);

        when(cloudAutomationVariablesClient.get("idTest2")).thenReturn(mapper.writeValueAsString(mixin.getRendering()));

        Mockito.doThrow(new CloudAutomationException("mixinTest2"))
               .when(cloudAutomationVariablesClient)
               .get("mixinTest2");

        mixinService.addEntity(compute2);

        verify(cloudAutomationVariablesClient).post("idTest2", mapper.writeValueAsString(mixinId2));
        verify(cloudAutomationVariablesClient).get("mixinTest2");
        verify(cloudAutomationVariablesClient).post("mixinTest2", mapper.writeValueAsString(mixin2.getRendering()));

    }

}
