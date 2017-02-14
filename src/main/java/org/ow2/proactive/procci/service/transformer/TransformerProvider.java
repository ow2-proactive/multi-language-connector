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
package org.ow2.proactive.procci.service.transformer;

import org.ow2.proactive.procci.model.InstanceModel;
import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.exception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public abstract class TransformerProvider {

    public static Logger logger = LoggerFactory.getLogger(TransformerProvider.class);

    protected static <T> T castInstanceModel(Class<T> classe, InstanceModel instanceModel) {

        if (classe.isInstance(instanceModel)) {
            return classe.cast(instanceModel);
        } else {
            logger.error("Error in castInstanceModel : the instance of " + instanceModel.getClass().getName() +
                         " is not an instance of " + classe.getName());
            throw new ServerException();
        }

    }

    /**
     * Give the type associated to the transformer class
     * @return the TransformerType associated
     */
    public abstract TransformerType getType();

    /**
     *  Convert the instance model into a cloud automaton model
     * @param instanceModel is the model that will be converted
     * @param actionType is the action that is intended on the object
     * @return an model converted from instanceModel
     */
    public abstract Model toCloudAutomationModel(InstanceModel instanceModel, String actionType);

    /**
     *  Convert the model from cloud automation to an instance model
     * @param model is the cloud automation model that is converted
     * @return a instance model converted from cloud automation model
     */
    public abstract InstanceModel toInstanceModel(Model model);

    /**
     *  Check if instance model is an applicable instance for the transformer
     * @param instanceModel is the instance model that will be tested
     * @return true if the instance model is applicable for the transformer otherwise return false
     */
    public abstract boolean isInstanceOfType(InstanceModel instanceModel);

}
