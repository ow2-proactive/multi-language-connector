package org.ow2.proactive.procci.model.occi.metamodel.rendering;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

/**
 * Model rendering for an entity
 */
@Getter
@EqualsAndHashCode
@Builder
public class EntityRendering {

    private String kind;
    private List<String> mixins;
    private AttributeRendering attributes;
    private List<String> actions;
    private String id;
    private String title;

}
