package com.maxeriksson.iths.Webshop.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "email")
    @NotBlank(message = "Email is required")
    @Email(message = "Must be a valid email")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Column(name = "admin")
    @NotNull(message = "Admin role must be specified")
    private boolean isAdmin;

    public User() {}

    public User(String email, String password) {
        setEmail(email);
        setPassword(password);
        setAdmin(false);
    }

    public User(String email, String password, boolean isAdmin) {
        setEmail(email);
        setPassword(password);
        setAdmin(isAdmin);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
