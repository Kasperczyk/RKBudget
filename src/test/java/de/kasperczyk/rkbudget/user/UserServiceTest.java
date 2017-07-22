package de.kasperczyk.rkbudget.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepositoryMock;

    @Before
    public void setup() {
        userService = new UserService(userRepositoryMock);
    }

    @Test
    public void existsShouldReturnTrueIfTheUserAlreadyExists() {
        when(userRepositoryMock.findByEmail(anyString())).thenReturn(new User());
        boolean result = userService.exists(new User());
        assertThat(result, is(true));
    }

    @Test
    public void existsShouldReturnFalseIfTheUserDoesNotAlreadyExists() {
        when(userRepositoryMock.findByEmail(anyString())).thenReturn(null);
        boolean result = userService.exists(new User());
        assertThat(result, is(false));
    }

    @Test
    public void addUserShouldSaveTheUser() {
        User user = new User();
        userService.addUser(user);
        verify(userRepositoryMock).save(user);
    }
}
