package org.ow2.proactive.procci.model.cloud.automation;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 03/06/16.
 */
public class ActionTest {

    @Test
    public void constructorTest(){
        Action action = new Action.Builder("Docker")
                .description("this is a test")
                .icon("icon url")
                .name("instance name")
                .originStates("inactive")
                .build();
        assertThat(action.getDescription()).matches("this is a test");
        assertThat(action.getIcon()).matches("icon url");
        assertThat(action.getName()).matches("instance name");
        assertThat(action.getOriginStates()).matches("inactive");
        assertThat(action.getType()).matches("Docker");
        assertThat(action.getJsonAction()).containsKey("icon");
        assertThat(action.getJsonAction()).containsKey("name");
        assertThat(action.getJsonAction()).containsKey("description");
        assertThat(action.getJsonAction()).containsKey("type");
        assertThat(action.getJsonAction()).containsKey("origin_states");
    }
}
