package org.ow2.proactive.procci.model.occi.platform.bigdata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.MissingAttributesException;
import org.ow2.proactive.procci.model.exception.SyntaxException;
import org.ow2.proactive.procci.model.occi.metamodel.Link;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.ResourceRendering;
import org.ow2.proactive.procci.model.occi.platform.Component;
import org.ow2.proactive.procci.model.occi.platform.Status;
import org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes;
import org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataIdentifiers;
import org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataKinds;
import org.ow2.proactive.procci.service.occi.MixinService;

public class SwarmBuilder extends Component.Builder {

    private Optional<String> hostIp;
    private Optional<String> masterIp;
    private Optional<String> agentsIp;
    private Optional<String> machineName;
    private Optional<String> networkName;

    public SwarmBuilder() {
        this.hostIp = Optional.empty();
        this.masterIp = Optional.empty();
        this.agentsIp = Optional.empty();
        this.machineName = Optional.empty();
        this.networkName = Optional.empty();
    }

    public SwarmBuilder(String hostIp, String masterIp) {
        this.hostIp = Optional.of(hostIp);
        this.masterIp = Optional.of(masterIp);
        this.agentsIp = Optional.empty();
        this.machineName = Optional.empty();
        this.networkName = Optional.empty();
    }

    public SwarmBuilder(String hostIp, String masterIp, String agentsIp, Optional<String> machineName,
            Optional<String> networkName) {
        this.hostIp = Optional.ofNullable(hostIp);
        this.masterIp = Optional.ofNullable(masterIp);
        this.agentsIp = Optional.ofNullable(agentsIp);
        this.machineName = machineName;
        this.networkName = networkName;

    }

    public SwarmBuilder(Model cloudautomation) throws ClientException {
        super(cloudautomation);

        Map<String, String> attributes = cloudautomation.getVariables();
        this.machineName = Optional.ofNullable(attributes.get(BigDataAttributes.MACHINE_NAME_NAME));
        this.networkName = Optional.ofNullable(attributes.get(BigDataAttributes.NETWORK_NAME_NAME));

        this.hostIp = Optional.ofNullable(attributes.get(BigDataAttributes.HOST_IP_NAME));
        this.masterIp = Optional.ofNullable(attributes.get(BigDataAttributes.MASTER_IP_NAME));
        this.agentsIp = Optional.ofNullable(attributes.get(BigDataAttributes.AGENTS_IP_NAME));
    }

    public SwarmBuilder(MixinService mixinService,
            ResourceRendering rendering) throws ClientException {
        super(mixinService, rendering);
        this.machineName = Optional.ofNullable(
                rendering.getAttributes().get(BigDataAttributes.MACHINE_NAME_NAME))
                .filter(machineName -> machineName instanceof String)
                .map(machineName -> (String) machineName);
        this.networkName = Optional.ofNullable(
                rendering.getAttributes().get(BigDataAttributes.NETWORK_NAME_NAME))
                .filter(networkName -> networkName instanceof String)
                .map(networkName -> (String) networkName);
        this.hostIp = Optional.ofNullable(rendering.getAttributes().get(BigDataAttributes.HOST_IP_NAME))
                .filter(hostIp -> hostIp instanceof String)
                .map(hostIp -> (String) hostIp);
        this.masterIp = Optional.ofNullable(rendering.getAttributes().get(BigDataAttributes.MASTER_IP_NAME))
                .filter(masterIp -> masterIp instanceof String)
                .map(masterIp -> (String) masterIp);
        this.agentsIp = Optional.ofNullable(rendering.getAttributes().get(BigDataAttributes.AGENTS_IP_NAME))
                .filter(agentIp -> agentIp instanceof String)
                .map(agentIp -> (String) agentIp);
    }

    public SwarmBuilder machineName(String machineName) {
        this.machineName = Optional.ofNullable(machineName);
        return this;
    }

    public SwarmBuilder networkName(String networkName) {
        this.networkName = Optional.ofNullable(networkName);
        return this;
    }

    public SwarmBuilder addAgentIp(String agentIp) {
        this.agentsIp = Optional.ofNullable(agentIp);
        return this;
    }

    @Override
    public SwarmBuilder status(String status) throws SyntaxException {
        this.status = Optional.ofNullable(Status.getStatusFromString(status));
        return this;
    }

    @Override
    public SwarmBuilder url(String url) {
        this.url = Optional.ofNullable(url);
        return this;
    }

    @Override
    public SwarmBuilder title(String title) {
        this.title = Optional.ofNullable(title);
        return this;
    }

    @Override
    public SwarmBuilder addMixin(Mixin mixin) {
        this.mixins.add(mixin);
        return this;
    }

    @Override
    public SwarmBuilder addMixins(List<Mixin> mixins) {
        this.mixins.addAll(mixins);
        return this;
    }

    @Override
    public SwarmBuilder summary(String summary) {
        this.summary = Optional.ofNullable(summary);
        return this;
    }

    @Override
    public SwarmBuilder addLink(Link link) {
        this.links.add(link);
        return this;
    }

    @Override
    public Swarm build() throws ClientException {
        return new Swarm(this.getUrl(), BigDataKinds.SWARM, this.getTitle(), this.getMixins(),
                this.getSummary(), this.getLinks(),
                status,
                machineName,
                hostIp.orElseThrow(() -> new MissingAttributesException(BigDataAttributes.HOST_IP_NAME,
                        BigDataKinds.SWARM.getTitle())),
                masterIp.orElseThrow(() -> new MissingAttributesException(BigDataAttributes.MASTER_IP_NAME,
                        BigDataKinds.SWARM.getTitle())),
                getAgentsIpFromString(this.agentsIp),
                networkName);
    }

    private List<String> getAgentsIpFromString(Optional<String> agentsIpString) {
        return agentsIpString
                .map(ip -> ip.replaceAll(" ", ""))
                .map(ip -> ip.split(BigDataIdentifiers.AGENT_IP_SEPARATOR))
                .map(ip -> Arrays.asList(ip))
                .orElse(new ArrayList<>());
    }


}
