package com.maxeriksson.iths.Webshop.service;

import com.maxeriksson.iths.Webshop.repository.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

@Service
@ApplicationScope
public class UserService {

    @Autowired private UserRepository repository;
}
