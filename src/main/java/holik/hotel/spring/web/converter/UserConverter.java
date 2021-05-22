package holik.hotel.spring.web.converter;

import holik.hotel.spring.persistence.model.Role;
import holik.hotel.spring.persistence.model.User;
import holik.hotel.spring.web.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<UserDto, User> {
    private final PasswordEncoder passwordEncoder;

    public UserConverter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User covertToEntity(UserDto userDto) {
        String passwordHash = passwordEncoder.encode(userDto.getPassword());
        Role role = new Role();
        role.setId(2);
        role.setRoleName("user");
        return new User(userDto.getFirstName(), userDto.getLastName(), userDto.getPhone(), userDto.getEmail(), passwordHash, role);
    }
}
