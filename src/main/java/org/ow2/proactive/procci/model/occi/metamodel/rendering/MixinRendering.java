package org.ow2.proactive.procci.model.occi.metamodel.rendering;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * Created by mael on 22/09/16.
 */
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class MixinRendering {

    private String term;
    private String scheme;
    private String title;
    private Map<String,Object> attributes;
    private List<String> actions;
    private List<String> depends;
    private List<String> applies;
    private String location;
}
