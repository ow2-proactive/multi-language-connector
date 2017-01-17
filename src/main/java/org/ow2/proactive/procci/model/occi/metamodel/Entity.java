/*
 * ProActive Parallel Suite(TM):
 * The Open Source library for parallel and distributed
 * Workflows & Scheduling, Orchestration, Cloud Automation
 * and Big Data Analysis on Enterprise Grids & Clouds.
 *
 * Copyright (c) 2007 - 2017 ActiveEon
 * Contact: contact@activeeon.com
 *
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation: version 3 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
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
