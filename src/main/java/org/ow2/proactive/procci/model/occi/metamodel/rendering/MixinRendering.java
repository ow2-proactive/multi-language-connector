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
package org.ow2.proactive.procci.model.occi.metamodel.rendering;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.ow2.proactive.procci.model.exception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


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
