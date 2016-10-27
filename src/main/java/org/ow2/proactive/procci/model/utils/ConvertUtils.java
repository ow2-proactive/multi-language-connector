package org.ow2.proactive.procci.model.utils;

import java.util.Optional;

import org.ow2.proactive.procci.model.exception.SyntaxException;

/**
 * Created by the Activeeon Team on 29/09/16.
 */
public class ConvertUtils {

    /**
     * Parse a string integer into an optional integer
     *
     * @param integerString an optional containing a string
     * @return an containing an integer
     * @throws SyntaxException if the string is not null and doesn't match any integer
     */
    public static Optional<Integer> getIntegerFromString(
            Optional<String> integerString) throws SyntaxException {
        try {
            return integerString.map(s -> Integer.parseInt(s));
        } catch (Exception e) {
            throw new SyntaxException(integerString.get(),"Integer");
        }
    }

    /**
     * Parse a string float into a  integer float
     *
     * @param floatString an optional containing a string
     * @return an optional a float
     * @throws SyntaxException if the string is not null and doesn't match any float
     */
    public static Optional<Float> getFloatFromString(Optional<String> floatString) throws SyntaxException {
        try {
            return floatString.map(s -> Float.parseFloat(s));
        } catch (Exception e) {
            throw new SyntaxException(floatString.get(),"Float");
        }
    }

    public static Optional<String> getStringFromObject(Optional<Object> stringObject) throws SyntaxException {
        if (!stringObject.isPresent()) {
            return Optional.empty();
        }
        try {
            return stringObject.map(obj -> (String) obj);
        } catch (Exception e) {
            throw new SyntaxException(stringObject.toString(),"String");
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

}
