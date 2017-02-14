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
package org.ow2.proactive.procci.service.transformer.occi;

import static org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelAttributes.ID_NAME;
import static org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes.AGENTS_IP_NAME;
import static org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes.HOST_IP_NAME;
import static org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes.MACHINE_NAME_NAME;
import static org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes.MASTER_IP_NAME;
import static org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes.NETWORK_NAME_NAME;
import static org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataIdentifiers.SWARM_MODEL;

import org.ow2.proactive.procci.model.InstanceModel;
import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelAttributes;
import org.ow2.proactive.procci.model.occi.platform.bigdata.Swarm;
import org.ow2.proactive.procci.model.occi.platform.bigdata.SwarmBuilder;
import org.ow2.proactive.procci.model.occi.platform.constants.PlatformAttributes;
import org.ow2.proactive.procci.service.occi.MixinService;
import org.ow2.proactive.procci.service.transformer.TransformerProvider;
import org.ow2.proactive.procci.service.transformer.TransformerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SwarmTransformer extends TransformerProvider {

    @Autowired
    private MixinService mixinService;

    public TransformerType getType() {
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

        Swarm swarm = castInstanceModel(Swarm.class, instanceModel);

        Model.Builder serviceBuilder = new Model.Builder(SWARM_MODEL,
                                                         actionType).addVariable(ID_NAME, swarm.getId())
                                                                    .addVariable(HOST_IP_NAME, swarm.getHostIp())
                                                                    .addVariable(MASTER_IP_NAME, swarm.getMasterIp())
                                                                    .addVariable(AGENTS_IP_NAME,
                                                                                 swarm.getAgentsIpAsString());

        swarm.getMachineName().ifPresent(machineName -> serviceBuilder.addVariable(MACHINE_NAME_NAME, machineName));
        swarm.getNetworkName().ifPresent(networkName -> serviceBuilder.addVariable(NETWORK_NAME_NAME, networkName));
        swarm.getStatus().ifPresent(status -> serviceBuilder.addVariable(PlatformAttributes.STATUS_NAME, status));
        swarm.getTitle().ifPresent(title -> serviceBuilder.addVariable(MetamodelAttributes.ENTITY_TITLE_NAME, title));
        swarm.getSummary().ifPresent(summary -> serviceBuilder.addVariable(MetamodelAttributes.SUMMARY_NAME, summary));

        swarm.getMixins().forEach(mixin -> mixin.toCloudAutomationModel(serviceBuilder));

        return serviceBuilder.build();
    }

    @Override
    public InstanceModel toInstanceModel(Model model) {
        return new SwarmBuilder(model).addMixins(mixinService.getMixinsByEntityId(model.getVariables().get(ID_NAME)))
                                      .build();
    }

    @Override
    public boolean isInstanceOfType(InstanceModel instanceModel) {
        return instanceModel instanceof Swarm;
    }
}
