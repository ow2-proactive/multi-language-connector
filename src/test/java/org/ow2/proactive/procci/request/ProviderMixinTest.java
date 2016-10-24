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

        Set<String> mixinId = new HashSet<>();
        mixinId.add("mixinTest");

        Mixin mixin = new MixinBuilder(providerMixin, "schemeTest", "mixinTest").build();

        Compute compute = new ComputeBuilder()
                .url("idTest")
                .addMixin(mixin)
                .build();

        providerMixin.addEntity(compute);

        verify(cloudAutomationVariables).update("idTest", mapper.writeValueAsString(mixinId));
        verify(cloudAutomationVariables).update("mixinTest", mapper.writeValueAsString(mixin.getRendering()));


        //test add new object with new mixin


        Set<String> mixinId2 = new HashSet<>();
        mixinId2.add("mixinTest2");

        Mixin mixin2 = new MixinBuilder(providerMixin, "schemeTest2", "mixinTest2").build();

        Compute compute2 = new ComputeBuilder()
                .url("idTest2")
                .addMixin(mixin2)
                .build();

        Mockito.doThrow(new CloudAutomationException("idTest2")).when(cloudAutomationVariables).update(
                "idTest2", mapper.writeValueAsString(mixinId2));
        Mockito.doThrow(new CloudAutomationException("mixinTest2")).when(cloudAutomationVariables).update(
                "mixinTest2", mapper.writeValueAsString(mixin2.getRendering()));

        providerMixin.addEntity(compute2);

        verify(cloudAutomationVariables).update("idTest2", mapper.writeValueAsString(mixinId2));
        verify(cloudAutomationVariables).update("mixinTest2",
                mapper.writeValueAsString(mixin2.getRendering()));
        verify(cloudAutomationVariables).post("idTest2", mapper.writeValueAsString(mixinId2));
        verify(cloudAutomationVariables).post("mixinTest2", mapper.writeValueAsString(mixin2.getRendering()));

    }


}














