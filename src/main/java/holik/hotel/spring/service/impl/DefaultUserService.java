package holik.hotel.spring.service.impl;

import holik.hotel.spring.persistence.model.User;
import holik.hotel.spring.persistence.repository.UserRepository;
import holik.hotel.spring.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
