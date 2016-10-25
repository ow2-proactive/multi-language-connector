package org.ow2.proactive.procci.model.occi.metamodel;

import java.io.IOException;
import java.util.Optional;

import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.CloudAutomationException;
import org.ow2.proactive.procci.model.exception.SyntaxException;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.Identifiers;
import org.ow2.proactive.procci.model.occi.infrastructure.mixin.VMImage;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.MixinRendering;
import org.ow2.proactive.procci.request.DataServices;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by mael on 12/10/16.
 */
@Component
public class ProviderMixin {

    private final ImmutableMap<String, Supplier<MixinBuilder>> providerMixin;
    @Autowired
    private DataServices dataServices;

    public ProviderMixin() {
        providerMixin = new ImmutableMap.Builder<String, Supplier<MixinBuilder>>()
                .put(Identifiers.VM_IMAGE, (() -> new VMImage.Builder(this)))
                .build();
    }

    public Optional<MixinBuilder> getInstance(String mixinName) {
        return Optional.ofNullable(providerMixin.get(mixinName)).map(supplier -> supplier.get());
    }

    public Mixin getMixinByTitle(String title) throws IOException, ClientException {


        String mixinString = null;
        try {
            mixinString = dataServices.get(title);
        } catch (CloudAutomationException ex) {
            throw new SyntaxException(title);
        }

        MixinRendering mixinRendering = MixinRendering.convertMixinFromString(mixinString);
        return new MixinBuilder(mixinRendering).build();
    }

}
