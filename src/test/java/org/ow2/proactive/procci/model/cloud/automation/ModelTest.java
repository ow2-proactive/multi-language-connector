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
package org.ow2.proactive.procci.model.cloud.automation;

import static com.google.common.truth.Truth.assertThat;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by the Activeeon team on 03/06/16.
 */
public class ModelTest {

    private Model cas;

    @Before
    public void setup() {
        cas = new Model.Builder("modelTest", "actionTypeTest").serviceType("typeTest")
                                                              .serviceName("nameTest")
                                                              .serviceDescription("descriptionTest")
                                                              .actionName("actionNameTest")
                                                              .actionDescription("actionDescriptionTest")
                                                              .actionOriginStates("originStatesTest")
                                                              .actionIcon("iconTest")
                                                              .addVariable("firstKey", "firstValue")
                                                              .addVariable("secondKey", "secondValue")
                                                              .build();
    }

    @Test
    public void constructorTest() {

        assertThat(cas.getServiceModel()).matches("modelTest");
        assertThat(cas.getServiceType()).matches("typeTest");
        assertThat(cas.getServiceName()).matches("nameTest");
        assertThat(cas.getServiceDescription()).matches("descriptionTest");
        assertThat(cas.getActionType()).matches("actionTypeTest");
        assertThat(cas.getActionName()).matches("actionNameTest");
        assertThat(cas.getActionDescription()).matches("actionDescriptionTest");
        assertThat(cas.getActionOriginStates()).matches("originStatesTest");
        assertThat(cas.getActionIcon()).matches("iconTest");
        assertThat(cas.getVariables()).containsExactly("firstKey", "firstValue", "secondKey", "secondValue");
    }

    @Test
    public void jsonConstructorTest() {
        JSONObject json = new JSONObject();

        JSONObject genericInfo = new JSONObject();
        genericInfo.put("pca.service.model", "modelTest");
        genericInfo.put("pca.service.type", "typeTest");
        genericInfo.put("pca.service.name", "nameTest");
        genericInfo.put("pca.service.description", "descriptionTest");
        genericInfo.put("pca.action.type", "actionTypeTest");
        genericInfo.put("pca.action.name", "actionNameTest");
        genericInfo.put("pca.action.description", "actionDescriptionTest");
        genericInfo.put("pca.action.origin.states", "originStatesTest");
        genericInfo.put("pca.action.icon", "iconTest");

        JSONObject variables = new JSONObject();
        variables.put("firstKey", "firstValue");
        variables.put("secondKey", "secondValue");

        json.put("genericInfo", genericInfo);
        json.put("variables", variables);

        assertThat(new Model(json)).isEqualTo(cas);
    }

    @Test
    public void getJsonTest() {
        assertThat(cas.getJson()).containsKey("genericInfo");
        assertThat(cas.getJson()).containsKey("variables");

        JSONObject service = (JSONObject) cas.getJson().get("genericInfo");
        JSONObject variables = (JSONObject) cas.getJson().get("variables");

        assertThat(service).containsEntry("pca.service.model", "modelTest");
        assertThat(service).containsEntry("pca.service.type", "typeTest");
        assertThat(service).containsEntry("pca.service.name", "nameTest");
        assertThat(service).containsEntry("pca.service.description", "descriptionTest");
        assertThat(service).containsEntry("pca.action.type", "actionTypeTest");
        assertThat(service).containsEntry("pca.action.name", "actionNameTest");
        assertThat(service).containsEntry("pca.action.description", "actionDescriptionTest");
        assertThat(service).containsEntry("pca.action.origin.states", "originStatesTest");
        assertThat(service).containsEntry("pca.action.icon", "iconTest");

        assertThat(variables).containsEntry("firstKey", "firstValue");
        assertThat(variables).containsEntry("secondKey", "secondValue");
    }

    @Test
    public void crossedTest() {
        assertThat(cas).isEqualTo(new Model(cas.getJson()));
    }

}
