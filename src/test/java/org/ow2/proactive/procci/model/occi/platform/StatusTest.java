package org.ow2.proactive.procci.model.occi.platform;

import org.ow2.proactive.procci.model.exception.SyntaxException;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class StatusTest {

    @Test
    public void getStatusfromStringTest() throws SyntaxException {
        assertThat(Status.getStatusFromString("active")).isEquivalentAccordingToCompareTo(Status.ACTIVE);
        assertThat(Status.getStatusFromString("ERROR")).isEquivalentAccordingToCompareTo(Status.ERROR);
        assertThat(Status.getStatusFromString("Inactive")).isEquivalentAccordingToCompareTo(Status.INACTIVE);

        Exception ex = null;
        try {
            Status.getStatusFromString("bob");
        } catch (SyntaxException e) {
            ex = e;
        }
        assertThat(ex).isNotNull();
        assertThat(ex).isInstanceOf(SyntaxException.class);
    }
}
