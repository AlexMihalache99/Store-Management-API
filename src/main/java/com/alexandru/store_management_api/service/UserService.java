package com.alexandru.store_management_api.service;

import com.alexandru.store_management_api.entity.User;
import com.alexandru.store_management_api.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        logger.debug("createUser request: username={}, role={}", user.getUsername(), user.getRole());
        User saved = userRepository.save(user);
        logger.info("Created user id={}", saved.getId());
        return saved;
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        logger.info("Retrieved {} users", users.size());
        return users;
    }

    public User getUserById(Long id) {
        logger.debug("getUserById id={}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        logger.info("Retrieved user id={}", id);
        return user;
    }

    public User updateUser(Long id, User updatedUser) {
        logger.debug("updateUser id={} request: username={}, role={}", id, updatedUser.getUsername(), updatedUser.getRole());
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(updatedUser.getUsername());
                    user.setRole(updatedUser.getRole());
                    User saved = userRepository.save(user);
                    logger.info("Updated user id={}", saved.getId());
                    return saved;
                })
                .orElseGet(() -> {
                    logger.warn("User id={} not found for update", id);
                    return null;
                });
    }

    public void deleteUser(Long id) {
        logger.debug("deleteUser id={}", id);
        userRepository.deleteById(id);
        logger.info("Deleted user id={}", id);
    }
    
}
