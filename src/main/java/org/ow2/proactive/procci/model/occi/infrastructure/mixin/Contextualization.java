package org.ow2.proactive.procci.model.occi.infrastructure.mixin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.Identifiers;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.occi.metamodel.Action;
import org.ow2.proactive.procci.model.occi.metamodel.Attribute;
import org.ow2.proactive.procci.model.occi.metamodel.Entity;
import org.ow2.proactive.procci.model.occi.metamodel.Kind;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import lombok.Data;
import lombok.Getter;

/**
 * Created by the Activeeon Team on 2/25/16.
 */

/**
 * Indicated the data that will be supplied to the compute
 */
@Getter
public class Contextualization extends Mixin {

    private String userdata;


    /**
     * Constructor with all parameters
     *
     * @param userdata Contextualization data(e.g., script executable) that the client supplies once and only once. It cannot be updated
     * @param entities is the set of resource instances
     */
    public Contextualization(String userdata, List<Entity> entities) {
        super(Identifiers.INFRASTRUCTURE_SCHEME, Identifiers.CONTEXTUALIZATION, Identifiers.CONTEXTUALIZATION,
                createAttributeSet(), new ArrayList<>(), new ArrayList<>(), setApplies(),
                entities);
        this.userdata = userdata;
    }

    private static List<Kind> setApplies() {
        List<Kind> applies = new ArrayList<>();
        applies.add(InfrastructureKinds.COMPUTE);
        return applies;
    }

    private static Set<Attribute> createAttributeSet() {
        Set<Attribute> attributes = new HashSet<>();
        attributes.add(Attributes.USERDATA);
        return attributes;
    }
}
