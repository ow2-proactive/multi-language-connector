package org.ow2.proactive.procci.model.occi.metamodel.rendering;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Map;

/**
 * Model rendering for an entity attribute
 */
@Getter
@EqualsAndHashCode
@Builder
public class AttributesRendering {

    private Map<String,Object> attributes;
}
