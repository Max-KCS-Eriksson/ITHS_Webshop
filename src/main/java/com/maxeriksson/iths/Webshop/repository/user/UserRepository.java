package com.maxeriksson.iths.Webshop.repository.user;

import com.maxeriksson.iths.Webshop.domain.user.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {}
