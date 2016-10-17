package org.ow2.proactive.procci.model.occi.metamodel;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 17/10/16.
 */
public class ProviderMixinTest {

    @Test
    public void getInstanceTest(){
        ProviderMixin providerMixin= new ProviderMixin();

        assertThat(providerMixin.getInstance("notAMixin").isPresent()).isFalse();
        assertThat(providerMixin.getInstance("vmimage").isPresent()).isTrue();

    }

}
