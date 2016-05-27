package org.ow2.proactive.papi.model.infrastructure.mixin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ow2.proactive.papi.model.infrastructure.constants.Attributes;
import org.ow2.proactive.papi.model.infrastructure.constants.Identifiers;
import org.ow2.proactive.papi.model.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.papi.model.metamodel.Action;
import org.ow2.proactive.papi.model.metamodel.Attribute;
import org.ow2.proactive.papi.model.metamodel.Entity;
import org.ow2.proactive.papi.model.metamodel.Kind;
import org.ow2.proactive.papi.model.metamodel.Mixin;

/**
 * Created by mael on 2/25/16.
 */

/**
 * Indicated the data that will be supplied to kthe compute
 */
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
                setAttributes(), new ArrayList<Action>(), new ArrayList<Mixin>(), setApplies(), entities);
        this.userdata = userdata;
    }

    private static List<Kind> setApplies() {
        List<Kind> applies = new ArrayList<>();
        applies.add(InfrastructureKinds.COMPUTE);
        return applies;
    }

    private static Set<Attribute> setAttributes() {
        Set<Attribute> attributes = new HashSet<>();
        attributes.add(Attributes.USERDATA);
        return attributes;
    }

    public String getUserdata() {
        return userdata;
    }

    public void setUserdata(String userdata) {
        this.userdata = userdata;
    }
}
