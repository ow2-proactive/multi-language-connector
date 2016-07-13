package org.ow2.proactive.procci.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.json.simple.JSONObject;

/**
 * Created by mael on 21/06/16.
 */

/**
 * The Exception occur when an http request fail with an other Cloud Automation microservice
 */
@Getter
@AllArgsConstructor
@ToString
public class CloudAutomationException extends Exception {
    private JSONObject jsonError;
}
