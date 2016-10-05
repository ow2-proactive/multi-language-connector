package org.ow2.proactive.procci.model.occi.metamodel.rendering;

import java.util.List;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Created by mael on 22/09/16.
 */
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class MixinRendering {

    @NonNull
    private String term;
    @NonNull
    private String scheme;
    private String title;
    private Map<String, AttributeRendering> attributes;
    private List<String> actions;
    private List<String> depends;
    private List<String> applies;
    private String location;
}
