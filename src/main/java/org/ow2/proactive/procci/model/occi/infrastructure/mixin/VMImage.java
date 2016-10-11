package org.ow2.proactive.procci.model.occi.infrastructure.mixin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ow2.proactive.procci.model.occi.infrastructure.constants.Attributes;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.Identifiers;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.occi.metamodel.Attribute;
import org.ow2.proactive.procci.model.occi.metamodel.Entity;
import org.ow2.proactive.procci.model.occi.metamodel.Kind;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import lombok.Getter;

/**
 * Created by mael on 11/10/16.
 */
@Getter
public class VMImage extends Mixin{

    private String image;

    public VMImage(String title, List<Mixin> depends, List<Entity> entities, String image){
        super(Identifiers.OCCIWARE_SCHEME,Identifiers.VM_IMAGE,title,setAttributes(),new ArrayList<>(),depends,initApplies(),entities);
        this.image = image;
    }

    private static List<Kind> initApplies(){
        List<Kind> applies = new ArrayList<>();
        applies.add(InfrastructureKinds.COMPUTE);
        return applies;
    }

    private static Set<Attribute> setAttributes(){
        Set<Attribute> attributes = new HashSet<>();
        attributes.add(Attributes.COMPUTE_IMAGE);
        return attributes;
    }
}
