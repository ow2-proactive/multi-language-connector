package org.ow2.proactive.procci.model;

import java.util.HashSet;
import java.util.Set;

import org.ow2.proactive.procci.fixtures.UserFixture;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Iaroslav on 4/29/2016.
 */
public class UserTest {

    @Test
    public void testHashcodeEquals(){

        Set<User> users = new HashSet<>();
        User user = UserFixture.simpleUser();
        users.add(user);
        users.add(user);
        users.add(user);

        assertThat(users.size(), is(1));
    }
}
