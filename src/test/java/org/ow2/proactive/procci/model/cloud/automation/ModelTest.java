package org.ow2.proactive.procci.model.cloud.automation;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 03/06/16.
 */
public class ModelTest {

    private Model cas;

    @Before
    public void setup(){
        cas = new Model.Builder("modelTest","create")
                .name("nameTest")
                .description("descriptionTest")
                .endpoint("endpointTest")
                .stateName("stateNameTest")
                .stateType("stateTypeTest")
                .type("typeTest")
                .icon("iconTest")
                .addVariable("firstKey","firstValue")
                .addVariable("secondKey","secondValue")
                .build();
    }

    @Test
    public void constructorTest(){

        //constructor test without the action in parameter
        assertThat(cas.getType()).matches("typeTest");
        assertThat(cas.getName()).matches("nameTest");
        assertThat(cas.getDescription()).matches("descriptionTest");
        assertThat(cas.getEndpoint()).matches("endpointTest");
        assertThat(cas.getModel()).matches("modelTest");
        assertThat(cas.getStateName()).matches("stateNameTest");
        assertThat(cas.getStateType()).matches("stateTypeTest");
        assertThat(cas.getVariables()).containsExactly("firstKey","firstValue","secondKey","secondValue");
        assertThat(cas.getAction().getType()).matches("create");

        //constructor with action parameter test
        cas = new Model.Builder("modelTest2",new Action.Builder("actionTest2").build()).build();
        assertThat(cas.getAction().getType()).matches("actionTest2");
        assertThat(cas.getModel()).matches("modelTest2");

    }

    @Test
    public void jsonConstructorTest(){
        JSONObject json = new JSONObject();
        json.put("serviceModel","occi.infrastructure.compute");
        json.put("serviceName","nameTest");
        json.put("serviceInstanceId","idTest");
        json.put("serviceInstanceName","instanceNameTest");
        json.put("serviceInstanceStatus","statusName");
        json.put("infrastuctureName","infraTest");
        json.put("instanceId","instanceIdTest");
        json.put("instanceEndpoint","endpointTest");

        Model model = new Model(json);

        assertThat(model.getModel()).matches("occi.infrastructure.compute");
        assertThat(model.getName()).matches("nameTest");
        assertThat(model.getStateName()).matches("statusName");
        assertThat(model.getVariables()).containsExactly("id","idTest","name","instanceNameTest");

    }

    @Test @Ignore
    public void getJsonTest(){
        assertThat(cas.getJson()).containsKey("service");
        assertThat(cas.getJson()).containsKey("action");
        assertThat(cas.getJson()).containsKey("variables");

        JSONObject service = (JSONObject) cas.getJson().get("service");
        JSONObject action = (JSONObject) cas.getJson().get("action");
        JSONObject variables = (JSONObject) cas.getJson().get("variables");

        assertThat(service).containsEntry("model","modelTest");
        assertThat(service).containsEntry("type","typeTest");
        assertThat(service).containsEntry("name","nameTest");
        assertThat(service).containsEntry("description","descriptionTest");
        assertThat(service).containsEntry("endpoint","endpointTest");
        assertThat(service).containsEntry("icon","iconTest");

        assertThat(action).containsEntry("type","create");
        assertThat(action).containsEntry("name","");
        assertThat(action).containsKey("origin_states");

        assertThat(variables).containsEntry("firstKey","firstValue");
        assertThat(variables).containsEntry("secondKey","secondValue");

    }

    @Test
    public void getCASRequestTest(){

        assertThat(cas.getCASRequest()).containsKey("variables");
        assertThat(cas.getCASRequest()).containsKey("genericInfo");

        JSONObject service = (JSONObject) cas.getCASRequest().get("genericInfo");
        assertThat(service).containsEntry("pca.service.model","modelTest");
        assertThat(service).containsEntry("pca.service.name","nameTest");
        assertThat(service).containsEntry("pca.action.type","create");

        JSONObject variables = (JSONObject) cas.getCASRequest().get("variables");
        assertThat(variables).containsEntry("firstKey","firstValue");
        assertThat(variables).containsEntry("secondKey","secondValue");



    }

    @Test
    public void getJSONTest(){
        assertThat(cas.getJson()).containsKey("service");
        assertThat(cas.getJson()).containsKey("variables");
        assertThat(cas.getJson()).containsKey("action");
    }
}
