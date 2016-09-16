package org.ow2.proactive.procci.model.occi.metamodel.rendering;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Map;

/**
 * Model rendering for an action
 */
@Getter
@EqualsAndHashCode
@Builder
public class ActionRendering {

    private String term;
    private String scheme;
    private String title;
    private Map<String,Object> attributes;
}
