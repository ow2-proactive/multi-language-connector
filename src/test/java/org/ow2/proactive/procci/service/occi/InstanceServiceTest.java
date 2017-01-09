package org.ow2.proactive.procci.service.occi;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;
import static org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelAttributes.ID_NAME;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.occi.infrastructure.Compute;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureIdentifiers;
import org.ow2.proactive.procci.model.occi.metamodel.Entity;
import org.ow2.proactive.procci.model.occi.metamodel.Resource;
import org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelAttributes;
import org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelIdentifiers;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.EntityRendering;
import org.ow2.proactive.procci.model.occi.platform.bigdata.Swarm;
import org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes;
import org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataIdentifiers;
import org.ow2.proactive.procci.model.utils.ConvertUtils;
import org.ow2.proactive.procci.service.CloudAutomationInstanceClient;

public class InstanceServiceTest {

    @InjectMocks
    private InstanceService instanceService;

    @Mock
    private CloudAutomationInstanceClient cloudAutomationInstanceClient;

    @Mock
    private MixinService mixinService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getEntityTest(){

        String id = "idTest";
        Model model = new Model.Builder("modelTest","actionTest")
                .addVariable(MetamodelAttributes.ID_NAME,id)
                .build();


        when(cloudAutomationInstanceClient.getInstanceByVariable(ID_NAME, ConvertUtils.formatURL(id)))
                .thenReturn(Optional.of(model));
        when(mixinService.getEntityMixinNames(id))
                .thenReturn(new HashSet<>());

        Optional<Entity> entity = instanceService.getEntity(id);

        assertThat(entity.isPresent()).isTrue();
        assertThat(entity.get().getId()).matches(id);
        assertThat(entity.get()).isInstanceOf(Resource.class);

        String id2 = "idTest2";
        Model model2 = new Model.Builder("http://schemas.ogf.org/occi/infrastructure#compute","actionTest")
                .addVariable(MetamodelAttributes.ID_NAME,id)
                .build();


        when(cloudAutomationInstanceClient.getInstanceByVariable(ID_NAME, ConvertUtils.formatURL(id2)))
                .thenReturn(Optional.of(model2));
        when(mixinService.getEntityMixinNames(id2))
                .thenReturn(new HashSet<>());

        Optional<Entity> entity2 = instanceService.getEntity(id2);

        assertThat(entity2.isPresent()).isTrue();
        assertThat(entity2.get().getId()).matches(id);
        assertThat(entity2.get()).isInstanceOf(Resource.class);
        assertThat(entity2.get()).isInstanceOf(Compute.class);

    }

    @Test
    public void getMockedEntityTest(){

        String id = "idTest";
        Model model = new Model.Builder("modelTest","actionTest")
                .addVariable(MetamodelAttributes.ID_NAME,id)
                .build();


        when(cloudAutomationInstanceClient.getInstanceByVariable(ID_NAME, ConvertUtils.formatURL(id)))
                .thenReturn(Optional.of(model));

        Optional<Entity> entity = instanceService.getEntity(id);

        assertThat(entity.isPresent()).isTrue();
        assertThat(entity.get().getId()).matches(id);

        String id2 = "idTest2";
        Model model2 = new Model.Builder(BigDataIdentifiers.BIGDATA_SCHEME+BigDataIdentifiers.SWARM_TERM,"actionTest")
                .addVariable(MetamodelAttributes.ID_NAME,id)
                .addVariable(BigDataAttributes.HOST_IP_NAME,"hostTest")
                .addVariable(BigDataAttributes.MASTER_IP_NAME,"masterTest")
                .build();


        when(cloudAutomationInstanceClient.getInstanceByVariable(ID_NAME, ConvertUtils.formatURL(id2)))
                .thenReturn(Optional.of(model2));
        when(mixinService.getEntityMixinNames(id2))
                .thenReturn(new HashSet<>());

        Optional<Entity> entity2 = instanceService.getEntity(id2);

        assertThat(entity2.isPresent()).isTrue();
        assertThat(entity2.get().getId()).matches(id);
        assertThat(entity2.get()).isInstanceOf(Resource.class);
        assertThat(entity2.get()).isInstanceOf(Swarm.class);
    }

    @Test
    public void getInstancesRenderingTest(){
        Model compute = new Model
                .Builder(InfrastructureIdentifiers.INFRASTRUCTURE_SCHEME+InfrastructureIdentifiers.COMPUTE,"action")
                .addVariable(MetamodelAttributes.ID_NAME,"id1")
                .build();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id1",compute.getJson());

        when(cloudAutomationInstanceClient.getRequest())
                .thenReturn(jsonObject);

        when(mixinService.getEntityMixinNames("id1"))
                .thenReturn(new HashSet<>());

        List<EntityRendering> renderings = instanceService.getInstancesRendering();


        assertThat(renderings.get(0).getKind())
                .matches(InfrastructureIdentifiers.INFRASTRUCTURE_SCHEME+InfrastructureIdentifiers.COMPUTE);

        Model resource = new Model.Builder("test","action")
                .build();

        jsonObject = new JSONObject();
        jsonObject.put("id2",resource.getJson());

        when(cloudAutomationInstanceClient.getRequest())
                .thenReturn(jsonObject);

        when(mixinService.getEntityMixinNames("id2"))
                .thenReturn(new HashSet<>());

        renderings = instanceService.getInstancesRendering();

        assertThat(renderings.get(0).getKind())
                .matches(MetamodelIdentifiers.CORE_SCHEME+MetamodelIdentifiers.RESOURCE_TERM);

    }
}
