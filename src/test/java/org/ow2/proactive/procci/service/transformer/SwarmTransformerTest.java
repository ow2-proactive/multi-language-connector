package org.ow2.proactive.procci.service.transformer;

import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.exception.SyntaxException;
import org.ow2.proactive.procci.model.occi.platform.Status;
import org.ow2.proactive.procci.model.occi.platform.bigdata.Swarm;
import org.ow2.proactive.procci.model.occi.platform.bigdata.SwarmBuilder;
import org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes;
import org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataIdentifiers;
import org.ow2.proactive.procci.model.occi.platform.constants.PlatformAttributes;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class SwarmTransformerTest {

    @Test
    public void toCloudAutomationModelTest() throws SyntaxException {
        Swarm swarm = new SwarmBuilder("hostIpTest", "masterIpTest")
                .addAgentIp("agent1")
                .addAgentIp("agent2")
                .machineName("machineNameTest")
                .status("active")
                .title("titleTest")
                .build();

        Model model = new SwarmTransformer().toCloudAutomationModel(swarm,"create");
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
