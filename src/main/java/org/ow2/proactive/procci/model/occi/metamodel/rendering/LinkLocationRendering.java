package org.ow2.proactive.procci.model.occi.metamodel.rendering;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;


/**
 * Model rendering for a resource link
 */
@Getter
@EqualsAndHashCode
@Builder
public class LinkLocationRendering {

    private String location;
    private String kind;

}
