package com.inovatrend.carService.service;

import com.inovatrend.carService.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserManager extends UserDetailsService {

    User save(User user);

    Page<User> getAllUsers(Pageable pageable);

    void deleteUser(Long id);
    Optional<User> getUser(Long userId);

    Optional<User> findByEmail(String email);

    public Optional<User> findUserByResetToken(String resetToken);
}
