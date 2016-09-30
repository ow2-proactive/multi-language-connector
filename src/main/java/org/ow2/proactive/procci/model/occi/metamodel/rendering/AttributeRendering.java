package org.ow2.proactive.procci.model.occi.metamodel.rendering;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Model rendering for an attribute
 */
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class AttributeRendering {

    private boolean mutable;
    private boolean required;
    private String type;
    private Object pattern;
    private Object defaultValue;
    private String description;

}
