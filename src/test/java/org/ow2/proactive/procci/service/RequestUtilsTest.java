package org.ow2.proactive.procci.service;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by the Activeeon team  on 23/06/16.
 */
public class RequestUtilsTest {

    @Test
    public void getPropertyTest() {
        assertThat(new RequestUtils().getProperty("scheduler.login.endpoint")).isNotEmpty();
    }
}
