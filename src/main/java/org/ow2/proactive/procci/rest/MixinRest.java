package org.ow2.proactive.procci.rest;


import java.io.IOException;
import java.net.URI;

import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import org.ow2.proactive.procci.model.occi.metamodel.MixinBuilder;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.MixinRendering;
import org.ow2.proactive.procci.request.CloudAutomationException;
import org.ow2.proactive.procci.request.CloudAutomationVariables;
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
 * Created by mael on 05/10/16.
 */
@RestController
@RequestMapping(value = Constant.QUERY_PATH)
public class MixinRest {

    private final Logger logger = LogManager.getRootLogger();

    //-------------------Get a Mixin--------------------------------------------------------

    @RequestMapping(value = "{mixinTitle}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MixinRendering> getMixin(@PathVariable("mixinTitle") String mixinTitle) {
        logger.debug("Getting Mixin " + mixinTitle);

        try {
            String mixinString = CloudAutomationVariables.get(mixinTitle);
            MixinRendering mixinRendering = MixinRendering.convertMixinFromString(mixinString);
            return new ResponseEntity(new MixinBuilder(mixinRendering).build().getRendering(), HttpStatus.OK);
        } catch (CloudAutomationException ex) {
            return new ResponseEntity(ex.getJsonError(), HttpStatus.NOT_FOUND);
        } catch (IOException exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //-------------------Create a Mixin--------------------------------------------------------

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<URI> createMixin(
            @RequestBody MixinRendering mixinRendering) {
        logger.debug("Creating Mixin " + mixinRendering.toString());

        try {
            Mixin mixin = new MixinBuilder(mixinRendering).build();
            String json = MixinRendering.convertStringFromMixin(mixin.getRendering());
            CloudAutomationVariables.post(mixin.getTitle(), json);
            return new ResponseEntity(mixin.getRendering(), HttpStatus.OK);
        } catch (IOException ex) {
            logger.error(this.getClass(), ex);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (CloudAutomationException ex) {
            return new ResponseEntity(ex.getJsonError(), HttpStatus.BAD_REQUEST);
        }
    }

    //-------------------Update a Mixin--------------------------------------------------------

    @RequestMapping(value = "{mixinTitle}", method = RequestMethod.PUT)
    public ResponseEntity updateMixin(
            @PathVariable("mixinTitle") String mixinTitle,
            @RequestBody MixinRendering mixinRendering) {
        logger.debug("Updating Mixin " + mixinTitle + " with " + mixinRendering.toString());

        Mixin mixin = null;
        try {
            mixin = new MixinBuilder(mixinRendering).build();
            String json = MixinRendering.convertStringFromMixin(mixin.getRendering());

            if(mixin.getTitle().matches(mixinTitle)) {
                CloudAutomationVariables.update(mixin.getTitle(), json);
            }
            else {
                CloudAutomationVariables.delete(mixinTitle);
                CloudAutomationVariables.post(mixin.getTitle(),json);
            }
        } catch (IOException ex) {
            logger.error(this.getClass(), ex);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (CloudAutomationException ex) {
            return new ResponseEntity(ex.getJsonError(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(mixin.getRendering(), HttpStatus.OK);
    }

    //-------------------Delete a Mixin--------------------------------------------------------

    @RequestMapping(value = "{mixinTitle}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMixin(
            @PathVariable("mixinTitle") String mixinTitle) {
        logger.debug("Deleting Mixin " + mixinTitle);

        try {
            CloudAutomationVariables.delete(mixinTitle);
        } catch (CloudAutomationException ex) {
            return new ResponseEntity(ex.getJsonError(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }


}