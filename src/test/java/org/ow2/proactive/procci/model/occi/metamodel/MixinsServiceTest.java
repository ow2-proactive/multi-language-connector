package org.ow2.proactive.procci.model.occi.metamodel;

import org.ow2.proactive.procci.request.MixinsService;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 17/10/16.
 */
public class MixinsServiceTest {

    @Test
    public void getInstanceTest() {
        MixinsService mixinsService = new MixinsService();

        assertThat(mixinsService.getMixinBuilder("notAMixin").isPresent()).isFalse();
        assertThat(mixinsService.getMixinBuilder("vmimage").isPresent()).isTrue();
        assertThat(mixinsService.getMixinBuilder("vmimage").get().build().getTerm()).matches("vmimage");
    }

}
