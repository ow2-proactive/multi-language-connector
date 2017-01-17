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
package org.ow2.proactive.procci.model.exception;

import org.json.simple.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


/**
 * Created by the Activeeon Team on 21/06/16.
 */

/**
 * The Exception occur when an http service fail with an other Cloud Automation microservice
 */
@Getter
@AllArgsConstructor
@ToString
public class CloudAutomationServerException extends ServerException {
    private JSONObject jsonError;

    public CloudAutomationServerException(String exception, String url, String request) {
        jsonError = new JSONObject();
        jsonError.put("error", exception);
        jsonError.put("url", url);
        jsonError.put("request", request);
    }

    @Override
    public String getJsonError() {
        return jsonError.toJSONString();
    }
}
