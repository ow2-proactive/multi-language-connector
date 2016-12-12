package org.ow2.proactive.procci.model.occi.platform.bigdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.occi.metamodel.constants.Attributes;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.ResourceRendering;
import org.ow2.proactive.procci.model.occi.platform.Status;
import org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes;
import org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataIdentifiers;
import org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataKinds;
import org.ow2.proactive.procci.model.occi.platform.constants.PlatformAttributes;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class SwarmTest {

    @Test
    public void requiredConstructorTest() throws ClientException {
        Swarm swarm = new SwarmBuilder("hostIpTest", "masterIpTest").build();
        assertThat(swarm.getHostIp()).matches("hostIpTest");
        assertThat(swarm.getMasterIp()).matches("masterIpTest");
        assertThat(swarm.getId()).isNotNull();
        assertThat(swarm.getKind()).isEqualTo(BigDataKinds.SWARM);
        assertThat(swarm.getAgentsIp()).isEmpty();
        assertThat(swarm.getStatus().isPresent()).isFalse();
        assertThat(swarm.getMachineName().isPresent()).isFalse();
        assertThat(swarm.getNetworkName().isPresent()).isFalse();
    }

    @Test
    public void allArgsConstructortest() throws ClientException {

        List<String> agents = new ArrayList<>();
        agents.add("agent1");
        agents.add("agent2");
        Swarm swarm = new SwarmBuilder("hostIpTest2", "masterIpTest2", agents, Optional.of("machineNameTest"),
                Optional.of("networkNameTest"))
                .status("active")
                .title("titleTest")
                .build();

        assertThat(swarm.getHostIp()).matches("hostIpTest2");
        assertThat(swarm.getMasterIp()).matches("masterIpTest2");
        assertThat(swarm.getAgentsIp()).containsExactly("agent1", "agent2");
        assertThat(swarm.getStatus().get()).isEqualTo(Status.ACTIVE);
        assertThat(swarm.getMachineName().get()).matches("machineNameTest");
        assertThat(swarm.getNetworkName().get()).matches("networkNameTest");
    }

    @Test
    public void cloudAutomationModelConstructorTest() throws ClientException {
        Model model = new Model.Builder(BigDataIdentifiers.SWARM_MODEL, "create")
                .addVariable(BigDataAttributes.AGENTS_IP_NAME, "agentIp1, agentIp2")
                .addVariable(BigDataAttributes.HOST_IP_NAME, "hostIpTest3")
                .addVariable(BigDataAttributes.MASTER_IP_NAME, "masterIpTest3")
                .addVariable(BigDataAttributes.NETWORK_NAME_NAME, "my-net")
                .addVariable(PlatformAttributes.STATUS_NAME, "inactive")
                .addVariable(Attributes.ENTITY_TITLE_NAME, "title")
                .build();

        Swarm swarm = new SwarmBuilder(model).build();
        assertThat(swarm.getHostIp()).matches("hostIpTest3");
        assertThat(swarm.getMasterIp()).matches("masterIpTest3");
        assertThat(swarm.getAgentsIp()).containsExactly("agentIp1", "agentIp2");
        assertThat(swarm.getStatus().get()).isEqualTo(Status.INACTIVE);
        assertThat(swarm.getMachineName().isPresent()).isFalse();
        assertThat(swarm.getNetworkName().get()).matches("my-net");
    }

    @Test
    public void toCloudAutomationModelTest() throws ClientException {
        Swarm swarm = new SwarmBuilder("hostIpTest", "masterIpTest")
                .addAgentIp("agent1")
                .addAgentIp("agent2")
                .machineName("machineNameTest")
                .status("active")
                .title("titleTest")
                .build();

        Model model = swarm.toCloudAutomationModel("create");
        assertThat(model.getServiceModel()).matches(BigDataIdentifiers.SWARM_MODEL);
        assertThat(model.getActionType()).matches("create");
        assertThat(model.getVariables().get(BigDataAttributes.HOST_IP_NAME)).matches("hostIpTest");
        assertThat(model.getVariables().get(BigDataAttributes.MASTER_IP_NAME)).matches("masterIpTest");
        assertThat(model.getVariables().get(BigDataAttributes.AGENTS_IP_NAME)).matches("agent1,agent2");
        assertThat(model.getVariables().get(BigDataAttributes.MACHINE_NAME_NAME)).matches("machineNameTest");
        assertThat(model.getVariables().get(BigDataAttributes.NETWORK_NAME_NAME)).isNull();
        assertThat(model.getVariables().get(PlatformAttributes.STATUS_NAME)).matches(Status.ACTIVE.name());
    }

    @Test
    public void getRenderingTest() throws ClientException {
        Swarm swarm = new SwarmBuilder("hostIpTest", "masterIpTest")
                .addAgentIp("agent1")
                .addAgentIp("agent2")
                .machineName("machineNameTest")
                .status("active")
                .title("titleTest")
                .build();

        ResourceRendering rendering = swarm.getRendering();
        assertThat(rendering.getLinks()).isEmpty();
        assertThat(rendering.getMixins()).isEmpty();
        assertThat(rendering.getAttributes()).containsEntry(BigDataAttributes.AGENTS_IP_NAME,
                "agent1,agent2");
        assertThat(rendering.getAttributes()).containsEntry(BigDataAttributes.HOST_IP_NAME, "hostIpTest");
        assertThat(rendering.getAttributes()).containsEntry(BigDataAttributes.MASTER_IP_NAME, "masterIpTest");
        assertThat(rendering.getAttributes()).containsEntry(BigDataAttributes.MACHINE_NAME_NAME,
                "machineNameTest");
        assertThat(rendering.getAttributes()).containsEntry(PlatformAttributes.STATUS_NAME,
                Status.ACTIVE.name());
        assertThat(rendering.getAttributes()).containsEntry(Attributes.ENTITY_TITLE_NAME, "titleTest");

        assertThat(rendering.getId()).isNotNull();
        assertThat(rendering.getKind()).matches(BigDataKinds.SWARM.getTitle());

    }
}
