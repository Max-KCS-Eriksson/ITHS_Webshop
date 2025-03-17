package com.maxeriksson.iths.Webshop.service;

import com.maxeriksson.iths.Webshop.domain.user.User;
import com.maxeriksson.iths.Webshop.repository.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

@Service
@ApplicationScope
public class UserService {

    @Autowired private UserRepository repository;
    @Autowired private PasswordEncoder passwordEncoder;

    public User registerNewUser(User user) {
        String email = user.getEmail();
        boolean emailExists = repository.findById(email).isPresent();
        if (emailExists) {
            throw new RuntimeException("An account with this email is already registered" + email);
        }

        return repository.save(
                new User(email, passwordEncoder.encode(user.getPassword()), user.isAdmin()));
    }
}
