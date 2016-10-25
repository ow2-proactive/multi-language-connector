package org.ow2.proactive.procci.request;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.CloudAutomationException;
import org.ow2.proactive.procci.model.occi.infrastructure.Compute;
import org.ow2.proactive.procci.model.occi.infrastructure.ComputeBuilder;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import org.ow2.proactive.procci.model.occi.metamodel.MixinBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by the Activeeon team on 20/10/16.
 */
public class ProviderMixinTest {

    @InjectMocks
    private ProviderMixin providerMixin;

    @Mock
    private CloudAutomationVariables cloudAutomationVariables;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getEntityMixinNamesTest() throws CloudAutomationException, IOException {
        Set<String> references = new HashSet<>();
        references.add("ref1Test");
        references.add("ref2Test");
        when(cloudAutomationVariables.get("idTest")).thenReturn(
                new ObjectMapper().writeValueAsString(references));
        Set<String> result = providerMixin.getEntityMixinNames("idTest");
        assertThat(result).contains("ref1Test");
        assertThat(result).contains("ref2Test");

        when(cloudAutomationVariables.get("idTest2")).thenReturn("[]");
        references = providerMixin.getEntityMixinNames("idTest2");
        assertThat(references).isEmpty();

        when(cloudAutomationVariables.get("idTest3")).thenThrow(new CloudAutomationException("idTest3"));
        Exception ex = null;
        try {
            providerMixin.getEntityMixinNames("idTest3");
        } catch (Exception e) {
            ex = e;
        }

        assertThat(ex).isInstanceOf(CloudAutomationException.class);
    }

    @Test
    public void addReferenceTest() throws ClientException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        Set<String> objectsId, references, newReferences, oldObjectId;


        //test update object and update mixin

        Set<String> mixinsId = new HashSet<>();
        mixinsId.add("mixinTest");

        Mixin mixin = new MixinBuilder("schemeTest", "mixinTest").build();

        Compute compute = new ComputeBuilder()
                .url("idTest")
                .addMixin(mixin)
                .build();

        when(cloudAutomationVariables.get("mixinTest")).thenReturn(
                mapper.writeValueAsString(mixin.getRendering()));

        providerMixin.addEntity(compute);

        mixin.addEntity(compute);

        verify(cloudAutomationVariables).post("idTest", mapper.writeValueAsString(mixinsId));
        verify(cloudAutomationVariables).get("mixinTest");
        verify(cloudAutomationVariables).update("mixinTest", mapper.writeValueAsString(mixin.getRendering()));


        //test add new object with new mixin


        Set<String> mixinId2 = new HashSet<>();
        mixinId2.add("mixinTest2");

        Mixin mixin2 = new MixinBuilder("schemeTest2", "mixinTest2").build();


        Compute compute2 = new ComputeBuilder()
                .url("idTest2")
                .addMixin(mixin2)
                .build();

        mixin2.addEntity(compute2);

        when(cloudAutomationVariables.get("idTest2")).thenReturn(
                mapper.writeValueAsString(mixin.getRendering()));

        Mockito.doThrow(new CloudAutomationException("mixinTest2")).when(cloudAutomationVariables).get(
                "mixinTest2");


        providerMixin.addEntity(compute2);

        verify(cloudAutomationVariables).post("idTest2",
                mapper.writeValueAsString(mixinId2));
        verify(cloudAutomationVariables).get("mixinTest2");
        verify(cloudAutomationVariables).post("mixinTest2", mapper.writeValueAsString(mixin2.getRendering()));


    }


}














