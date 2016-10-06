/*
 *  *
 * ProActive Parallel Suite(TM): The Java(TM) library for
 *    Parallel, Distributed, Multi-Core Computing for
 *    Enterprise Grids & Clouds
 *
 * Copyright (C) 1997-2016 INRIA/University of
 *                 Nice-Sophia Antipolis/ActiveEon
 * Contact: proactive@ow2.org or contact@activeeon.com
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; version 3 of
 * the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 *
 *  Initial developer(s):               The ProActive Team
 *                        http://proactive.inria.fr/team_members.htm
 *  Contributor(s):
 *
 *  * $$ACTIVEEON_INITIAL_DEV$$
 */
package org.ow2.proactive.procci.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.exception.SyntaxException;
import org.ow2.proactive.procci.model.occi.infrastructure.ComputeBuilder;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.EntitiesRendering;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.EntityRendering;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.ResourceRendering;
import org.ow2.proactive.procci.model.utils.ConvertUtils;
import org.ow2.proactive.procci.request.CloudAutomationException;
import org.ow2.proactive.procci.request.CloudAutomationInstances;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.ow2.proactive.procci.model.occi.metamodel.constants.Attributes.ID_NAME;

/**
 * Implement CRUD methods for REST service
 */
@RestController
@RequestMapping(value = Constant.COMPUTE_PATH)
public class ComputeRest {

    private final Logger logger = LogManager.getRootLogger();

    //-------------------Retrieve All Computes--------------------------------------------------------

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EntitiesRendering> listAllComputes() {
        logger.debug("Get all Compute instances");
        try {
            JSONObject resources = new CloudAutomationInstances().getRequest();

            List<Model> models = (List<Model>) resources.keySet()
                    .stream()
                    .map(key -> new Model((JSONObject) resources.get(key)))
                    .collect(Collectors.toList());

            List<EntityRendering> results = new ArrayList<>();
            for (Model model : models) {
                results.add(new ComputeBuilder(model).build().getRendering());
            }

            return new ResponseEntity<>(new EntitiesRendering.Builder().addEntities(results).build(),
                    HttpStatus.OK);
        } catch (CloudAutomationException e) {
            logger.error(this.getClass(), e);
            return new ResponseEntity(e.getJsonError(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (SyntaxException e) {
            logger.error(this.getClass(), e);
            return new ResponseEntity(e.getUserException(), HttpStatus.BAD_REQUEST);
        }
    }


    //-------------------Retrieve Single Compute--------------------------------------------------------

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResourceRendering> getCompute(@PathVariable("id") String id) {
        logger.debug("Get Compute ");
        try {
            Optional<Model> computeModel = new CloudAutomationInstances().getInstanceByVariable(ID_NAME,
                    ConvertUtils.formatURL(id));
            if (!computeModel.isPresent()) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            } else {
                ComputeBuilder computeBuilder = new ComputeBuilder(computeModel.get());
                return new ResponseEntity<>(computeBuilder.build().getRendering(), HttpStatus.OK);
            }
        } catch (CloudAutomationException e) {
            logger.error(this.getClass(), e);
            return new ResponseEntity(e.getJsonError(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (SyntaxException e) {
            logger.error(this.getClass(), e);
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


    //-------------------Create a Compute--------------------------------------------------------

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ResourceRendering> createCompute(
            @RequestBody ResourceRendering computeRendering) throws InterruptedException, NumberFormatException {
        logger.debug("Creating Compute " + computeRendering.toString());
        try {
            ComputeBuilder compute = new ComputeBuilder(computeRendering);
            JSONObject pcaModel = compute.build().toCloudAutomationModel("create").getJson();
            Model model = new Model(new CloudAutomationInstances().postRequest(pcaModel));
            ComputeBuilder response = new ComputeBuilder(model);
            return new ResponseEntity<>(response.build().getRendering(), HttpStatus.CREATED);
        } catch (CloudAutomationException e) {
            logger.error(this.getClass(), e);
            return new ResponseEntity(e.getJsonError(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (SyntaxException e) {
            logger.error(this.getClass(), e);
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /*@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SwaggerTest> createComputeTest(@RequestBody SwaggerTest computeRendering) throws InterruptedException, NumberFormatException {
        logger.debug("Creating Compute " + computeRendering.toString());
        return new ResponseEntity<>(computeRendering,HttpStatus.OK);
    }*/


    /**
     * The following method will be added soon
     */

    //------------------- Apply an action on a Compute --------------------------------------------------------
    /*
        @RequestMapping(value = "{action}", method = RequestMethod.POST)
        public ResponseEntity<Compute> actionOnCompute(@PathVariable("action") String action, @RequestBody Compute compute) {
            logger.debug("Action "+ action+" on the Compute " + compute.getTitle());
            JSONObject pcaModel = compute.toPCAModel(action).getCloudAutomationServiceRequest();
            try {
                new CloudAutomationInstances().sendRequest(pcaModel);
            }catch (CloudAutomationException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(compute,HttpStatus.OK);
        }

        //------------------- Update a Compute --------------------------------------------------------

        @RequestMapping(value = "{id}", method = RequestMethod.PUT)
        public ResponseEntity<Compute> updateCompute(@RequestBody Compute compute) {
            logger.debug("Updating Compute " + compute.getTitle());
            JSONObject pcaModel = compute.toPCAModel("update").getCloudAutomationServiceRequest();
            try {
                String result = new CloudAutomationInstances().sendRequest(pcaModel);
            }catch (CloudAutomationException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(compute,HttpStatus.OK);
        }

        //------------------- Delete a Compute --------------------------------------------------------

        @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
        public ResponseEntity<Compute> deleteCompute(@PathVariable("id") String id, @PathVariable String sessionid) {
            logger.debug("Fetching & Deleting Compute with name " + id);
            JSONObject pcaModel = new ComputeBuilder(id,sessionid).build().toPCAModel("delete").getCloudAutomationModel();
            try{
                new CloudAutomationInstances().sendRequest(pcaModel);
            }catch (CloudAutomationException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
*/
}
