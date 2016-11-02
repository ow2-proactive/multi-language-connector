package org.ow2.proactive.procci.model.occi.infrastructure;

import org.ow2.proactive.procci.model.occi.infrastructure.state.StorageState;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by the Activeeon team on 2/25/16.
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
        assertThat(storage.getSummary().get()).isEqualTo("summaryTest");
        assertThat(storage.getTitle().get()).isEqualTo("titleTest");
    }
}
