package org.ow2.proactive.procci.model.occi.metamodel.rendering;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ow2.proactive.procci.model.exception.ServerException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by the Activeeon Team on 22/09/16.
 */
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder(builderClassName = "Builder")
@Api(description = "Mixin is an extension mecanism which enables to new resource capablilities")
public class MixinRendering {

    public static Logger logger = LoggerFactory.getLogger(MixinRendering.class);

    @ApiModelProperty(required = true)
    private String term;
    @ApiModelProperty(required = true)
    private String scheme;
    private String title;
    private Map<String, AttributeRendering> attributes;
    private List<String> actions;
    private List<String> depends;
    private List<String> applies;
    @ApiModelProperty(hidden = true)
    private String location;
    @ApiModelProperty(hidden = true)
    private Set<String> entities;

    public static MixinRendering convertMixinFromString(String mixinRendering) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(mixinRendering, MixinRendering.class);
        } catch (IOException ex) {
            logger.error("IO Exception in MixinRendering :", ex.getMessage());
            throw new ServerException();
        }
    }

    public static String convertStringFromMixin(MixinRendering mixinRendering) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(mixinRendering);
        } catch (IOException ex) {
            logger.error("IO Exception in MixinRendering :", ex.getMessage());
            throw new ServerException();
        }
    }
}
