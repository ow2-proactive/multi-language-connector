package org.ow2.proactive.procci.request;

import org.junit.Ignore;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 23/06/16.
 */
public class CloudAutomationRequestTest {

    @Test
    public void getPropertyTest(){
        assertThat(new CloudAutomationRequest().getProperty("server.endpoint")).isNotEmpty();
    }

    @Ignore @Test
    public void getSessionIdTest(){
        assertThat(new CloudAutomationRequest().getSessionId()).isNotEmpty();
    }
}
