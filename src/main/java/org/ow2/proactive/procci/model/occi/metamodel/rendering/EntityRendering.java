package org.ow2.proactive.procci.model.occi.metamodel.rendering;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

/**
 * Model rendering for an entity
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
public class EntityRendering {

    private String kind;
    private List<String> mixins;
    private AttributesRendering attributes;
    private List<String> actions;
    private String id;
    private String title;

}
