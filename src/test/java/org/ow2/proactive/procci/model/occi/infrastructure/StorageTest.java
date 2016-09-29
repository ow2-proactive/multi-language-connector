package org.ow2.proactive.procci.model.occi.infrastructure;

import org.ow2.proactive.procci.model.occi.infrastructure.action.OfflineStorage;
import org.ow2.proactive.procci.model.occi.infrastructure.action.OnlineStorage;
import org.ow2.proactive.procci.model.occi.infrastructure.state.StorageState;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 2/25/16.
 */

public class StorageTest {

    @Test
    public void constructorTest() {
        Storage storage = new Storage.Builder().size(new Float(5))
                .url("url")
                .title("titleTest")
                .summary("summaryTest")
                .state(StorageState.ERROR)
                .build();
        assertThat(storage.getState()).isEquivalentAccordingToCompareTo(StorageState.ERROR);
        assertThat(storage.getSummary()).isEqualTo("summaryTest");
        assertThat(storage.getTitle()).isEqualTo("titleTest");
    }
}
