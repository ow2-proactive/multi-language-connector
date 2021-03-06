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
package org.ow2.proactive.procci.model.utils;

import java.io.IOException;
import java.util.Optional;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.ow2.proactive.procci.model.exception.ServerException;
import org.ow2.proactive.procci.model.exception.SyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by the Activeeon Team on 29/09/16.
 */
public class ConvertUtils {

    public static Logger logger = LoggerFactory.getLogger(ConvertUtils.class);

    /**
     * Parse a string integer into an optional integer
     *
     * @param integerString an optional containing a string
     * @return an containing an integer
     * @throws SyntaxException if the string is not null and doesn't match any integer
     */
    public static Optional<Integer> convertIntegerFromString(Optional<String> integerString) throws SyntaxException {
        try {
            return integerString.map(s -> Integer.parseInt(s));
        } catch (Exception e) {
            throw new SyntaxException(integerString.get(), "Integer");
        }
    }

    /**
     * Parse a string float into a  integer float
     *
     * @param floatString an optional containing a string
     * @return an optional a float
     * @throws SyntaxException if the string is not null and doesn't match any float
     */
    public static Optional<Float> convertFloatFromString(Optional<String> floatString) throws SyntaxException {
        try {
            return floatString.map(s -> Float.parseFloat(s));
        } catch (Exception e) {
            throw new SyntaxException(floatString.get(), "Float");
        }
    }

    public static Optional<String> convertStringFromObject(Optional<Object> stringObject) throws SyntaxException {
        if (!stringObject.isPresent()) {
            return Optional.empty();
        }
        try {
            return stringObject.map(obj -> (String) obj);
        } catch (Exception e) {
            throw new SyntaxException(stringObject.toString(), "String");
        }
    }

    /**
     * Convert fan UUID into a close format managed by Cloud Automation
     *
     * @param url and urn:uuid:(UUID) format
     * @return an UUID with the unsupported character replaced
     */
    public static String formatURL(String url) {
        return url.replaceAll("−", "-");
    }

    public static String mapObject(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (IOException ex) {
            logger.error("IO Exception in " + ConvertUtils.class.getName() + " :", ex);
            throw new ServerException();
        }

    }

    public static <T> T readMappedObject(String references, TypeReference<T> mapType) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(references, mapType);
        } catch (IOException ex) {
            logger.error("IO Exception in " + ConvertUtils.class.getName() + " :", ex);
            throw new ServerException();
        }
    }
}
