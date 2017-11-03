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

import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.NotFoundException;
import org.ow2.proactive.procci.model.exception.ServerException;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import org.ow2.proactive.procci.model.occi.metamodel.MixinBuilder;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.MixinRendering;
import org.ow2.proactive.procci.service.occi.InstanceService;
import org.ow2.proactive.procci.service.occi.MixinService;
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
import org.springframework.web.client.HttpClientErrorException;


/**
 * Created by the Activeeon Team on 05/10/16.
 */
@RestController
@RequestMapping(value = PathConstant.QUERY_PATH)
public class MixinRest {

    private final Logger logger = LoggerFactory.getLogger(MixinRest.class);

    @Autowired
    private InstanceService instanceService;

    @Autowired
    private MixinService mixinService;

    //-------------------Get a Mixin--------------------------------------------------------

    @RequestMapping(value = "{mixinTitle}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MixinRendering> getMixin(@PathVariable("mixinTitle") String mixinTitle) {
        logger.debug("Getting Mixin " + mixinTitle);

        try {
            return new ResponseEntity(mixinService.getMixinByTitle(mixinTitle).getRendering(), HttpStatus.OK);
        } catch (ClientException ex) {
            return new ResponseEntity(ex.getJsonError(), HttpStatus.BAD_REQUEST);
        } catch (ServerException exception) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                                      HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (HttpClientErrorException ex) {
            logger.info("Mixin not found", ex);
            return new ResponseEntity(new NotFoundException().getJsonError(), HttpStatus.NOT_FOUND);
        }
    }

    //-------------------Create a Mixin--------------------------------------------------------

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<MixinRendering> createMixin(@RequestBody MixinRendering mixinRendering) {
        logger.debug("Creating Mixin " + mixinRendering.toString());
        try {
            Mixin mixin = new MixinBuilder(mixinService, instanceService, mixinRendering).build();
            mixinService.addMixin(mixin);

            return new ResponseEntity(mixin.getRendering(), HttpStatus.OK);
        } catch (ServerException ex) {
            logger.error(this.getClass().getName(), ex);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ClientException ex) {
            return new ResponseEntity(ex.getJsonError(), HttpStatus.BAD_REQUEST);
        }
    }

    //-------------------Update a Mixin--------------------------------------------------------

    @RequestMapping(value = "{mixinTitle}", method = RequestMethod.PUT)
    public ResponseEntity<MixinRendering> updateMixin(@PathVariable("mixinTitle") String mixinTitle,
            @RequestBody MixinRendering mixinRendering) {
        logger.debug("Updating Mixin " + mixinTitle + " with " + mixinRendering.toString());

        Mixin mixin = null;
        try {
            mixin = new MixinBuilder(mixinService, instanceService, mixinRendering).build();
            mixinService.addMixin(mixin);
        } catch (ServerException ex) {
            logger.error(this.getClass().getName(), ex);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ClientException ex) {
            return new ResponseEntity(ex.getJsonError(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(mixin.getRendering(), HttpStatus.OK);
    }

    //-------------------Remove a Mixin--------------------------------------------------------
    @RequestMapping(value = "{mixinTitle}", method = RequestMethod.DELETE)
    public ResponseEntity<MixinRendering> removeMixin(@PathVariable("mixinTitle") String mixinTitle) {
        logger.debug("Deleting Mixin " + mixinTitle);

        Mixin mixin = null;
        try {
            mixin = mixinService.getMixinByTitle(mixinTitle);
            mixinService.removeMixin(mixinTitle);
        } catch (ServerException ex) {
            logger.error(this.getClass().getName(), ex);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ClientException ex) {
            return new ResponseEntity(ex.getJsonError(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(mixin.getRendering(), HttpStatus.OK);
    }

}
