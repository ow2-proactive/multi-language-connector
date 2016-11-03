package org.ow2.proactive.procci.model.occi.metamodel;

import org.ow2.proactive.procci.request.MixinService;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 17/10/16.
 */
public class MixinServiceTest {

    @Test
    public void getInstanceTest() {
        MixinService mixinService = new MixinService();

        assertThat(mixinService.getMixinBuilder("notAMixin").isPresent()).isFalse();
        assertThat(mixinService.getMixinBuilder("vmimage").isPresent()).isTrue();
        assertThat(mixinService.getMixinBuilder("vmimage").get().build().getTerm()).matches("vmimage");
    }

}
