package org.ow2.proactive.procci.rest;


import java.io.IOException;

import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import org.ow2.proactive.procci.model.occi.metamodel.MixinBuilder;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.MixinRendering;
import org.ow2.proactive.procci.request.InstancesService;
import org.ow2.proactive.procci.request.MixinsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
 * Created by the Activeeon Team on 05/10/16.
 */
@RestController
@RequestMapping(value = PathConstant.QUERY_PATH)
public class MixinRest {

    private final Logger logger = LogManager.getRootLogger();

    @Autowired
    private InstancesService instancesService;

    @Autowired
    private MixinsService mixinsService;

    //-------------------Get a Mixin--------------------------------------------------------

    @RequestMapping(value = "{mixinTitle}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MixinRendering> getMixin(@PathVariable("mixinTitle") String mixinTitle) {
        logger.debug("Getting Mixin " + mixinTitle);

        try {
            return new ResponseEntity(
                    mixinsService.getMixinByTitle(mixinTitle).getRendering(),
                    HttpStatus.OK);
        } catch (ClientException ex) {
            return new ResponseEntity(ex.getJsonError(), HttpStatus.BAD_REQUEST);
        } catch (IOException exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //-------------------Create a Mixin--------------------------------------------------------

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<MixinRendering> createMixin(
            @RequestBody MixinRendering mixinRendering) {
        logger.debug("Creating Mixin " + mixinRendering.toString());
        try {
            Mixin mixin = new MixinBuilder(mixinsService, instancesService, mixinRendering).build();
            mixinsService.addMixin(mixin);

            return new ResponseEntity(mixin.getRendering(), HttpStatus.OK);
        } catch (IOException ex) {
            logger.error(this.getClass(), ex);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ClientException ex) {
            return new ResponseEntity(ex.getJsonError(), HttpStatus.BAD_REQUEST);
        }
    }

    //-------------------Update a Mixin--------------------------------------------------------

    @RequestMapping(value = "{mixinTitle}", method = RequestMethod.PUT)
    public ResponseEntity<MixinRendering> updateMixin(
            @PathVariable("mixinTitle") String mixinTitle,
            @RequestBody MixinRendering mixinRendering) {
        logger.debug("Updating Mixin " + mixinTitle + " with " + mixinRendering.toString());

        Mixin mixin = null;
        try {
            mixin = new MixinBuilder(mixinsService, instancesService, mixinRendering).build();
            mixinsService.addMixin(mixin);
        } catch (IOException ex) {
            logger.error(this.getClass(), ex);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ClientException ex) {
            return new ResponseEntity(ex.getJsonError(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(mixin.getRendering(), HttpStatus.OK);
    }

}
