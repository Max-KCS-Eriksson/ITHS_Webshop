package com.maxeriksson.iths.Webshop.service;

import com.maxeriksson.iths.Webshop.domain.user.User;
import com.maxeriksson.iths.Webshop.repository.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired private UserRepository repository;
    @Autowired private PasswordEncoder passwordEncoder;

    public User registerNewUser(User user) {
        String email = user.getEmail();
        boolean emailExists = repository.findById(email).isPresent();
        if (emailExists) {
            throw new RuntimeException("User with email " + email + " is already registered");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return repository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optional = repository.findById(email);
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("User with email " + email + " not found");
        }
        return optional.get();
    }
}
