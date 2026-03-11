package com.lalit.taskflow.controller;

import com.lalit.taskflow.model.User;
import com.lalit.taskflow.repository.UserRepository;
import com.lalit.taskflow.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired private UserRepository userRepo;
    @Autowired private PasswordEncoder encoder;
    @Autowired private AuthenticationManager authManager;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String email    = body.get("email");
        String password = body.get("password");

        if (userRepo.existsByUsername(username))
            return ResponseEntity.badRequest().body(Map.of("error", "Username already taken"));
        if (userRepo.existsByEmail(email))
            return ResponseEntity.badRequest().body(Map.of("error", "Email already registered"));

        User user = new User(username, email, encoder.encode(password));
        userRepo.save(user);
        String token = jwtUtil.generate(username);
        return ResponseEntity.status(201).body(Map.of("token", token, "username", username));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        try {
            authManager.authenticate(
                new UsernamePasswordAuthenticationToken(body.get("username"), body.get("password")));
            String token = jwtUtil.generate(body.get("username"));
            return ResponseEntity.ok(Map.of("token", token, "username", body.get("username")));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }
    }
}
