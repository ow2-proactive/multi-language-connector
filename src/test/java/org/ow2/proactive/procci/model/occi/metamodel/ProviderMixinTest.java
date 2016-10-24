package org.ow2.proactive.procci.model.occi.metamodel;

import org.ow2.proactive.procci.request.ProviderMixin;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 17/10/16.
 */
public class ProviderMixinTest {

    @Test
    public void getInstanceTest() {
        ProviderMixin providerMixin = new ProviderMixin();

        assertThat(providerMixin.getMixinBuilder("notAMixin").isPresent()).isFalse();
        assertThat(providerMixin.getMixinBuilder("vmimage").isPresent()).isTrue();
        assertThat(providerMixin.getMixinBuilder("vmimage").get().build().getTerm()).matches("vmimage");
    }

}
