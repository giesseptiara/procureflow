package ProcureFlow.controller;

import ProcureFlow.entity.Role;
import ProcureFlow.entity.User;
import ProcureFlow.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Map<String, Object> createUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Role role
    ) {
        User user = userService.createUser(username, password, role);

        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("username", user.getUsername());
        response.put("role", user.getRole());
        response.put("active", user.getActive());

        return response;
    }
}