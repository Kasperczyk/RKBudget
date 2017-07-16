package de.kasperczyk.rkbudget.user;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserTest {

    private User user;

    @Test
    public void getFullNameShouldReturnFirstNameSpaceLastName() {
        user = new User() {{
           setFirstName("Rene");
           setLastName("Kasperczyk");
        }};
        assertThat(user.getFullName(), is("Rene Kasperczyk"));
    }
}
