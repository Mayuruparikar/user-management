package com.task.user_management.service;

import com.task.user_management.entity.User;
import com.task.user_management.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;
    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }
    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        if (user.getRoles().isEmpty()) user.getRoles().add("USER");
        return repo.save(user);
    }
    public User findByEmail(String email) {
        return repo.findByEmail(email).orElse(null);
    }
    public List<User> findAll() {
        return repo.findAll();
    }
    public User findById(Long id) {
        return repo.findById(id).orElse(null);
    }
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
