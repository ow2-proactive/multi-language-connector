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
import org.ow2.proactive.procci.model.occi.metamodel.Attribute;
import org.ow2.proactive.procci.model.occi.metamodel.Entity;
import org.ow2.proactive.procci.model.occi.metamodel.Kind;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import org.ow2.proactive.procci.model.occi.metamodel.MixinBuilder;
import org.ow2.proactive.procci.request.ProviderMixin;
import lombok.Getter;

/**
 * Created by the Activeeon Team on 11/10/16.
 */
@Getter
public class VMImage extends Mixin {

    private String image;

    public VMImage(String title, List<Mixin> depends, List<Entity> entities, String image) {
        super(Identifiers.OCCIWARE_SCHEME, Identifiers.VM_IMAGE, title, setAttributes(), new ArrayList<>(),
                depends, initApplies(), entities);
        this.image = image;
    }

    private static List<Kind> initApplies() {
        List<Kind> applies = new ArrayList<>();
        applies.add(InfrastructureKinds.COMPUTE);
        return applies;
    }

    private static Set<Attribute> setAttributes() {
        Set<Attribute> attributes = new HashSet<>();
        attributes.add(Attributes.COMPUTE_IMAGE);
        return attributes;
    }

    @Override
    public Model.Builder toCloudAutomationModel(Model.Builder cloudAutomation) {
        cloudAutomation.addVariable(Attributes.COMPUTE_IMAGE_NAME, image);
        return cloudAutomation;
    }

    public static class Builder extends MixinBuilder {

        public Builder(ProviderMixin providerMixin) {
            super(providerMixin, Identifiers.OCCIWARE_SCHEME, Identifiers.VM_IMAGE);
        }

        @Override
        public Mixin build(Map attributesMap) throws ClientException {
            return new VMImage(this.getTitle(), this.getDepends(), this.getEntities(),
                    Optional.ofNullable(
                            convertAttributeInString(attributesMap.get(Attributes.COMPUTE_IMAGE_NAME)))
                            .orElseThrow(() -> new MissingAttributesException(Attributes.COMPUTE_IMAGE_NAME,
                                    Attributes.COMPUTE_IMAGE.getName())));
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
