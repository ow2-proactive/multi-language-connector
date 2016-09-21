package org.ow2.proactive.procci.model.occi.metamodel.constants;

import org.ow2.proactive.procci.model.occi.metamodel.Attribute;
import org.ow2.proactive.procci.model.occi.metamodel.Type;

/**
 * Created by mael on 3/2/16.
 */

/**
 * Contains all the attributes of the meta-model
 */
public class Attributes {

    //occi  core variable
    public static final String ENTITY_TITLE_NAME = "occi.entity.title";
    public static final String SUMMARY_NAME = "occi.core.summary";
    public static final String ID_NAME = "occi.entity.id";

    public static final Attribute TERM = new Attribute.Builder("occi.category.term", Type.OBJECT, true,
            false).description(
            "Unique identifier of the Category instance " +
                    "within the categorization scheme.").build();
    public static final Attribute SCHEME = new Attribute.Builder("occi.category.scheme", Type.OBJECT, true,
            false).description("The categorization scheme.").build();
    public static final Attribute CATEGORY_TITLE = new Attribute.Builder("occi.category.title", Type.OBJECT,
            false,
            false).description("The display name of an instance.").build();
    public static final Attribute ENTITY_TITLE = new Attribute.Builder(ENTITY_TITLE_NAME, Type.OBJECT,
            false,
            true).description("The display name of an instance.").build();
    public static final Attribute ID = new Attribute.Builder(ID_NAME, Type.OBJECT, true,
            false).description(
            "A unique identifier (within the service " +
                    "provider’s name-space) of the Entity sub-type instance.").build();
    public static final Attribute KIND = new Attribute.Builder("occi.entity.kind", Type.OBJECT, true,
            false).description(
            "The Kind instance uniquely identify" +
                    "ing the particular Entity sub-type of this instance.List of Entity instances associated " +
                    "with the Mixin instance.").build();
    public static final Attribute MIXINS = new Attribute.Builder("occi.entity.mixins", Type.LIST, false,
            false).description("Mixin instances associated to this en" +
            "tity instance. Consumers can expect the Attributes and Actions of the as" +
            "sociated Mixins to be exposed by the instance.").build();
    public static final Attribute KIND_ACTIONS = new Attribute.Builder("occi.kind.actions", Type.LIST, false,
            false).description("List of Action instances defined by the Kind instances").build();
    public static final Attribute PARENT = new Attribute.Builder("occi.kind.parent", Type.OBJECT, false,
            false).description("Another Kind instance which this Kind has an " +
            "inheritance relationship with.").build();
    public static final Attribute KIND_ENTITIES = new Attribute.Builder("occi.kind.entities", Type.LIST,
            false,
            false).description("List of Entity instances. Instances of the particular" +
            " Entity sub-type which is uniquely identified by this Kind instance.").build();
    public static final Attribute SOURCE = new Attribute.Builder("occi.link.source", Type.OBJECT, true,
            true).description("The Resource instances the Link instance " +
            "originates from.").build();
    public static final Attribute TARGET = new Attribute.Builder("occi.link.target", Type.OBJECT, true,
            true).description("The unique identifier of an Object this " +
            "Link instance points to.").build();
    public static final Attribute TARGET_KIND = new Attribute.Builder("occi.link.target.kind", Type.OBJECT,
            false,
            true).description("The Kind of target, if applicable.").build();
    public static final Attribute MIXIN_ACTIONS = new Attribute.Builder("occi.mixin.actions", Type.LIST,
            false,
            false).description("List of Action instances defined by" +
            "the Mixin instance.").build();
    public static final Attribute DEPENDS = new Attribute.Builder("occi.mixin.depends", Type.LIST, false,
            false).description("List of Mixin instances this Mixin" +
            "instance depends on.").build();
    public static final Attribute APPLIES = new Attribute.Builder("occi.mixin.applies", Type.LIST, false,
            false).description("List of Kind instances this Mixin" +
            "instance applies to.").build();
    public static final Attribute MIXIN_ENTITIES = new Attribute.Builder("occi.mixin.entities", Type.LIST,
            false,
            true).description("List of Entity instances associated" +
            "with the Mixin instance.").build();
    public static final Attribute LINKS = new Attribute.Builder("occi.resource.links", Type.LIST, false,
            true).description(
            "List of Link compositions. Being a" +
                    "composite relation the removal of a Link from the set MUST also remove" +
                    "the Link instance.").build();
    public static final Attribute SUMMARY = new Attribute.Builder(SUMMARY_NAME, Type.OBJECT, false,
            true).description("A summarizing description of the Re" +
            "source instance.").build();
}
