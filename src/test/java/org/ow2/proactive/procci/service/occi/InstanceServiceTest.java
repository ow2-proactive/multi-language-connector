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
package org.ow2.proactive.procci.service.occi;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelAttributes.ID_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.occi.infrastructure.Compute;
import org.ow2.proactive.procci.model.occi.infrastructure.ComputeBuilder;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureIdentifiers;
import org.ow2.proactive.procci.model.occi.infrastructure.mixin.VMImage;
import org.ow2.proactive.procci.model.occi.metamodel.Entity;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import org.ow2.proactive.procci.model.occi.metamodel.Resource;
import org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelAttributes;
import org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelIdentifiers;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.EntityRendering;
import org.ow2.proactive.procci.model.occi.platform.bigdata.Swarm;
import org.ow2.proactive.procci.model.occi.platform.bigdata.SwarmBuilder;
import org.ow2.proactive.procci.service.CloudAutomationInstanceClient;
import org.ow2.proactive.procci.service.transformer.TransformerManager;
import org.ow2.proactive.procci.service.transformer.TransformerType;
import org.ow2.proactive.procci.service.transformer.occi.ComputeTransformer;
import org.ow2.proactive.procci.service.transformer.occi.SwarmTransformer;


public class InstanceServiceTest {

    @InjectMocks
    private InstanceService instanceService;

    @Mock
    private CloudAutomationInstanceClient cloudAutomationInstanceClient;

    @Mock
    private TransformerManager transformerManager;

    @Mock
    private ComputeTransformer computeTransformer;

    @Mock
    private SwarmTransformer swarmTransformer;

    @Mock
    private MixinService mixinService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getEntityTest() {

        String id = "idTest";
        Compute entryCompute = new ComputeBuilder().title("titleTest")
                                                   .architecture(Compute.Architecture.X64)
                                                   .cores(2)
                                                   .url(id)
                                                   .hostame("hostnameTest")
                                                   .summary("summaryTest")
                                                   .build();

        when(cloudAutomationInstanceClient.getInstanceModel(ID_NAME,
                                                            id,
                                                            computeTransformer)).thenReturn(Optional.of(entryCompute));
        when(transformerManager.getTransformerProvider(TransformerType.COMPUTE)).thenReturn(computeTransformer);
        when(computeTransformer.isInstanceOfType(entryCompute)).thenReturn(true);

        Optional<Entity> entity = instanceService.getEntity(id, TransformerType.COMPUTE);

        assertThat(entity.isPresent()).isTrue();
        assertThat(entity.get().getId()).matches(id);
        assertThat(entity.get()).isInstanceOf(Compute.class);

        Compute compute = (Compute) entity.get();

        assertThat(compute.getTitle().get()).matches("titleTest");
        assertThat(compute.getArchitecture().get()).isEqualTo(Compute.Architecture.X64);
        assertThat(compute.getCores().get()).isEqualTo(2);
        assertThat(compute.getHostname().get()).matches("hostnameTest");
        assertThat(compute.getSummary().get()).matches("summaryTest");

        String id2 = "idTest2";

        when(cloudAutomationInstanceClient.getInstanceModel(ID_NAME,
                                                            id2,
                                                            computeTransformer)).thenReturn(Optional.empty());

        Optional<Entity> entity2 = instanceService.getEntity(id2, TransformerType.COMPUTE);

        assertThat(entity2.isPresent()).isFalse();

    }

    @Test
    public void getMockedEntityTest() {

        String id = "idTest";

        Swarm entrySwarm = new SwarmBuilder("hostTest", "masterTest").url(id).build();

        when(cloudAutomationInstanceClient.getInstanceModel(ID_NAME,
                                                            id,
                                                            swarmTransformer)).thenReturn(Optional.of(entrySwarm));

        when(transformerManager.getTransformerProvider(TransformerType.SWARM)).thenReturn(swarmTransformer);
        when(swarmTransformer.isInstanceOfType(entrySwarm)).thenReturn(true);

        Optional<Entity> entity = instanceService.getEntity(id, TransformerType.SWARM);

        assertThat(entity.isPresent()).isTrue();
        assertThat(entity.get().getId()).matches(id);

        Swarm swarm = (Swarm) entity.get();

        assertThat(swarm).isInstanceOf(Resource.class);
        assertThat(swarm).isInstanceOf(Swarm.class);
        assertThat(swarm.getHostIp()).matches("hostTest");
        assertThat(swarm.getMasterIp()).matches("masterTest");

        String id2 = "id2Test";

        when(cloudAutomationInstanceClient.getInstanceModel(ID_NAME,
                                                            id2,
                                                            swarmTransformer)).thenReturn(Optional.empty());

        when(transformerManager.getTransformerProvider(TransformerType.SWARM)).thenReturn(swarmTransformer);

        Optional<Entity> emptyEntity = instanceService.getEntity(id2, TransformerType.SWARM);

        assertThat(emptyEntity.isPresent()).isFalse();

    }

    @Test
    public void getInstancesRenderingTest() {
        Model compute = new Model.Builder(InfrastructureIdentifiers.COMPUTE_MODEL, "action")
                                                                                            .addVariable(MetamodelAttributes.ID_NAME,
                                                                                                         "id1")
                                                                                            .build();

        List<Model> models = new ArrayList<>();
        models.add(compute);

        when(cloudAutomationInstanceClient.getModels()).thenReturn(models);

        List<EntityRendering> renderings = instanceService.getInstancesRendering();

        assertThat(renderings.get(0).getKind()).matches(InfrastructureIdentifiers.INFRASTRUCTURE_SCHEME +
                                                        InfrastructureIdentifiers.COMPUTE);

        Model resource = new Model.Builder("test", "action").addVariable(ID_NAME, "id").build();

        models = new ArrayList<>();
        models.add(resource);

        when(cloudAutomationInstanceClient.getModels()).thenReturn(models);

        renderings = instanceService.getInstancesRendering();

        assertThat(renderings.get(0).getKind()).matches(MetamodelIdentifiers.CORE_SCHEME +
                                                        MetamodelIdentifiers.RESOURCE_TERM);
    }

    @Test
    public void createTest() {
        Mixin mixin = new VMImage("vmimageTest", new ArrayList<>(), new ArrayList<>(), "imageTest");

        Compute compute = new ComputeBuilder().cores("2").title("titleTest").addMixin(mixin).build();

        when(cloudAutomationInstanceClient.postInstanceModel(compute,
                                                             "create",
                                                             computeTransformer)).thenReturn(compute);

        when(transformerManager.getTransformerProvider(TransformerType.COMPUTE)).thenReturn(computeTransformer);

        Resource resource = instanceService.create(compute, TransformerType.COMPUTE);

        verify(mixinService).addEntity(compute);

        assertThat(resource).isInstanceOf(Compute.class);

        Compute result = (Compute) resource;

        assertThat(result.getCores().get()).isEqualTo(new Integer(2));
        assertThat(result.getTitle().get()).matches("titleTest");
    }

}
