package holik.hotel.spring.service.impl;

import holik.hotel.spring.persistence.model.User;
import holik.hotel.spring.persistence.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DefaultUserService userService;

    @Test
    public void createUser() {
        User user = new User();
        userService.createUser(user);
        verify(userRepository).save(user);
    }

    @Test
    public void getUserById() {
        Optional<User> optionalUser = Optional.of(new User());
        when(userRepository.findById(5)).thenReturn(optionalUser);
        Optional<User> result = userService.getUserById(5);

        verify(userRepository).findById(5);
        assertEquals(optionalUser, result);
    }

    @Test
    public void getUserByEmail() {
        Optional<User> optionalUser = Optional.of(new User());
        when(userRepository.findByEmail("user@gmail.com")).thenReturn(optionalUser);
        Optional<User> result = userService.getUserByEmail("user@gmail.com");

        verify(userRepository).findByEmail("user@gmail.com");
        assertEquals(optionalUser, result);
    }
}