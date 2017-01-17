package org.ow2.proactive.procci.model.occi.metamodel.rendering;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * Model rendering for a resource link
 */
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class LinkLocationRendering {

    private String location;

    private String kind;

}
