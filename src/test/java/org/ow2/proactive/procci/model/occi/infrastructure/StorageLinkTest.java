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
package org.ow2.proactive.procci.model.occi.infrastructure;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;
import org.ow2.proactive.procci.model.exception.SyntaxException;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.occi.infrastructure.state.NetworkState;

import com.google.common.truth.Truth;


/**
 * Created by the Activeeon team on 2/25/16.
 */

public class StorageLinkTest {

    @Test
    public void constructorTest() {
        Storage storage = new Storage.Builder().size(new Float(2)).url("storage").build();
        try {
            StorageLink storageLink = new StorageLink.Builder(storage, "targetid", "deviceId").url("storageLink")
                                                                                              .mountpoint("mountpointTest")
                                                                                              .targetKind(InfrastructureKinds.COMPUTE)
                                                                                              .title("title")
                                                                                              .state(NetworkState.ERROR)
                                                                                              .build();
            Truth.assertThat(storageLink.getSource()).isEqualTo(storage);
            Truth.assertThat(storageLink.getId().toString()).contains("storageLink");
            Truth.assertThat(storageLink.getState()).isEquivalentAccordingToCompareTo(NetworkState.ERROR);
            Truth.assertThat(storageLink.getTitle().get()).contains("title");
            assertThat(storageLink.getDeviceId()).contains("deviceId");
            Truth.assertThat(storageLink.getTarget().toString()).contains("targetid");
            assertThat(storageLink.getMountPoint()).contains("mountpointTest");
        } catch (SyntaxException e) {
            e.printStackTrace();
        }
    }
}
