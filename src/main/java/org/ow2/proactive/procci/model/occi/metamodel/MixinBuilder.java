package org.ow2.proactive.procci.model.occi.metamodel;

import org.ow2.proactive.procci.model.occi.infrastructure.constants.Identifiers;
import org.ow2.proactive.procci.model.occi.infrastructure.mixin.ResourceTemplate;

import java.util.*;

import static org.ow2.proactive.procci.model.occi.metamodel.constants.Identifiers.*;



/**
 * Created by mael on 22/09/16.
 */
public class MixinBuilder {

    private String scheme;
    private String term;
    private String title;
    private Set<Attribute> attributes;
    private List<Action> actions;
    private List<Mixin> depends;
    private List<Kind> applies;
    private List<Entity> entities;

    public MixinBuilder(){
        this.scheme = CORE_SCHEME;
        this.term = MIXIN;
        this.title = scheme+term;
        this.attributes = new HashSet<>();
        this.actions = new ArrayList<>();
        this.depends = new ArrayList<>();
        this.applies = new ArrayList<>();
        this.entities = new ArrayList<>();
    }

    public MixinBuilder setScheme(String scheme){
        this.scheme = scheme;
        return this;
    }

    public MixinBuilder setTerm(String term){
        this.term = term;
        return this;
    }

    public MixinBuilder setTitle(String title){
        this.title = title;
        return this;
    }

    public MixinBuilder addAttribute(Attribute attribute){
        this.attributes.add(attribute);
        return this;
    }

    public MixinBuilder addAction(Action action){
        this.actions.add(action);
        return this;
    }

    public MixinBuilder addDepend(Mixin depend){
        depends.add(depend);
        return this;
    }

    public MixinBuilder addApply(Kind apply){
        this.applies.add(apply);
        return this;
    }

    public MixinBuilder addEntity(Entity entity){
        this.entities.add(entity);
        return this;
    }

    /**
     *  Update the mixin builder according to the name of the  mixin
     * @param name is composed of the scheme and term of the mixin
     * @return the updated Builder
     */
    public MixinBuilder updateFromMixinName(String name){
        final String separator = "#";
        if(name.contains(separator)) {
            this.scheme = name.split(separator)[0] + separator;
            this.term = name.split(separator)[1];
        }
        return this;
    }

    /**
     *  Build the instance of a mixin according to its scheme and term
     * @return a mixin instance
     */
    public Mixin build(){
        switch (this.term){
            case Identifiers.RESOURCE_TEMPLATE :
                return new ResourceTemplate(entities);
            default:
                return new Mixin(scheme, term, title, attributes, actions, depends, applies, entities);
        }
    }


}
