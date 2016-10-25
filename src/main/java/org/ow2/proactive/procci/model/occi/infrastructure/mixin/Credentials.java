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

/**
 * Created by the Activeeon Team on 2/25/16.
 */

/**
 * Protect the access of a compute thanks to an ssh public key
 */
public class Credentials extends Mixin {

    private String publickey;

    /**
     * Constructor with all the parameters
     *
     * @param publickey The contents of the public key file to be injected into the Compute Resource
     * @param entities  is the set of resource instances
     */
    public Credentials(String publickey, List<Entity> entities) {
        super(Identifiers.CREDENTIALS_SCHEME, Identifiers.CREDENTIALS, Identifiers.CREDENTIALS,
                createAttributesSet(), new ArrayList<Action>(), new ArrayList<Mixin>(), setApplies(),
                entities);
        this.publickey = publickey;
    }

    private static List<Kind> setApplies() {
        List<Kind> applies = new ArrayList<>();
        applies.add(InfrastructureKinds.COMPUTE);
        return applies;
    }

    private static Set<Attribute> createAttributesSet() {
        Set<Attribute> attributes = new HashSet<>();
        attributes.add(Attributes.SSH_PUBLICKEY);
        return attributes;
    }

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }
}
