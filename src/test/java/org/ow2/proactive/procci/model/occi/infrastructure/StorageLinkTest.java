package org.ow2.proactive.procci.model.occi.infrastructure;

import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.occi.infrastructure.state.NetworkState;
import com.google.common.truth.Truth;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 2/25/16.
 */

public class StorageLinkTest {

    @Test
    public void constructorTest() {
        Storage storage = new Storage.Builder().size(new Float(2)).url("storage").build();
        StorageLink storageLink = new StorageLink.Builder(storage, "targetid", "deviceId")
                .url("storageLink")
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
    }
}
