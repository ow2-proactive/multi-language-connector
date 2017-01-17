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
package org.ow2.proactive.procci.service.transformer;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;
import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.exception.SyntaxException;
import org.ow2.proactive.procci.model.occi.platform.Status;
import org.ow2.proactive.procci.model.occi.platform.bigdata.Swarm;
import org.ow2.proactive.procci.model.occi.platform.bigdata.SwarmBuilder;
import org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes;
import org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataIdentifiers;
import org.ow2.proactive.procci.model.occi.platform.constants.PlatformAttributes;


public class SwarmTransformerTest {

    @Test
    public void toCloudAutomationModelTest() throws SyntaxException {
        Swarm swarm = new SwarmBuilder("hostIpTest", "masterIpTest").addAgentIp("agent1, agent2")
                                                                    .machineName("machineNameTest")
                                                                    .status("active")
                                                                    .title("titleTest")
                                                                    .build();

        Model model = new SwarmTransformer().toCloudAutomationModel(swarm, "create");
        assertThat(model.getServiceModel()).matches(BigDataIdentifiers.SWARM_MODEL);
        assertThat(model.getActionType()).matches("create");
        assertThat(model.getVariables().get(BigDataAttributes.HOST_IP_NAME)).matches("hostIpTest");
        assertThat(model.getVariables().get(BigDataAttributes.MASTER_IP_NAME)).matches("masterIpTest");
        assertThat(model.getVariables().get(BigDataAttributes.AGENTS_IP_NAME)).matches("agent1,agent2");
        assertThat(model.getVariables().get(BigDataAttributes.MACHINE_NAME_NAME)).matches("machineNameTest");
        assertThat(model.getVariables().get(BigDataAttributes.NETWORK_NAME_NAME)).isNull();
        assertThat(model.getVariables().get(PlatformAttributes.STATUS_NAME)).matches(Status.ACTIVE.name());
    }
}
