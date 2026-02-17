package com.codecamp.payroll_management_system.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codecamp.payroll_management_system.dto.AuthRequestDTO;
import com.codecamp.payroll_management_system.dto.AuthResponseDTO;
import com.codecamp.payroll_management_system.model.User;
import com.codecamp.payroll_management_system.services.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.register(user);
    }

    @PostMapping("/register/bulk")
    public List<User> registerBulk(@RequestBody List<User> users) {
        return service.registerBulk(users);
    }
    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody AuthRequestDTO request) {

        User user = service.findByUsername(request.getUsername());

        if (user == null || !user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Temporary token (until JWT added)
        return new AuthResponseDTO("dummy-token");
    }

}
