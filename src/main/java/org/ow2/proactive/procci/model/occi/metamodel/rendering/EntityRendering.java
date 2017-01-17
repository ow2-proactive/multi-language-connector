package org.ow2.proactive.procci.model.occi.metamodel.rendering;

import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * Model rendering for an entity
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public abstract class EntityRendering {

    private String kind;

    private List<String> mixins;

    private Map<String, Object> attributes;

    private List<String> actions;

    private String id;
}
