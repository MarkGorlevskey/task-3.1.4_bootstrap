package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


public interface UserService extends UserDetailsService {
    void save(User user);

    User findById(Long id);

    List<User> getAllUsers();

    void update(User user, Long id);

    void deleteById(Long id);
    User findByUsername(String email);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}