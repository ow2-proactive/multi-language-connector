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

import static org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureAttributes.ARCHITECTURE_NAME;
import static org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureAttributes.COMPUTE_STATE_NAME;
import static org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureAttributes.CORES_NAME;
import static org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureAttributes.HOSTNAME_NAME;
import static org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureAttributes.MEMORY_NAME;
import static org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureAttributes.SHARE_NAME;
import static org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureIdentifiers.COMPUTE_MODEL;
import static org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureIdentifiers.COMPUTE_SCHEME;
import static org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelAttributes.ENTITY_TITLE_NAME;
import static org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelAttributes.ID_NAME;
import static org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelAttributes.SUMMARY_NAME;

import org.ow2.proactive.procci.model.InstanceModel;
import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.occi.infrastructure.Compute;
import org.springframework.stereotype.Component;


@Component
public class ComputeTransformer extends TransformerProvider {

    public TransformerType getType() {
        return TransformerType.COMPUTE;
    }

    /**
     * Convert OCCI compute to Proactive Cloud Automation Compute
     *
     * @param actionType is the action to apply on the compute
     * @return the proactive cloud automation model for the compute
     */
    @Override
    public Model toCloudAutomationModel(InstanceModel instanceModel, String actionType) {

        Compute compute = castInstanceModel(Compute.class, instanceModel);

        Model.Builder serviceBuilder = new Model.Builder(COMPUTE_MODEL, actionType).addVariable(ID_NAME,
                                                                                                compute.getId());
        compute.getTitle().ifPresent(title -> serviceBuilder.addVariable(ENTITY_TITLE_NAME, title));
        compute.getSummary().ifPresent(summary -> serviceBuilder.addVariable(SUMMARY_NAME, summary));
        compute.getArchitecture().ifPresent(archi -> serviceBuilder.addVariable(ARCHITECTURE_NAME, archi));
        compute.getCores().ifPresent(coresNumber -> serviceBuilder.addVariable(CORES_NAME, coresNumber));
        compute.getMemory().ifPresent(memoryNumber -> serviceBuilder.addVariable(MEMORY_NAME, memoryNumber));
        compute.getShare().ifPresent(shareNumber -> serviceBuilder.addVariable(SHARE_NAME, shareNumber));
        compute.getHostname().ifPresent(host -> serviceBuilder.addVariable(HOSTNAME_NAME, host));
        compute.getState().ifPresent(currentState -> serviceBuilder.addVariable(COMPUTE_STATE_NAME, currentState));

        compute.getMixins().forEach(mixin -> mixin.toCloudAutomationModel(serviceBuilder));

        return serviceBuilder.build();
    }
}
