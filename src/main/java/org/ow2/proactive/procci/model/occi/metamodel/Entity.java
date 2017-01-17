/*
 * ProActive Parallel Suite(TM): The Java(TM) library for
 *    Parallel, Distributed, Multi-Core Computing for
 *    Enterprise Grids & Clouds
 *
 * Copyright (C) 2013-2015 ActiveEon
 * 
 * Contact: proactive@ow2.org or contact@activeeon.com
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; version 3 of
 * the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 *
 *  Initial developer(s):               The ProActive Team
 *                        http://proactive.inria.fr/team_members.htm
 *  Contributor(s):
 *
 * $$ACTIVEEON_INITIAL_DEV$$
 */

package org.ow2.proactive.procci.model.occi.metamodel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.ow2.proactive.procci.model.InstanceModel;
import org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelAttributes;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.EntityRendering;
import org.ow2.proactive.procci.model.utils.ConvertUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


/**
 * Entity is the abstract type that will gather the information contained in Resource and Link
 */
@ToString
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@Getter
public abstract class Entity extends InstanceModel {
    private final String id;

    private final Kind kind;

    private Optional<String> title;

    private List<Mixin> mixins;

    public Entity() {
        this.id = ConvertUtils.formatURL(generateId());
        this.kind = new Kind.Builder("default.kind.url", "entity").build();
        this.title = Optional.empty();
        this.mixins = new ArrayList<>();
    }

    /**
     * Minimal constructor
     *
     * @param url  is the user url
     * @param kind is the kind instance which uniquely identify the instance
     */
    public Entity(Optional<String> url, Kind kind) {
        this.id = ConvertUtils.formatURL(url.orElse(generateId()));
        this.kind = kind;
        this.title = Optional.empty();
        this.mixins = new ArrayList<>();
    }

    /**
     * Constructor which set all parameters
     *
     * @param url    is the user url
     * @param kind   is the kind instance which uniquely identify the instance
     * @param title  is the display name of the instance
     * @param mixins are the mixins instance associate to the instance
     */
    public Entity(Optional<String> url, Kind kind, Optional<String> title, List<Mixin> mixins) {
        this.id = ConvertUtils.formatURL(url.orElse(generateId()));
        this.kind = kind;
        this.title = title;
        this.mixins = mixins;
    }

    public static Set<Attribute> getAttributes() {
        Set<Attribute> attributes = new HashSet<>();
        attributes.add(MetamodelAttributes.ID);
        attributes.add(MetamodelAttributes.ENTITY_TITLE);
        attributes.add(MetamodelAttributes.KIND);
        attributes.add(MetamodelAttributes.MIXINS);
        return attributes;
    }

    private String generateId() {
        return "urn:uuid:" + UUID.randomUUID().toString();
    }

    //WARNING : The Character '−' is not supported by the scheduler so it is replaced by the character '-'

    public String getRenderingId() {
        return this.id.replaceAll("-", "−");
    }

    public abstract EntityRendering getRendering();

}
