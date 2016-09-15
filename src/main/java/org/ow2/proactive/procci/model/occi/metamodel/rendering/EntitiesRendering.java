package org.ow2.proactive.procci.model.occi.metamodel.rendering;

import lombok.*;

import java.util.HashMap;

/**
 * Model rendering for one or several entities
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class EntitiesRendering {
    private HashMap<String,EntityRendering> entities;

    public class Builder{
        private HashMap entities;

        public Builder(){
            entities = new HashMap();
        }

        public Builder addEntity(String url, Object obj){
            entities.put(url,obj);
            return this;
        }

        public EntitiesRendering build(){
            return new EntitiesRendering(entities);
        }
    }
}
