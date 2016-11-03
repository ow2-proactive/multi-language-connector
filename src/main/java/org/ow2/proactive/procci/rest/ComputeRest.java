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
import java.util.List;
import java.util.Optional;

import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.occi.infrastructure.Compute;
import org.ow2.proactive.procci.model.occi.infrastructure.ComputeBuilder;
import org.ow2.proactive.procci.model.occi.metamodel.Entity;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.EntitiesRendering;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.EntityRendering;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.ResourceRendering;
import org.ow2.proactive.procci.model.utils.ConvertUtils;
import org.ow2.proactive.procci.request.InstanceService;
import org.ow2.proactive.procci.request.MixinService;
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


    //-------------------Retrieve All Computes--------------------------------------------------------

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EntitiesRendering> listAllComputes() {
        logger.debug("Get all Compute instances");
        try {

            List<EntityRendering> entityRenderings = instanceService.getInstancesRendering();
            return new ResponseEntity<>(new EntitiesRendering.Builder().addEntities(entityRenderings).build(),
                    HttpStatus.OK);
        } catch (ClientException e) {
            logger.error(this.getClass().getName(), e);
            return new ResponseEntity(e.getJsonError(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            logger.error(this.getClass().getName(), e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //-------------------Retrieve Single Compute--------------------------------------------------------

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResourceRendering> getCompute(@PathVariable("id") String id) {
        logger.debug("Get Compute ");
        try {

            Optional<Entity> compute = instanceService.getEntity(ConvertUtils.formatURL(id));
            if (!compute.isPresent()) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(((Compute) compute.get()).getRendering(), HttpStatus.OK);
            }
        } catch (ClientException e) {
            logger.error(this.getClass().getName(), e);
            return new ResponseEntity(e.getJsonError(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            logger.error(this.getClass().getName(), e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    //-------------------Create a Compute--------------------------------------------------------

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ResourceRendering> createCompute(
            @RequestBody ResourceRendering computeRendering) throws InterruptedException, NumberFormatException {
        logger.debug("Creating Compute " + computeRendering.toString());
        try {
            ComputeBuilder computeBuilder = new ComputeBuilder(mixinService, computeRendering);
            Compute response = instanceService.create(computeBuilder.build());
            return new ResponseEntity<>(response.getRendering(), HttpStatus.CREATED);
        } catch (ClientException e) {
            logger.error(this.getClass().getName(), e);
            return new ResponseEntity(e.getJsonError(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            logger.error(this.getClass().getName(), e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
