package org.ow2.proactive.procci.model.occi.metamodel.rendering;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

/**
 * Model rendering for a link
 */
@Getter
@EqualsAndHashCode
@Builder
public class LinkRendering extends EntityRendering{

    private LinkLocationRendering source;
    private LinkLocationRendering target;
}
