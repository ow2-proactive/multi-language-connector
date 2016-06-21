package org.ow2.proactive.procci.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by mael on 21/06/16.
 */
@Getter @AllArgsConstructor @EqualsAndHashCode @ToString
public class HTTPException extends Exception {
    private String message;
}
