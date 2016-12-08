package org.ow2.proactive.procci.model.occi.platform;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.ow2.proactive.procci.model.occi.metamodel.Attribute;
import org.ow2.proactive.procci.model.occi.metamodel.Kind;
import org.ow2.proactive.procci.model.occi.metamodel.Link;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import org.ow2.proactive.procci.model.occi.metamodel.Resource;
import org.ow2.proactive.procci.model.occi.platform.constants.Attributes;
import lombok.Getter;

public class Component extends Resource {

    @Getter
    private Status status;

    public Component(Optional<String> url, Kind kind, Optional<String> title, List<Mixin> mixins,
            Optional<String> summary, List<Link> links, Status status) {
        super(url, kind, title, mixins, summary, links);
        this.status = status;
    }

    public Component(Optional<String> url, Kind kind, Optional<String> title, List<Mixin> mixins,
            Optional<String> summary, List<Link> links) {
        super(url, kind, title, mixins, summary, links);
        this.status = Status.INACTIVE;
    }

    public static Set<Attribute> createAttributeSet() {
        Set<Attribute> attributeSet = new HashSet<>();
        attributeSet.addAll(Resource.createAttributeSet());
        attributeSet.add(Attributes.STATUS);
        return attributeSet;
    }


}
