package ProcureFlow.service;

import ProcureFlow.entity.Role;
import ProcureFlow.entity.User;
import ProcureFlow.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    public User createUser(
            String username,
            String password,
            Role role
    ) {
        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User(
                username,
                passwordEncoder.encode(password),
                role
        );

        return userRepository.save(user);
    }
}