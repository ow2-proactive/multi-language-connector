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
import org.ow2.proactive.procci.model.exception.CloudAutomationServerException;
import org.ow2.proactive.procci.model.occi.infrastructure.Compute;
import org.ow2.proactive.procci.model.occi.infrastructure.ComputeBuilder;
import org.ow2.proactive.procci.model.occi.infrastructure.state.ComputeState;


public class ComputeTransformerTest {

    @Test
    public void toCloudAutomationModelTest() throws CloudAutomationServerException {
        Compute compute = new ComputeBuilder().url("url")
                                              .architecture(Compute.Architecture.X64)
                                              .cores(5)
                                              .hostame("hostnameTest")
                                              .memory(new Float(3))
                                              .state(ComputeState.SUSPENDED)
                                              .share(2)
                                              .summary("summaryTest")
                                              .title("titleTest")
                                              .build();

        Model model = new ComputeTransformer().toCloudAutomationModel(compute, "create");
        assertThat(model.getServiceModel()).isEqualTo("occi.infrastructure.compute");
        assertThat(model.getActionType()).isEqualTo("create");
        assertThat(model.getVariables()).containsEntry("occi.compute.cores", "5");
        assertThat(model.getVariables()).containsEntry("occi.compute.architecture", "X64");
        assertThat(model.getVariables()).containsEntry("occi.compute.memory", "3.0");
        assertThat(model.getVariables()).containsEntry("occi.entity.title", "titleTest");
    }

}
