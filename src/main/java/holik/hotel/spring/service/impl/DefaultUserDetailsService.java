package holik.hotel.spring.service.impl;

import holik.hotel.spring.persistence.model.User;
import holik.hotel.spring.service.UserService;
import holik.hotel.spring.web.details.DefaultUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    public DefaultUserDetailsService() {}

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userService.getUserByEmail(email);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User with email does not exist"));
        return new DefaultUserDetails(user);
    }
}
