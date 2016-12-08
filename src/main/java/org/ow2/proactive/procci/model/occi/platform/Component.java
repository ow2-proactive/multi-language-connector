package org.ow2.proactive.procci.model.occi.platform;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.ow2.proactive.procci.model.exception.SyntaxException;
import org.ow2.proactive.procci.model.occi.metamodel.Attribute;
import org.ow2.proactive.procci.model.occi.metamodel.Kind;
import org.ow2.proactive.procci.model.occi.metamodel.Link;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import org.ow2.proactive.procci.model.occi.metamodel.Resource;
import org.ow2.proactive.procci.model.occi.platform.constants.PlatformAttributes;
import org.ow2.proactive.procci.model.occi.platform.constants.PlatformKinds;
import lombok.Getter;

public class Component extends Resource {

    @Getter
    private Optional<Status> status;

    public Component(Optional<String> url, Kind kind, Optional<String> title, List<Mixin> mixins,
            Optional<String> summary, List<Link> links, Optional<Status> status) {
        super(url, kind, title, mixins, summary, links);
        this.status = status;
    }

    public Component(Optional<String> url, Kind kind, Optional<String> title, List<Mixin> mixins,
            Optional<String> summary, List<Link> links) {
        super(url, kind, title, mixins, summary, links);
        this.status = Optional.empty();
    }

    public static Set<Attribute> createAttributeSet() {
        Set<Attribute> attributeSet = new HashSet<>();
        attributeSet.addAll(Resource.createAttributeSet());
        attributeSet.add(PlatformAttributes.STATUS);
        return attributeSet;
    }

    public static class Builder extends Resource.Builder {

        protected Optional<Status> status;

        public Component.Builder status(String status) throws SyntaxException {
            this.status = Optional.ofNullable(Status.getStatusFromString(status));
            return this;
        }

        @Override
        public Component build(){
            return new Component(this.getUrl(), PlatformKinds.COMPONENT,this.getTitle(),this.getMixins(),
                    this.getSummary(),this.getLinks(),status);
        }
    }


}
