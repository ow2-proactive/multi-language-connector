package org.ow2.proactive.procci.model.occi.platform.bigdata;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.occi.metamodel.Attribute;
import org.ow2.proactive.procci.model.occi.metamodel.Kind;
import org.ow2.proactive.procci.model.occi.metamodel.Link;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import org.ow2.proactive.procci.model.occi.metamodel.constants.Attributes;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.ResourceRendering;
import org.ow2.proactive.procci.model.occi.platform.Component;
import org.ow2.proactive.procci.model.occi.platform.Status;
import org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataIdentifiers;
import org.ow2.proactive.procci.model.occi.platform.constants.PlatformAttributes;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static org.ow2.proactive.procci.model.occi.metamodel.constants.Attributes.ENTITY_TITLE_NAME;
import static org.ow2.proactive.procci.model.occi.metamodel.constants.Attributes.SUMMARY_NAME;
import static org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes.AGENTS_IP;
import static org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes.AGENTS_IP_NAME;
import static org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes.HOST_IP;
import static org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes.HOST_IP_NAME;
import static org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes.MACHINE_NAME;
import static org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes.MACHINE_NAME_NAME;
import static org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes.MASTER_IP;
import static org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes.MASTER_IP_NAME;
import static org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes.NETWORK_NAME;
import static org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes.NETWORK_NAME_NAME;
import static org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataIdentifiers.SWARM_MODEL;
import static org.ow2.proactive.procci.model.occi.platform.constants.PlatformAttributes.STATUS_NAME;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Swarm extends Component {

    private final String hostIp;
    private final String masterIp;
    private final List<String> agentsIp;
    private Optional<String> machineName;
    private Optional<String> networkName;

    public Swarm(Optional<String> url,
            Kind kind,
            Optional<String> title,
            List<Mixin> mixins,
            Optional<String> summary,
            List<Link> links,
            Optional<Status> status,
            Optional<String> machineName,
            String hostIp,
            String masterIp,
            List<String> agentsIp,
            Optional<String> networkName
    ) {
        super(url, kind, title, mixins, summary, links, status);
        this.machineName = machineName;
        this.hostIp = hostIp;
        this.masterIp = masterIp;
        this.agentsIp = agentsIp;
        this.networkName = networkName;
    }

    public static Set<Attribute> createAttributeSet() {
        Set<Attribute> attributeSet = new HashSet<>();
        attributeSet.addAll(Component.createAttributeSet());
        attributeSet.add(MACHINE_NAME);
        attributeSet.add(HOST_IP);
        attributeSet.add(MASTER_IP);
        attributeSet.add(AGENTS_IP);
        attributeSet.add(NETWORK_NAME);
        return attributeSet;
    }

    /**
     * Convert OCCI swarm to Proactive Cloud Automation Compute
     *
     * @param actionType is the action to apply on the swarm
     * @return the proactive cloud automation model for the swarm
     */
    public Model toCloudAutomationModel(String actionType) {

        Model.Builder serviceBuilder = new Model.Builder(SWARM_MODEL, actionType)
                .addVariable(HOST_IP_NAME, this.getHostIp())
                .addVariable(MASTER_IP_NAME, this.getMasterIp())
                .addVariable(AGENTS_IP_NAME, getAgentsIpAsString());

        this.machineName.ifPresent(machineName -> serviceBuilder.addVariable(MACHINE_NAME_NAME, machineName));
        this.networkName.ifPresent(networkName -> serviceBuilder.addVariable(NETWORK_NAME_NAME, networkName));
        this.getStatus().ifPresent(
                status -> serviceBuilder.addVariable(PlatformAttributes.STATUS_NAME, status));
        this.getTitle().ifPresent(title -> serviceBuilder.addVariable(Attributes.ENTITY_TITLE_NAME, title));
        this.getSummary().ifPresent(summary -> serviceBuilder.addVariable(Attributes.SUMMARY_NAME, summary));

        this.getMixins().forEach(mixin -> mixin.toCloudAutomationModel(serviceBuilder));

        return serviceBuilder.build();
    }

    /**
     * Concatenate the ip contained in the agents ip list
     *
     * @return the ip separated by the AGENT_IP_SEPARATOR
     */
    private String getAgentsIpAsString() {
        String agentsIp;
        if (this.agentsIp.isEmpty()) {
            return "";
        }
        agentsIp = this.agentsIp.get(0);
        for (int i = 1; i < this.agentsIp.size(); i++) {
            agentsIp += BigDataIdentifiers.AGENT_IP_SEPARATOR + this.agentsIp.get(i);
        }
        return agentsIp;
    }

    /**
     * Give the OCCI rendering of a swarm
     *
     * @return the swarm rendering
     */
    public ResourceRendering getRendering() {

        ResourceRendering.Builder resourceRendering = new ResourceRendering.Builder(this.getKind().getTitle(),
                this.getRenderingId());
        this.getTitle().ifPresent(title -> resourceRendering.addAttribute(ENTITY_TITLE_NAME, title));
        this.getSummary().ifPresent(summary -> resourceRendering.addAttribute(SUMMARY_NAME, summary));
        this.getStatus().ifPresent(status -> resourceRendering.addAttribute(STATUS_NAME, status.name()));
        this.machineName.ifPresent(name -> resourceRendering.addAttribute(MACHINE_NAME_NAME, name));
        resourceRendering.addAttribute(HOST_IP_NAME, hostIp);
        resourceRendering.addAttribute(MASTER_IP_NAME, masterIp);
        resourceRendering.addAttribute(AGENTS_IP_NAME, getAgentsIpAsString());
        networkName.ifPresent(name -> resourceRendering.addAttribute(NETWORK_NAME_NAME, name));

        this.getMixins().forEach(mixin -> resourceRendering.addMixin(mixin.getTitle()));

        return resourceRendering.build();
    }

}
