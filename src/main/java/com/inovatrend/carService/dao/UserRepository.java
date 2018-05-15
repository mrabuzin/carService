package com.inovatrend.carService.dao;

import com.inovatrend.carService.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository< User, Long> {

    User findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByResetToken(String resetToken);

}
