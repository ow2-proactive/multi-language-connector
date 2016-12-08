package org.ow2.proactive.procci.model.occi.metamodel.constants;

import org.ow2.proactive.procci.model.occi.metamodel.Attribute;
import org.ow2.proactive.procci.model.occi.metamodel.Type;

/**
 * Created by the Activeeon Team on 3/2/16.
 */

/**
 * Contains all the attributes of the meta-model
 */
public class Attributes {

    //occi  core variables
    public static final String TERM_NAME = "occi.category.term";
    public static final String SCHEME_NAME = "occi.category.scheme";
    public static final String CATEGORY_TITLE_NAME = "occi.category.title";
    public static final String ENTITY_TITLE_NAME = "occi.entity.title";
    public static final String ID_NAME = "occi.entity.id";
    public static final String KIND_NAME = "occi.entity.kind";
    public static final String MIXINS_NAME = "occi.entity.mixins";
    public static final String KIND_ACTIONS_NAME = "occi.kind.actions";
    public static final String PARENT_NAME = "occi.kind.parent";
    public static final String KIND_ENTITIES_NAME = "occi.kind.entities";
    public static final String SOURCE_NAME = "occi.link.source";
    public static final String TARGET_NAME = "occi.link.target";
    public static final String TARGET_KIND_NAME = "occi.link.target.kind";
    public static final String MIXIN_ACTIONS_NAME = "occi.mixin.actions";
    public static final String DEPENDS_NAME = "occi.mixin.depends";
    public static final String APPLIES_NAME = "occi.mixin.applies";
    public static final String MIXIN_ENTITES_NAME = "occi.mixin.entities";
    public static final String LINKS_NAME = "occi.resource.links";
    public static final String SUMMARY_NAME = "occi.core.summary";

    //occi core attributes
    public static final Attribute TERM = new Attribute.Builder(TERM_NAME).type(Type.OBJECT).mutable(
            true).required(false).description(
            "Unique identifier of the Category instance " +
                    "within the categorization scheme.").build();
    public static final Attribute SCHEME = new Attribute.Builder(SCHEME_NAME).type(Type.OBJECT).mutable(
            true).required(false).description("The categorization scheme.").build();
    public static final Attribute CATEGORY_TITLE = new Attribute.Builder(CATEGORY_TITLE_NAME).type(
            Type.OBJECT).mutable(false).required(false).description(
            "The display name of an instance.").build();
    public static final Attribute ENTITY_TITLE = new Attribute.Builder(ENTITY_TITLE_NAME).type(
            Type.OBJECT).mutable(false).required(true).description(
            "The display name of an instance.").build();
    public static final Attribute ID = new Attribute.Builder(ID_NAME).type(Type.OBJECT).mutable(
            true).required(false).description(
            "A unique identifier (within the service " +
                    "provider’s name-space) of the Entity sub-type instance.").build();
    public static final Attribute KIND = new Attribute.Builder(KIND_NAME).type(Type.OBJECT).mutable(
            true).required(false).description(
            "The Kind instance uniquely identify" +
                    "ing the particular Entity sub-type of this instance.List of Entity instances associated " +
                    "with the Mixin instance.").build();
    public static final Attribute MIXINS = new Attribute.Builder(MIXINS_NAME).type(Type.LIST).mutable(
            false).required(false).description("Mixin instances associated to this en" +
            "tity instance. Consumers can expect the BigDataAttributes and Actions of the as" +
            "sociated Mixins to be exposed by the instance.").build();
    public static final Attribute KIND_ACTIONS = new Attribute.Builder(KIND_ACTIONS_NAME).type(
            Type.LIST).mutable(false).required(false).description(
            "List of Action instances defined by the Kind instances").build();
    public static final Attribute PARENT = new Attribute.Builder(PARENT_NAME).type(Type.OBJECT).mutable(
            false).required(false).description("Another Kind instance which this Kind has an " +
            "inheritance relationship with.").build();
    public static final Attribute KIND_ENTITIES = new Attribute.Builder(KIND_ENTITIES_NAME).type(
            Type.LIST).mutable(false).required(false).description(
            "List of Entity instances. Instances of the particular" +
                    " Entity sub-type which is uniquely identified by this Kind instance.").build();
    public static final Attribute SOURCE = new Attribute.Builder(SOURCE_NAME).type(Type.OBJECT).mutable(
            true).required(true).description("The Resource instances the Link instance " +
            "originates from.").build();
    public static final Attribute TARGET = new Attribute.Builder(TARGET_NAME).type(Type.OBJECT).mutable(
            true).required(true).description("The unique identifier of an Object this " +
            "Link instance points to.").build();
    public static final Attribute TARGET_KIND = new Attribute.Builder(TARGET_KIND_NAME).type(
            Type.OBJECT).mutable(false).required(true).description(
            "The Kind of target, if applicable.").build();
    public static final Attribute MIXIN_ACTIONS = new Attribute.Builder(MIXIN_ACTIONS_NAME).type(
            Type.LIST).mutable(false).required(false).description("List of Action instances defined by" +
            "the Mixin instance.").build();
    public static final Attribute DEPENDS = new Attribute.Builder(DEPENDS_NAME).type(Type.LIST).mutable(
            false).required(false).description("List of Mixin instances this Mixin" +
            "instance depends on.").build();
    public static final Attribute APPLIES = new Attribute.Builder(APPLIES_NAME).type(Type.LIST).mutable(
            false).required(false).description("List of Kind instances this Mixin" +
            "instance applies to.").build();
    public static final Attribute MIXIN_ENTITIES = new Attribute.Builder(MIXIN_ENTITES_NAME).type(
            Type.LIST).mutable(false).required(true).description("List of Entity instances associated" +
            "with the Mixin instance.").build();
    public static final Attribute LINKS = new Attribute.Builder(LINKS_NAME).type(Type.LIST).mutable(
            false).required(true).description(
            "List of Link compositions. Being a" +
                    "composite relation the removal of a Link from the set MUST also remove" +
                    "the Link instance.").build();
    public static final Attribute SUMMARY = new Attribute.Builder(SUMMARY_NAME).type(Type.OBJECT).mutable(
            false).required(true).description("A summarizing description of the Re" +
            "source instance.").build();
}
