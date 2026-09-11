package ProcureFlow.controller;

import ProcureFlow.dto.LoginRequest;
import ProcureFlow.entity.User;
import ProcureFlow.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import ProcureFlow.security.JwtService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(
            UserService userService,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequest request
    ) {
        try {
            User user = userService.getUserByUsername(request.getUsername());

            if (!user.getActive()) {
                return ResponseEntity.status(403).body(
                        Map.of("message", "User account is inactive")
                );
            }

            if (!passwordEncoder.matches(
                    request.getPassword(),
                    user.getPassword()
            )) {
                return ResponseEntity.status(401).body(
                        Map.of("message", "Invalid username or password")
                );
            }

            String token = jwtService.generateToken(
                    user.getUsername(),
                    user.getRole().name()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("username", user.getUsername());
            response.put("role", user.getRole());
            response.put("token", token);

            return ResponseEntity.ok(response);

        } catch (RuntimeException exception) {
            return ResponseEntity.status(401).body(
                    Map.of("message", "Invalid username or password")
            );
        }
    }
}