package org.ow2.proactive.procci.model.occi.infrastructure.mixin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.MissingAttributesException;
import org.ow2.proactive.procci.model.exception.SyntaxException;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.Identifiers;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.occi.metamodel.Action;
import org.ow2.proactive.procci.model.occi.metamodel.Attribute;
import org.ow2.proactive.procci.model.occi.metamodel.Entity;
import org.ow2.proactive.procci.model.occi.metamodel.Kind;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import org.ow2.proactive.procci.model.occi.metamodel.MixinBuilder;

/**
 * Created by the Activeeon Team on 2/25/16.
 */

/**
 * Indicated the data that will be supplied to the compute
 */
public class Contextualization extends Mixin {

    private String userdata;


    /**
     * Constructor with all parameters
     *
     * @param userdata Contextualization data(e.g., script executable) that the client supplies once and only once. It cannot be updated
     * @param entities is the set of resource instances
     */
    public Contextualization(String title, List<Mixin> depends, List<Entity> entities, String userdata) {
        super(Identifiers.COMPUTE_SCHEME, Identifiers.CONTEXTUALIZATION, title,
                createAttributeSet(), new ArrayList<Action>(), depends, setApplies(),
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

    public String getUserdata() {
        return userdata;
    }


    @Override
    public Model.Builder toCloudAutomationModel(Model.Builder cloudAutomation) {
        cloudAutomation.addVariable(Attributes.USERDATA_NAME, this.userdata);
        return cloudAutomation;
    }

    public static class Builder extends MixinBuilder {

        public Builder() {
            super(Identifiers.COMPUTE_SCHEME, Identifiers.CONTEXTUALIZATION);
        }

        @Override
        public Contextualization build(Map attributesMap) throws ClientException {
            return new Contextualization(this.getTitle(), this.getDepends(), this.getEntities(),
                    Optional.ofNullable(
                            convertAttributeInString(attributesMap.get(Attributes.USERDATA_NAME)))
                            .orElseThrow(() -> new MissingAttributesException(Attributes.USERDATA_NAME,
                                    Attributes.USERDATA.getName())));
        }

        private String convertAttributeInString(Object attribute) throws SyntaxException {
            try {
                return (String) attribute;
            } catch (ClassCastException e) {
                throw new SyntaxException(attribute.toString());
            }
        }


    }
}
