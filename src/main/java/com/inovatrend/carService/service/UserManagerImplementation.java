package com.inovatrend.carService.service;

import com.inovatrend.carService.dao.UserRepository;
import com.inovatrend.carService.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserManagerImplementation implements UserManager {

    private final UserRepository userRepository;

    public UserManagerImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {

        return userRepository.save(user);
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional getUser(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepository.findByUsername(username);
        if(user != null){
            return user;
        } else {
            throw new UsernameNotFoundException("User not found!");
        }
    }


    @Override
    public Optional findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional findUserByResetToken(String resetToken) {
        return userRepository.findByResetToken(resetToken);
    }

}
