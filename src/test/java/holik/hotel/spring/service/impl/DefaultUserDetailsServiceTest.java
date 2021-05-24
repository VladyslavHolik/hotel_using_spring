package holik.hotel.spring.service.impl;

import holik.hotel.spring.persistence.model.User;
import holik.hotel.spring.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUserDetailsServiceTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private DefaultUserDetailsService userDetailsService;

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameIfUserDoesNotExist() {
        when(userService.getUserByEmail("user@gmail.com")).thenReturn(Optional.empty());
        userDetailsService.loadUserByUsername("user@gmail.com");
    }

    @Test
    public void loadUserByUsername() {
        when(userService.getUserByEmail("existinguser@gmail.com")).thenReturn(Optional.of(new User()));
        userDetailsService.loadUserByUsername("existinguser@gmail.com");
    }
}