package com.task.user_management.controller;

import com.task.user_management.entity.User;
import com.task.user_management.service.UserService;
import com.task.user_management.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    private final UserService service;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    public AuthController(UserService service, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.service = service;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(service.register(user));
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        User u = service.findByEmail(user.getEmail());
        if (u != null && encoder.matches(user.getPassword(), u.getPassword())) {
            String token = jwtUtil.generateToken(u.getEmail(), u.getRoles());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.badRequest().body("Invalid credentials");
    }
}
