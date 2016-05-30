/*
 *  *
 * ProActive Parallel Suite(TM): The Java(TM) library for
 *    Parallel, Distributed, Multi-Core Computing for
 *    Enterprise Grids & Clouds
 *
 * Copyright (C) 1997-2015 INRIA/University of
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

import java.util.Collection;

import org.ow2.proactive.procci.model.occi.infrastructure.Compute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
@RequestMapping(value = "/compute/")
public class ComputeRest {

        private final Logger logger = LogManager.getRootLogger();

        //-------------------Retrieve All Computes--------------------------------------------------------

        @RequestMapping(method = RequestMethod.GET)
        public ResponseEntity<Collection<Compute>> listAllComputes() {
            logger.debug("Creating all Compute instances");
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }


        //-------------------Retrieve Single Compute--------------------------------------------------------

        @RequestMapping(value = "{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Compute> getCompute(@PathVariable("name") String name) {
            logger.debug("Get Compute ");
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

        }


        //-------------------Create a Compute--------------------------------------------------------

        @RequestMapping(method = RequestMethod.POST)
        public ResponseEntity<Compute> createCompute(@RequestBody Compute compute) {
            logger.debug("Creating Compute "+ compute.getId());
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }


        //------------------- Update a Compute --------------------------------------------------------

        @RequestMapping(value = "{name}", method = RequestMethod.PUT)
        public ResponseEntity<Compute> updateCompute(@PathVariable("id") String id, @RequestBody Compute compute) {
            logger.debug("Updating Compute " + id);
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }

        //------------------- Delete a Compute --------------------------------------------------------

        @RequestMapping(value = "{name}", method = RequestMethod.DELETE)
        public ResponseEntity<Compute> deleteCompute(@PathVariable("name") String name) {
            logger.debug("Fetching & Deleting Compute with name " + name);
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }


        //------------------- Delete All Computes --------------------------------------------------------

        @RequestMapping(method = RequestMethod.DELETE)
        public ResponseEntity<Compute> deleteAllComputes() {
            logger.debug("Deleting All Computes");
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }

    }
