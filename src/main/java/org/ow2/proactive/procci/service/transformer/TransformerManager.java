package org.ow2.proactive.procci.service.transformer;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TransformerManager {

    private Map<TransformerType, TransformerProvider> transformerPerType;

    @Autowired
    public TransformerManager(List<TransformerProvider> transformerProviders) {
        transformerPerType = transformerProviders.stream().collect(Collectors.toMap(TransformerProvider::getType,
                                                                                    Function.identity()));
    }

    public TransformerProvider getTransformerProvider(TransformerType transformerType) {
        return transformerPerType.get(transformerType);
    }

}
