package org.ow2.proactive.procci.rest;

import java.util.HashMap;
import java.util.Map;

import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import org.ow2.proactive.procci.model.occi.metamodel.MixinBuilder;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.MixinRendering;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mael on 05/10/16.
 */
@RestController
@RequestMapping(value = Constant.QUERY_PATH)
public class QueryRest {

    private final Logger logger = LogManager.getRootLogger();


    //-------------------Create a Mixin--------------------------------------------------------

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<MixinRendering> getMixin(
            @RequestBody MixinRendering mixinRendering) {
        logger.debug("Get Mixin " + mixinRendering.toString());

        Mixin mixin = new MixinBuilder(mixinRendering).build();
        Map map = new HashMap();

        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);


    }


    //-------------------Create a Mixin--------------------------------------------------------

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<MixinRendering> createMixin(
            @RequestBody MixinRendering mixinRendering) {
        logger.debug("Creating Mixin " + mixinRendering.toString());

        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);


    }
}
