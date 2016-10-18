package org.ow2.proactive.procci.rest;

import java.io.IOException;

import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.CloudAutomationException;
import org.ow2.proactive.procci.model.occi.metamodel.MixinBuilder;
import org.ow2.proactive.procci.model.occi.metamodel.ProviderMixin;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.MixinRendering;
import org.ow2.proactive.procci.request.CloudAutomationVariables;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by mael on 14/10/16.
 */
public class MixinRestTest {

    @InjectMocks
    private MixinRest mixinRest;

    @Mock
    private CloudAutomationVariables cloudAutomationVariables;

    @Mock
    private ProviderMixin providerMixin;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getMixinTest() throws CloudAutomationException, IOException {
        String mixin = MixinRendering.convertStringFromMixin(
                new MixinBuilder(providerMixin, "schemeTest", "termTest").build().getRendering());
        when(cloudAutomationVariables.get("titleTest")).thenReturn(mixin);
        ResponseEntity<MixinRendering> response = mixinRest.getMixin("titleTest");
        assertThat(response.getBody().getScheme()).matches("schemeTest");
        assertThat(response.getBody().getTerm()).matches("termTest");
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        when(cloudAutomationVariables.get("titleTest2")).thenThrow(
                new CloudAutomationException("titleTest2"));
        ResponseEntity<MixinRendering> responseClientError = mixinRest.getMixin("titleTest2");
        assertThat(responseClientError.getStatusCode().is4xxClientError()).isTrue();

        when(cloudAutomationVariables.get("titleTest3")).thenThrow(IOException.class);
        ResponseEntity<MixinRendering> responseServerError = mixinRest.getMixin("titleTest3");
        assertThat(responseServerError.getStatusCode().is5xxServerError()).isTrue();
    }

    @Test
    public void postMixinTest() throws ClientException, IOException {
        MixinRendering mixinRendering = new MixinBuilder(providerMixin, "schemeTest",
                "termTest").build().getRendering();
        ResponseEntity<MixinRendering> response = mixinRest.createMixin(mixinRendering);
        assertThat(response.getBody().getScheme()).matches("schemeTest");
        assertThat(response.getBody().getTerm()).matches("termTest");
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    public void updateMixinTest() throws ClientException, IOException {
        MixinRendering mixinRendering = new MixinBuilder(providerMixin, "schemeTest",
                "termTest").build().getRendering();
        ResponseEntity<MixinRendering> response = mixinRest.updateMixin("termTest", mixinRendering);
        assertThat(response.getBody().getScheme()).matches("schemeTest");
        assertThat(response.getBody().getTerm()).matches("termTest");
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        ResponseEntity<MixinRendering> response2 = mixinRest.updateMixin("anotherTermTest", mixinRendering);
        assertThat(response2.getBody().getScheme()).matches("schemeTest");
        assertThat(response2.getBody().getTerm()).matches("termTest");
        assertThat(response2.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    public void deleteMixinTest() throws ClientException, IOException {
        ResponseEntity<MixinRendering> response = mixinRest.deleteMixin("termTest");
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }
}
