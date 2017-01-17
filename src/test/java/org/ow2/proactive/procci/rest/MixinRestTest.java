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
package org.ow2.proactive.procci.rest;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.CloudAutomationException;
import org.ow2.proactive.procci.model.exception.ServerException;
import org.ow2.proactive.procci.model.occi.metamodel.MixinBuilder;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.MixinRendering;
import org.ow2.proactive.procci.service.CloudAutomationVariablesClient;
import org.ow2.proactive.procci.service.occi.MixinService;
import org.springframework.http.ResponseEntity;


/**
 * Created by the Activeeon team  on 14/10/16.
 */
public class MixinRestTest {

    @InjectMocks
    private MixinRest mixinRest;

    @Mock
    private CloudAutomationVariablesClient cloudAutomationVariables;

    @Mock
    private MixinService mixinService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getMixinTest() throws ClientException, IOException {
        when(mixinService.getMixinByTitle("titleTest")).thenReturn(new MixinBuilder("schemeTest", "termTest")
                                                                                                             .title("titleTest")
                                                                                                             .build());
        ResponseEntity<MixinRendering> response = mixinRest.getMixin("titleTest");
        assertThat(response.getBody().getScheme()).matches("schemeTest");
        assertThat(response.getBody().getTerm()).matches("termTest");
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        when(mixinService.getMixinByTitle("titleTest2")).thenThrow(new CloudAutomationException("titleTest2"));
        ResponseEntity<MixinRendering> responseClientError = mixinRest.getMixin("titleTest2");
        assertThat(responseClientError.getStatusCode().is4xxClientError()).isTrue();

        when(mixinService.getMixinByTitle("titleTest3")).thenThrow(ServerException.class);
        ResponseEntity<MixinRendering> responseServerError = mixinRest.getMixin("titleTest3");
        assertThat(responseServerError.getStatusCode().is5xxServerError()).isTrue();
    }

    @Test
    public void postMixinTest() throws ClientException, IOException {
        MixinRendering mixinRendering = new MixinBuilder("schemeTest", "termTest").build().getRendering();
        ResponseEntity<MixinRendering> response = mixinRest.createMixin(mixinRendering);
        assertThat(response.getBody().getScheme()).matches("schemeTest");
        assertThat(response.getBody().getTerm()).matches("termTest");
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    public void updateMixinTest() throws ClientException, IOException {
        MixinRendering mixinRendering = new MixinBuilder("schemeTest", "termTest").build().getRendering();
        ResponseEntity<MixinRendering> response = mixinRest.updateMixin("termTest", mixinRendering);
        assertThat(response.getBody().getScheme()).matches("schemeTest");
        assertThat(response.getBody().getTerm()).matches("termTest");
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        ResponseEntity<MixinRendering> response2 = mixinRest.updateMixin("anotherTermTest", mixinRendering);
        assertThat(response2.getBody().getScheme()).matches("schemeTest");
        assertThat(response2.getBody().getTerm()).matches("termTest");
        assertThat(response2.getStatusCode().is2xxSuccessful()).isTrue();
    }

}
