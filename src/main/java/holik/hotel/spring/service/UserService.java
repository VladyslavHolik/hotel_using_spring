package holik.hotel.spring.service;

import holik.hotel.spring.persistence.model.User;

import java.util.Optional;

public interface UserService {
    void createUser(User user);
    Optional<User> getUserById(int id);
    Optional<User> getUserByEmail(String email);
}
