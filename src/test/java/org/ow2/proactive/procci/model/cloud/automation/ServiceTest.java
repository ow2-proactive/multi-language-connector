package org.ow2.proactive.procci.model.cloud.automation;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 03/06/16.
 */
public class ServiceTest {

    private Service service;

    @Before
    public void setup(){
        service = new Service.Builder("modelTest","create")
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
        assertThat(service.getJsonVariables()).containsExactly("firstKey","firstValue","secondKey","secondValue");
        assertThat(service.getJsonService()).containsKey("name");
        assertThat(service.getJsonService()).containsKey("description");
        assertThat(service.getJsonService()).containsKey("endpoint");
        assertThat(service.getJsonService()).containsKey("state_name");
        assertThat(service.getJsonService()).containsKey("state_type");
        assertThat(service.getJsonService()).containsKey("type");
        assertThat(service.getAction().getType()).matches("create");

        //constructor with action parameter test
        service = new Service.Builder("modelTest2",new Action.Builder("actionTest2").build()).build();
        assertThat(service.getAction().getType()).matches("actionTest2");
        assertThat(service.getModel()).matches("modelTest2");
    }

    @Test
    public void getCloudAutomationModelTest(){
        assertThat(service.getCloudAutomationModel()).containsEntry("service",service.getJsonService());
        assertThat(service.getCloudAutomationModel()).containsEntry("variables",service.getJsonVariables());
        assertThat(service.getCloudAutomationModel()).containsEntry("action",service.getAction().getJsonAction());
    }
}
