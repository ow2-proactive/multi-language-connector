package org.ow2.proactive.procci.service.occi;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelAttributes.ID_NAME;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.json.simple.JSONObject;
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
import org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes;
import org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataIdentifiers;
import org.ow2.proactive.procci.model.utils.ConvertUtils;
import org.ow2.proactive.procci.service.CloudAutomationInstanceClient;
import org.ow2.proactive.procci.service.transformer.ComputeTransformer;
import org.ow2.proactive.procci.service.transformer.TransformerManager;
import org.ow2.proactive.procci.service.transformer.TransformerType;

public class InstanceServiceTest {

    @InjectMocks
    private InstanceService instanceService;

    @Mock
    private CloudAutomationInstanceClient cloudAutomationInstanceClient;

    @Mock
    private MixinService mixinService;

    @Mock
    private TransformerManager transformerManager;

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
        Model model2 = new Model.Builder("occi.infrastructure.compute","actionTest")
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
        Model model2 = new Model.Builder(BigDataIdentifiers.SWARM_MODEL,"actionTest")
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
                .Builder(InfrastructureIdentifiers.COMPUTE_MODEL,"action")
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

    @Test
    public void createTest(){
        Mixin mixin = new VMImage("vmimageTest",new ArrayList<>(),new ArrayList<>(),"imageTest");
        Mixin mockedMixin = new VMImage("vmimageTest2",new ArrayList<>(),new ArrayList<>(),"imageTest2");

        Compute compute = new ComputeBuilder().cores("2").title("titleTest").addMixin(mixin).build();
        Set<String> mixinSet = new HashSet<>(1);
        mixinSet.add(mixin.getTitle());

        //get the transformer
        when(transformerManager.getTransformerProvider(TransformerType.COMPUTE))
                .thenReturn(new ComputeTransformer());

        //in -> the compute in json, out -> the response in json
        when(cloudAutomationInstanceClient.postRequest(new ComputeTransformer()
                .toCloudAutomationModel(compute,"create")
                .getJson())
        ).thenReturn(new ComputeTransformer()
                .toCloudAutomationModel(compute,"create")
                .getJson()
        );

        //list the mixin name
        when(mixinService.getEntityMixinNames(compute.getId()))
                .thenReturn(mixinSet);

        //get the mixin from its title
        when(mixinService.getMixinMockByTitle(mixin.getTitle()))
                .thenReturn(mockedMixin);

        assertThat(compute.getMixins()).containsExactly(mixin);
        assertThat(mixin.getEntities()).containsExactly(compute);

        Resource resource = instanceService.create(compute,TransformerType.COMPUTE);

        verify(mixinService).addEntity(compute);
        verify(mixinService).getEntityMixinNames(compute.getId());

        assertThat(resource).isInstanceOf(Compute.class);
        assertThat(mockedMixin.getEntities().get(0)).isEqualTo(compute);
        assertThat(resource.getMixins()).containsExactly(mockedMixin);

        Compute result = (Compute) resource;

        assertThat(result.getCores().get()).isEqualTo(new Integer(2));
        assertThat(result.getTitle().get()).matches("titleTest");
    }

}