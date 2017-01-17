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
package org.ow2.proactive.procci.model.occi.infrastructure;

import static com.google.common.truth.Truth.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ow2.proactive.procci.model.exception.CloudAutomationServerException;
import org.ow2.proactive.procci.model.exception.SyntaxException;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.occi.infrastructure.state.NetworkState;
import org.ow2.proactive.procci.service.CloudAutomationVariablesClient;
import org.ow2.proactive.procci.service.occi.MixinService;

import com.google.common.truth.Truth;


/**
 * Created by the Activeeon team on 2/25/16.
 */

public class NetworkInterfaceTest {

    @Mock
    private CloudAutomationVariablesClient cloudAutomationVariablesClient;

    @Mock
    private MixinService mixinService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void ConstructorTest() throws IOException, CloudAutomationServerException {
        Compute compute = new ComputeBuilder().url("url:compute").build();

        try {
            NetworkInterface networkInterface = new NetworkInterface.Builder(compute,
                                                                             "url:target",
                                                                             "mac",
                                                                             "linktarget").url("url:networkinterface")
                                                                                          .title("titleTest")
                                                                                          .state(NetworkState.ACTIVE)
                                                                                          .targetKind(InfrastructureKinds.STORAGE)
                                                                                          .build();

            Truth.assertThat(networkInterface.getTitle().get()).isEqualTo("titleTest");
            Truth.assertThat(networkInterface.getState()).isEquivalentAccordingToCompareTo(NetworkState.ACTIVE);
            Truth.assertThat(networkInterface.getTarget().toString()).isEqualTo("url:target");
            assertThat(networkInterface.getMac()).isEqualTo("mac");
            Truth.assertThat(networkInterface.getSource()).isEqualTo(compute);
            Truth.assertThat(networkInterface.getId().toString()).contains("url:networkinterface");
            assertThat(networkInterface.getLinkInterface()).isEqualTo("linktarget");
        } catch (SyntaxException e) {
            e.printStackTrace();
        }
    }
}
