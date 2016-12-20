package org.ow2.proactive.procci.service.transformer;

import org.ow2.proactive.procci.model.InstanceModel;
import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.occi.metamodel.constants.Attributes;
import org.ow2.proactive.procci.model.occi.platform.bigdata.Swarm;
import org.ow2.proactive.procci.model.occi.platform.constants.PlatformAttributes;

import org.springframework.stereotype.Component;

import static org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes.AGENTS_IP_NAME;
import static org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes.HOST_IP_NAME;
import static org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes.MACHINE_NAME_NAME;
import static org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes.MASTER_IP_NAME;
import static org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes.NETWORK_NAME_NAME;
import static org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataIdentifiers.SWARM_MODEL;

@Component
public class SwarmTransformer extends TransformerProvider {

    public TransformerType getType(){
        return TransformerType.SWARM;
    }

    /**
     * Convert OCCI swarm to Proactive Cloud Automation Compute
     *
     * @param actionType is the action to apply on the swarm
     * @return the proactive cloud automation model for the swarm
     */
    @Override
    public Model toCloudAutomationModel(InstanceModel instanceModel, String actionType) {

        Swarm swarm = castInstanceModel(Swarm.class,instanceModel);

        Model.Builder serviceBuilder = new Model.Builder(SWARM_MODEL, actionType)
                .addVariable(HOST_IP_NAME, swarm.getHostIp())
                .addVariable(MASTER_IP_NAME, swarm.getMasterIp())
                .addVariable(AGENTS_IP_NAME, swarm.getAgentsIpAsString());

        swarm.getMachineName().ifPresent(machineName -> serviceBuilder.addVariable(MACHINE_NAME_NAME, machineName));
        swarm.getNetworkName().ifPresent(networkName -> serviceBuilder.addVariable(NETWORK_NAME_NAME, networkName));
        swarm.getStatus().ifPresent(
                status -> serviceBuilder.addVariable(PlatformAttributes.STATUS_NAME, status));
        swarm.getTitle().ifPresent(title -> serviceBuilder.addVariable(Attributes.ENTITY_TITLE_NAME, title));
        swarm.getSummary().ifPresent(summary -> serviceBuilder.addVariable(Attributes.SUMMARY_NAME, summary));

        swarm.getMixins().forEach(mixin -> mixin.toCloudAutomationModel(serviceBuilder));

        return serviceBuilder.build();
    }
}
