package org.ow2.proactive.procci.model.occi.metamodel.rendering;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

/**
 * Created by mael on 14/09/16.
 */
@Getter
@EqualsAndHashCode
@Builder
public class ResourceRendering extends EntityRendering{

    private List<LinkRendering> links;
    private String summary;

}
