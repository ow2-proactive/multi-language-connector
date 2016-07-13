package org.ow2.proactive.procci.model.cloud.automation;

import org.json.simple.JSONObject;
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
        System.out.println(action.getJson());
        assertThat(action.getDescription()).matches("this is a test");
        assertThat(action.getIcon()).matches("icon url");
        assertThat(action.getName()).matches("instance name");
        assertThat(action.getOriginStates()).matches("inactive");
        assertThat(action.getType()).matches("Docker");
    }

    @Test
    public void jsonConstructorTest(){
        JSONObject json = new JSONObject();
        json.put("type","typeTest");
        json.put("icon","iconTest");
        json.put("name","nameTest");
        json.put("description","descriptionTest");
        json.put("origin_states","originStatesTest");
        json.put("badKey",new Integer(1));
        Action action = new Action(json);
        assertThat(action.getDescription()).matches("descriptionTest");
        assertThat(action.getIcon()).matches("iconTest");
        assertThat(action.getName()).matches("nameTest");
        assertThat(action.getOriginStates()).matches("originStatesTest");
        assertThat(action.getType()).matches("typeTest");
    }

    @Test
    public void getJsonTest(){
        Action action = new Action.Builder("Docker")
                .description("this is a test")
                .icon("icon url")
                .name("instance name")
                .originStates("inactive")
                .build();
        assertThat(action.getJson()).containsKey("icon");
        assertThat(action.getJson()).containsKey("name");
        assertThat(action.getJson()).containsKey("description");
        assertThat(action.getJson()).containsKey("type");
        assertThat(action.getJson()).containsKey("origin_states");
    }

}
