package org.ow2.proactive.procci.model.occi.platform;

import org.ow2.proactive.procci.model.exception.SyntaxException;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class ComponentTest {

    @Test
    public void constructorTest() throws SyntaxException {

        Component component = new Component.Builder()
                .title("bob")
                .status("active")
                .build();

        assertThat(component.getTitle().get()).matches("bob");
        assertThat(component.getStatus().get()).isEquivalentAccordingToCompareTo(Status.ACTIVE);
        assertThat(component).isInstanceOf(Component.class);
    }
}
