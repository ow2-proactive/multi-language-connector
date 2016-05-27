package org.ow2.proactive.procci.model.infrastructure;

import org.ow2.proactive.procci.model.infrastructure.action.OfflineStorage;
import org.ow2.proactive.procci.model.infrastructure.action.OnlineStorage;
import org.ow2.proactive.procci.model.infrastructure.state.StorageState;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 2/25/16.
 */

public class StorageTest {

    @Test
    public void constructorTest() {
        Storage storage = new Storage.Builder("url", new Float(5))
                .title("titleTest")
                .summary("summaryTest")
                .state(StorageState.ERROR)
                .addAction(OfflineStorage.getInstance())
                .addAction(OnlineStorage.getInstance())
                .build();
        assertThat(storage.getState()).isEquivalentAccordingToCompareTo(StorageState.ERROR);
        assertThat(storage.getSummary()).isEqualTo("summaryTest");
        assertThat(storage.getTitle()).isEqualTo("titleTest");
        assertThat(storage.getActions()).containsExactly(OfflineStorage.getInstance(),
                OnlineStorage.getInstance());
    }
}
