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

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.ow2.proactive.procci.model.exception.CloudAutomationException;
import org.ow2.proactive.procci.model.exception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by the Activeeon Team on 06/10/16.
 */

/**
 * Send CRUD service on cloud-automation-service/variables
 */
@Service
public class CloudAutomationVariablesClient {

    private static final Logger logger = LoggerFactory.getLogger(CloudAutomationVariablesClient.class);

    private static final String VARIABLES_ENDPOINT = "cloud-automation-service.variables.endpoint";

    @Autowired
    private RequestUtils requestUtils;

    public String get(String key) throws CloudAutomationException {
        logger.debug("get " + key + " on " + requestUtils.getProperty(VARIABLES_ENDPOINT));
        try {
            Response response = Request.Get(getResourceUrl(key)).version(HttpVersion.HTTP_1_1).execute();

            return requestUtils.readHttpResponse(response.returnResponse());

        } catch (IOException ex) {
            logger.error("Unable to get on " + getResourceUrl(key) + ", exception : " + ex.getMessage());
            throw new ServerException();
        }
    }

    public void post(String key, String value) throws CloudAutomationException {
        logger.debug("post " + key + " on " + requestUtils.getProperty(VARIABLES_ENDPOINT));
        try {
            HttpResponse response = Request.Post(getQueryUrl(key))
                                           .useExpectContinue()
                                           .version(HttpVersion.HTTP_1_1)
                                           .bodyString(value, ContentType.APPLICATION_JSON)
                                           .execute()
                                           .returnResponse();

            checkStatus(response);

        } catch (IOException ex) {
            logger.error("Unable to post on " + getQueryUrl(key) + ", exception : " + ex.getMessage());
            throw new ServerException();
        }

    }

    public void update(String key, String value) throws CloudAutomationException {
        logger.debug("update " + key + " on " + requestUtils.getProperty(VARIABLES_ENDPOINT));
        try {
            HttpResponse response = Request.Put(getResourceUrl(key))
                                           .useExpectContinue()
                                           .version(HttpVersion.HTTP_1_1)
                                           .bodyString(value, ContentType.APPLICATION_JSON)
                                           .execute()
                                           .returnResponse();

            checkStatus(response);

        } catch (IOException ex) {
            logger.error("Unable to put on " + getResourceUrl(key) + " ,exception : " + ex.getMessage());
            throw new ServerException();
        }
    }

    public void delete(String key) throws CloudAutomationException {
        logger.debug("delete " + key + " on " + requestUtils.getProperty(VARIABLES_ENDPOINT));
        try {
            HttpResponse response = Request.Delete(getResourceUrl(key))
                                           .version(HttpVersion.HTTP_1_1)
                                           .execute()
                                           .returnResponse();

            checkStatus(response);

        } catch (IOException ex) {
            logger.error("Unable to delete on " + getQueryUrl(key) + ", exception : " + ex.getMessage());
            throw new ServerException();
        }
    }

    private String getVariablesUrl() {
        return requestUtils.getProperty(VARIABLES_ENDPOINT);
    }

    private String getResourceUrl(String key) {
        return getVariablesUrl() + "/" + key;
    }

    private String getQueryUrl(String key) {
        return getVariablesUrl() + "?key=" + key;
    }

    private void checkStatus(HttpResponse response) throws CloudAutomationException {
        if (response.getStatusLine().getStatusCode() >= 300) {
            throw new CloudAutomationException(response.getStatusLine().getReasonPhrase());
        }
    }

}
