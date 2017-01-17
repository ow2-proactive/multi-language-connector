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

import java.util.Set;
import java.util.UUID;

import org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelAttributes;

import com.google.common.collect.ImmutableSet;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Category is the basis type of identification mecanism
 **/
@EqualsAndHashCode(of = { "scheme", "term" })
@Data
public class Category {

    private final String scheme;

    private final String term;

    private final ImmutableSet<Attribute> attributes;

    private String title;

    /**
     * Constructor which set all the parameters
     *
     * @param scheme     is the categorisation scheme
     * @param term       is the unique identifier of the category instance
     * @param title      is the name of the instance
     * @param attributes are the category and sub-types attributes
     */
    public Category(String scheme, String term, String title, Set<Attribute> attributes) {
        if (("").equals(scheme)) {
            this.scheme = UUID.randomUUID().toString();
        } else {
            this.scheme = scheme;
        }
        this.title = title;
        this.term = term;
        this.attributes = new ImmutableSet.Builder<Attribute>().add(MetamodelAttributes.SCHEME)
                                                               .add(MetamodelAttributes.TERM)
                                                               .add(MetamodelAttributes.CATEGORY_TITLE)
                                                               .addAll(attributes)
                                                               .build();
    }
}
