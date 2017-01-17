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

import java.util.Optional;

import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.ServerException;
import org.ow2.proactive.procci.model.occi.metamodel.Entity;
import org.ow2.proactive.procci.model.occi.metamodel.Resource;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.ResourceRendering;
import org.ow2.proactive.procci.model.occi.platform.bigdata.Swarm;
import org.ow2.proactive.procci.model.occi.platform.bigdata.SwarmBuilder;
import org.ow2.proactive.procci.model.utils.ConvertUtils;
import org.ow2.proactive.procci.service.occi.InstanceService;
import org.ow2.proactive.procci.service.occi.MixinService;
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
@RequestMapping(value = PathConstant.SWARM_PATH)
public class SwarmRest {

    private final Logger logger = LoggerFactory.getLogger(ComputeRest.class);

    @Autowired
    private MixinService mixinService;

    @Autowired
    private InstanceService instanceService;

    //-------------------Retrieve a Swarm instance--------------------------------------------------------

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResourceRendering> getSwarm(@PathVariable("id") String id) {
        logger.debug("Get Swarm " + id);
        try {

            Optional<Entity> swarm = instanceService.getEntity(ConvertUtils.formatURL(id));
            if (!swarm.isPresent()) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(((Swarm) swarm.get()).getRendering(), HttpStatus.OK);
            }
        } catch (ClientException e) {
            logger.error(this.getClass().getName(), e);
            return new ResponseEntity(e.getJsonError(), HttpStatus.BAD_REQUEST);
        } catch (ServerException e) {
            logger.error(this.getClass().getName(), e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //-------------------Deploy Swarm--------------------------------------------------------

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ResourceRendering> createSwarm(@RequestBody ResourceRendering swarmRendering) {

        logger.debug("Deploy a swarm " + swarmRendering.toString());
        try {
            swarmRendering.checkAttributes(Swarm.getAttributes(), "Compute");
            SwarmBuilder swarmBuilder = new SwarmBuilder(mixinService, swarmRendering);
            Resource response = instanceService.create(swarmBuilder.build(), TransformerType.SWARM);
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
