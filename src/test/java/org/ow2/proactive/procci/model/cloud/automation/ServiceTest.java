package org.ow2.proactive.procci.model.cloud.automation;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 03/06/16.
 */
public class ServiceTest {

    private Model service;

    @Before
    public void setup(){
        service = new Model.Builder("modelTest","create")
                .name("nameTest")
                .description("descriptionTest")
                .endpoint("endpointTest")
                .stateName("stateNameTest")
                .stateType("stateTypeTest")
                .type("typeTest")
                .addVariable("firstKey","firstValue")
                .addVariable("secondKey","secondValue")
                .build();
    }

    @Test
    public void constructorTest(){

        //constructor test without the action in parameter
        assertThat(service.getType()).matches("typeTest");
        assertThat(service.getName()).matches("nameTest");
        assertThat(service.getDescription()).matches("descriptionTest");
        assertThat(service.getEndpoint()).matches("endpointTest");
        assertThat(service.getModel()).matches("modelTest");
        assertThat(service.getStateName()).matches("stateNameTest");
        assertThat(service.getStateType()).matches("stateTypeTest");
        assertThat(service.getVariables()).containsExactly("firstKey","firstValue","secondKey","secondValue");
        assertThat(service.getName()).matches("nameTest");
        assertThat(service.getDescription()).matches("descriptionTest");
        assertThat(service.getEndpoint()).matches("endpointTest");
        assertThat(service.getStateName()).matches("stateNameTest");
        assertThat(service.getStateType()).matches("stateTypeTest");
        assertThat(service.getAction().getType()).matches("create");

        //constructor with action parameter test
        service = new Model.Builder("modelTest2",new Action.Builder("actionTest2").build()).build();
        assertThat(service.getAction().getType()).matches("actionTest2");
        assertThat(service.getModel()).matches("modelTest2");

    }

    @Test
    public void getJSONTest(){
        assertThat(service.getJson()).containsKey("service");
        assertThat(service.getJson()).containsKey("variables");
        assertThat(service.getJson()).containsKey("action");
    }
}
