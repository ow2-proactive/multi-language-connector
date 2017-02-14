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
package org.ow2.proactive.procci.rest;

import java.util.List;
import java.util.Optional;

import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.ServerException;
import org.ow2.proactive.procci.model.occi.infrastructure.Compute;
import org.ow2.proactive.procci.model.occi.infrastructure.ComputeBuilder;
import org.ow2.proactive.procci.model.occi.metamodel.Entity;
import org.ow2.proactive.procci.model.occi.metamodel.Resource;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.EntitiesRendering;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.EntityRendering;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.ResourceRendering;
import org.ow2.proactive.procci.service.occi.InstanceService;
import org.ow2.proactive.procci.service.occi.MixinService;
import org.ow2.proactive.procci.service.transformer.TransformerManager;
import org.ow2.proactive.procci.service.transformer.TransformerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Implement CRUD methods for REST service
 */
@RestController
@RequestMapping(value = PathConstant.COMPUTE_PATH)
public class ComputeRest {

    private final Logger logger = LoggerFactory.getLogger(ComputeRest.class);

    @Autowired
    private MixinService mixinService;

    @Autowired
    private InstanceService instanceService;

    @Autowired
    private TransformerManager transformerManager;

    //-------------------Retrieve All Computes--------------------------------------------------------

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EntitiesRendering> listAllComputes() {
        logger.debug("Get all Compute instances");
        try {

            List<EntityRendering> entityRenderings = instanceService.getInstancesRendering(mixinService);
            return new ResponseEntity<>(new EntitiesRendering.Builder().addEntities(entityRenderings).build(),
                                        HttpStatus.OK);
        } catch (ClientException e) {
            logger.error(this.getClass().getName(), e);
            return new ResponseEntity(e.getJsonError(), HttpStatus.BAD_REQUEST);
        } catch (ServerException e) {
            logger.error(this.getClass().getName(), e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //-------------------Retrieve Single Compute--------------------------------------------------------

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResourceRendering> getCompute(@PathVariable("id") String id) {
        logger.debug("Get Compute ");
        try {

            Optional<Entity> compute = instanceService.getEntity(id,
                                                                 transformerManager.getTransformerProvider(TransformerType.COMPUTE));
            if (!compute.isPresent()) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(((Compute) compute.get()).getRendering(), HttpStatus.OK);
            }
        } catch (ClientException e) {
            logger.error(this.getClass().getName(), e);
            return new ResponseEntity(e.getJsonError(), HttpStatus.BAD_REQUEST);
        } catch (ServerException e) {
            logger.error(this.getClass().getName(), e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //-------------------Create a Compute--------------------------------------------------------

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ResourceRendering> createCompute(@RequestBody ResourceRendering computeRendering)
            throws InterruptedException, NumberFormatException {
        logger.debug("Creating Compute " + computeRendering.toString());
        try {
            computeRendering.checkAttributes(Compute.getAttributes(), "Compute", mixinService);

            Resource response = instanceService.create(new ComputeBuilder(mixinService, computeRendering).build(),
                                                       transformerManager.getTransformerProvider(TransformerType.COMPUTE),
                                                       mixinService);
            return new ResponseEntity<>(response.getRendering(), HttpStatus.CREATED);
        } catch (ClientException e) {
            logger.error(this.getClass().getName(), e);
            return new ResponseEntity(e.getJsonError(), HttpStatus.BAD_REQUEST);
        } catch (ServerException e) {
            logger.error(this.getClass().getName(), e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
