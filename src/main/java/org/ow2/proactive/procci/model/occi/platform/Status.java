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
package org.ow2.proactive.procci.model.occi.platform;

import org.ow2.proactive.procci.model.exception.SyntaxException;


public enum Status {
    ACTIVE("The component is running"),
    INACTIVE("The component is not running"),
    ERROR("Error : the component is not working correctly");

    private String message;

    Status(String message) {
        this.message = message;
    }

    public static Status getStatusFromString(String value) throws SyntaxException {
        if (ACTIVE.name().equalsIgnoreCase(value)) {
            return ACTIVE;
        } else if (INACTIVE.name().equalsIgnoreCase(value)) {
            return INACTIVE;
        } else if (ERROR.name().equalsIgnoreCase(value)) {
            return ERROR;
        } else {
            throw new SyntaxException(value, ACTIVE.name() + ", " + INACTIVE.name() + " or " + ERROR.name());
        }
    }
}
