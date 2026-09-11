package ProcureFlow.config;

import ProcureFlow.entity.Role;
import ProcureFlow.entity.User;
import ProcureFlow.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeUsers(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {

            if (!userRepository.existsByUsernameIgnoreCase("admin")) {

                User admin = new User(
                        "admin",
                        passwordEncoder.encode("admin123"),
                        Role.ADMIN
                );

                userRepository.save(admin);

                System.out.println("Default admin user created.");
            }
        };
    }
}