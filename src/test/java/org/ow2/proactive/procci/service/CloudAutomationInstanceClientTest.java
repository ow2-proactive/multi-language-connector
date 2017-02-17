/*
 * ProActive Parallel Suite(TM):
 * The Open Source library for parallel and distributed
 * Workflows & Scheduling, Orchestration, Cloud Automation
 * and Big Data Analysis on Enterprise Grids & Clouds.
 *
 * Copyright (c) 2007 - 2017 ActiveEon
 * Contact: contact@activeeon.com
 *
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation: version 3 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 */
package org.ow2.proactive.procci.service;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelAttributes.ID_NAME;
import static org.ow2.proactive.procci.service.CloudAutomationInstanceClient.PCA_INSTANCES_ENDPOINT;

import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ow2.proactive.procci.model.InstanceModel;
import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.occi.infrastructure.Compute;
import org.ow2.proactive.procci.model.occi.infrastructure.ComputeBuilder;
import org.ow2.proactive.procci.service.transformer.TransformerManager;
import org.ow2.proactive.procci.service.transformer.TransformerType;
import org.ow2.proactive.procci.service.transformer.occi.ComputeTransformer;


public class CloudAutomationInstanceClientTest {

    @InjectMocks
    private CloudAutomationInstanceClient cloudAutomationInstanceClient;

    @Mock
    private RequestUtils requestUtils;

    @Mock
    private TransformerManager transformerManager;

    @Mock
    private CloudAutomationVariablesClient cloudAutomationVariablesClient;

    @Mock
    private ComputeTransformer computeTransformer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getModelsTest() {
        String url = "urlTest";

        Model model1 = new Model.Builder("model1Test", "actionTest").build();
        Model model2 = new Model.Builder("modelT2est", "actionTest").build();

        //test for empty database
        when(requestUtils.getRequest(url)).thenReturn(new JSONObject());
        when(requestUtils.getProperty(PCA_INSTANCES_ENDPOINT)).thenReturn(url);

        List<Model> noModel = cloudAutomationInstanceClient.getModels();

        assertThat(noModel).isEmpty();

        //test for one item in the database
        JSONObject oneItemJson = new JSONObject();
        oneItemJson.put("id1", model1.getJson());

        when(requestUtils.getRequest(url)).thenReturn(oneItemJson);

        List<Model> uniqueModel = cloudAutomationInstanceClient.getModels();

        assertThat(uniqueModel).containsExactly(model1);

        //test for two item in the database
        JSONObject twoItemJson = new JSONObject();
        twoItemJson.put("id2", model1.getJson());
        twoItemJson.put("id3", model2.getJson());

        when(requestUtils.getRequest(url)).thenReturn(twoItemJson);

        List<Model> twoModels = cloudAutomationInstanceClient.getModels();

        assertThat(twoModels).containsExactly(model1, model2);
    }

    @Test
    public void getInstanceByVariablesTest() {
        String url = "urlTest";

        String id1 = "id1";
        String id2 = "id2";

        Model model1 = new Model.Builder("model1Test", "actionTest").addVariable(ID_NAME, id1).build();
        Model model2 = new Model.Builder("modelT2est", "actionTest").addVariable(ID_NAME, id2).build();

        when(requestUtils.getProperty(PCA_INSTANCES_ENDPOINT)).thenReturn(url);

        //test for empty database
        when(requestUtils.getRequest(url)).thenReturn(new JSONObject());

        Optional<Model> noModel = cloudAutomationInstanceClient.getInstanceByVariable("noKey", "noValue");

        assertThat(noModel.isPresent()).isFalse();

        //test for one item in the database
        JSONObject oneItemJson = new JSONObject();
        oneItemJson.put("id1", model1.getJson());
        when(requestUtils.getRequest(url)).thenReturn(oneItemJson);

        //with the good key and value
        Optional<Model> goodModel = cloudAutomationInstanceClient.getInstanceByVariable(ID_NAME, id1);
        assertThat(goodModel.get()).isEqualTo(model1);

        //with a bad key and good value
        Optional<Model> badKey = cloudAutomationInstanceClient.getInstanceByVariable("noKey", "noValue");
        assertThat(badKey.isPresent()).isFalse();

        //with a good key and bad value
        Optional<Model> badValue = cloudAutomationInstanceClient.getInstanceByVariable("noKey", "noValue");
        assertThat(badValue.isPresent()).isFalse();

        //test for two item in the database
        JSONObject twoItemJson = new JSONObject();
        twoItemJson.put("id2", model1.getJson());
        twoItemJson.put("id3", model2.getJson());

        when(requestUtils.getRequest(url)).thenReturn(twoItemJson);

        Optional<Model> goodModel2 = cloudAutomationInstanceClient.getInstanceByVariable(ID_NAME, id2);

        assertThat(goodModel2.get()).isEqualTo(model2);
    }

    @Test
    public void getInstanceModelTest() {
        String url = "urlTest";
        String id1 = "id1";
        String endpointTest = "endpointTest";

        Model model1 = new Model.Builder("model1Test", "actionTest").addVariable(PCA_INSTANCES_ENDPOINT, endpointTest)
                                                                    .addVariable(ID_NAME, id1)
                                                                    .build();
        Compute computeReceive = new ComputeBuilder(model1).build();

        when(requestUtils.getProperty(PCA_INSTANCES_ENDPOINT)).thenReturn(url);

        //test for empty database
        when(requestUtils.getRequest(url)).thenReturn(new JSONObject());

        Optional<InstanceModel> emptyModel = cloudAutomationInstanceClient.getInstanceModel("noKey",
                                                                                            "noValue",
                                                                                            computeTransformer);
        assertThat(emptyModel.isPresent()).isFalse();

        //test for one item in the database
        JSONObject oneItemJson = new JSONObject();
        oneItemJson.put("id1", model1.getJson());
        when(requestUtils.getRequest(url)).thenReturn(oneItemJson);
        when(transformerManager.getTransformerProvider(TransformerType.COMPUTE)).thenReturn(computeTransformer);
        when(computeTransformer.toInstanceModel(model1)).thenReturn(computeReceive);

        Optional<InstanceModel> resultInstance = cloudAutomationInstanceClient.getInstanceModel(ID_NAME,
                                                                                                id1,
                                                                                                computeTransformer);
        assertThat(resultInstance.get()).isInstanceOf(Compute.class);

        Compute compute = (Compute) resultInstance.get();
        assertThat(compute).isEqualTo(computeReceive);
    }

    @Test
    public void postInstanceModelTest() {
        String url = "urlTest";
        String id1 = "id1";
        String endpointTest = "endpointTest";
        Compute sendCompute = new ComputeBuilder().url(id1).build();
        Compute receiveCompute = new ComputeBuilder().url(id1).hostame(endpointTest).build();
        Model sendModel = new Model.Builder("model1Test", "actionTest").addVariable(ID_NAME, id1).build();
        Model receiveModel = new Model.Builder("model1Test", "actionTest").addVariable(ID_NAME, id1)
                                                                          .addVariable("endpoint", endpointTest)
                                                                          .build();

        when(requestUtils.getProperty(PCA_INSTANCES_ENDPOINT)).thenReturn(url);
        when(transformerManager.getTransformerProvider(TransformerType.COMPUTE)).thenReturn(computeTransformer);
        when(requestUtils.postRequest(sendModel.getJson(), url)).thenReturn(receiveModel.getJson());
        when(computeTransformer.toCloudAutomationModel(sendCompute, "create")).thenReturn(sendModel);
        when(computeTransformer.toInstanceModel(receiveModel)).thenReturn(receiveCompute);

        InstanceModel instanceModel = cloudAutomationInstanceClient.postInstanceModel(sendCompute,
                                                                                      "create",
                                                                                      computeTransformer);

        assertThat(instanceModel).isInstanceOf(Compute.class);

        Compute computeResult = (Compute) instanceModel;

        assertThat(computeResult.getHostname().get()).matches(endpointTest);
        assertThat(computeResult).isEqualTo(receiveCompute);
    }

    @Test
    public void deleteInstanceModelTest(){

        String entityIdToDelete = "entityIdToDelete";
        String url = "url";

        when(requestUtils.getProperty(PCA_INSTANCES_ENDPOINT)).thenReturn(url);

        cloudAutomationInstanceClient.deleteInstanceModel(entityIdToDelete);

        verify(requestUtils,times(1)).getProperty(PCA_INSTANCES_ENDPOINT);
        verify(requestUtils,times(1)).deleteRequest(url,entityIdToDelete);
    }
}
