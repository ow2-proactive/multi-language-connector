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

import com.google.common.collect.ImmutableSet;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.ow2.proactive.procci.model.occi.metamodel.constants.Attributes;

import java.util.Set;
import java.util.UUID;

/**
 * Category is the basis type of identification mecanism
 **/
@EqualsAndHashCode( of =  {"scheme", "term"})
public class Category {

    @Getter
    private final String scheme;
    @Getter
    private final String term;
    @Getter
    private String title;
    @Getter
    private final ImmutableSet<Attribute> attributes;

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
        this.attributes = new ImmutableSet.Builder<Attribute>()
                .add(Attributes.SCHEME)
                .add(Attributes.TERM)
                .add(Attributes.CATEGORY_TITLE)
                .addAll(attributes)
                .build();
    }
}
