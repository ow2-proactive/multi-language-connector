package org.ow2.proactive.procci.model.occi.metamodel.rendering;

import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Model rendering for an action
 */
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class ActionRendering {

    private String term;
    private String scheme;
    private String title;
    private Map<String, Object> attributes;
}
