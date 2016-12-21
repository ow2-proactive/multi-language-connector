package org.ow2.proactive.procci.service.transformer;

import org.ow2.proactive.procci.model.InstanceModel;
import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.exception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public abstract class TransformerProvider {

    public static Logger logger = LoggerFactory.getLogger(TransformerProvider.class);

    public abstract TransformerType getType();
    public abstract Model toCloudAutomationModel(InstanceModel instanceModel,String actionType);

    protected static <T> T castInstanceModel(Class<T> classe, InstanceModel instanceModel){

        if (classe.isInstance(instanceModel)){
            return classe.cast(instanceModel);
        }else {
            logger.error("Error in castInstanceModel : the instance of "+instanceModel.getClass().getName() +
                    " is not an instance of "+classe.getName());
            throw new ServerException();
        }

    }

}
