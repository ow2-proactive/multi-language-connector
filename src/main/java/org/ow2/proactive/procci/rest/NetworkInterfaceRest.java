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

import org.ow2.proactive.procci.model.occi.infrastructure.NetworkInterface;
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
@RequestMapping(value = "/networkInterface/")
public class NetworkInterfaceRest {

    private final Logger logger = LogManager.getRootLogger();

    //-------------------Retrieve All NetworkInterfaces--------------------------------------------------------

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<NetworkInterface>> listAllNetworkInterfaces() {
        logger.debug("Creating all NetworkInterface instances");
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    //-------------------Retrieve Single NetworkInterface--------------------------------------------------------

    @RequestMapping(value = "{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NetworkInterface> getNetworkInterface(@PathVariable("name") String name) {
        logger.debug("Get NetworkInterface ");
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    //-------------------Create a NetworkInterface--------------------------------------------------------

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<NetworkInterface> createNetworkInterface(@RequestBody NetworkInterface storage) {
        logger.debug("Creating NetworkInterface "+ storage.getId());
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    //------------------- Update a NetworkInterface --------------------------------------------------------

    @RequestMapping(value = "{name}", method = RequestMethod.PUT)
    public ResponseEntity<NetworkInterface> updateNetworkInterface(@PathVariable("id") String id, @RequestBody NetworkInterface storage) {
        logger.debug("Updating NetworkInterface " + id);
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    //------------------- Delete a NetworkInterface --------------------------------------------------------

    @RequestMapping(value = "{name}", method = RequestMethod.DELETE)
    public ResponseEntity<NetworkInterface> deleteNetworkInterface(@PathVariable("name") String name) {
        logger.debug("Fetching & Deleting NetworkInterface with name " + name);
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    //------------------- Delete All NetworkInterfaces --------------------------------------------------------

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<NetworkInterface> deleteAllNetworkInterfaces() {
        logger.debug("Deleting All NetworkInterfaces");
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


}
