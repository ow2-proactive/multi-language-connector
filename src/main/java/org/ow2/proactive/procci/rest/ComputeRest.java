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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.occi.infrastructure.Compute;
import org.ow2.proactive.procci.model.occi.infrastructure.ComputeBuilder;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.EntitiesRendering;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.EntityRendering;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.ResourceRendering;
import org.ow2.proactive.procci.model.utils.ConvertUtils;
import org.ow2.proactive.procci.request.CloudAutomationInstances;
import org.ow2.proactive.procci.request.ProviderMixin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = PathConstant.COMPUTE_PATH)
public class ComputeRest {

    private final Logger logger = LogManager.getRootLogger();

    @Autowired
    private CloudAutomationInstances cloudAutomationInstances;

    @Autowired
    private ProviderMixin providerMixin;


    //-------------------Retrieve All Computes--------------------------------------------------------

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EntitiesRendering> listAllComputes() {
        logger.debug("Get all Compute instances");
        try {
            JSONObject resources = cloudAutomationInstances.getRequest();

            List<Model> models = (List<Model>) resources.keySet()
                    .stream()
                    .map(key -> new Model((JSONObject) resources.get(key)))
                    .collect(Collectors.toList());

            List<EntityRendering> results = new ArrayList<>();
            for (Model model : models) {
                results.add(new ComputeBuilder(providerMixin, model).build().getRendering());
            }

            return new ResponseEntity<>(new EntitiesRendering.Builder().addEntities(results).build(),
                    HttpStatus.OK);
        } catch (ClientException e) {
            logger.error(this.getClass(), e);
            return new ResponseEntity(e.getJsonError(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            logger.error(this.getClass(), e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //-------------------Retrieve Single Compute--------------------------------------------------------

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResourceRendering> getCompute(@PathVariable("id") String id) {
        logger.debug("Get Compute ");
        try {
            Optional<Model> computeModel = cloudAutomationInstances.getInstanceByVariable(ID_NAME,
                    ConvertUtils.formatURL(id));
            if (!computeModel.isPresent()) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            } else {
                Compute compute = new ComputeBuilder(providerMixin, computeModel.get()).build();
                return new ResponseEntity<>(compute.getRendering(), HttpStatus.OK);
            }
        } catch (ClientException e) {
            logger.error(this.getClass(), e);
            return new ResponseEntity(e.getJsonError(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            logger.error(this.getClass(), e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    //-------------------Create a Compute--------------------------------------------------------

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ResourceRendering> createCompute(
            @RequestBody ResourceRendering computeRendering) throws InterruptedException, NumberFormatException {
        logger.debug("Creating Compute " + computeRendering.toString());
        try {
            ComputeBuilder computeBuilder = new ComputeBuilder(providerMixin, computeRendering);
            Compute response = computeBuilder.build().create(providerMixin, cloudAutomationInstances);
            return new ResponseEntity<>(response.getRendering(), HttpStatus.CREATED);
        } catch (ClientException e) {
            logger.error(this.getClass(), e);
            return new ResponseEntity(e.getJsonError(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            logger.error(this.getClass(), e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
