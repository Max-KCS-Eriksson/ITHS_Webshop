package com.maxeriksson.iths.Webshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    @Autowired private UserDetailsService userDetailsService;
    @Autowired private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(
                csrf -> {
                    csrf.ignoringRequestMatchers("/api/**"); // TODO: fix later
                });
        http.authorizeHttpRequests(
                        auth -> {
                            auth.requestMatchers(HttpMethod.GET, "/").permitAll();
                            auth.requestMatchers(HttpMethod.POST, "/register").permitAll();

                            auth.requestMatchers("/admin/**").hasAuthority("ADMIN");

                            auth.requestMatchers("/api/**").permitAll(); // TODO: fix later

                            auth.anyRequest().authenticated();
                        })
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager() {
        ProviderManager authProviderManager =
                new ProviderManager(Arrays.asList(daoAuthenticationProvider()));
        authProviderManager.setAuthenticationEventPublisher(authenticationEventPublisher());

        return authProviderManager;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);

        return provider;
    }

    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher() {
        return new DefaultAuthenticationEventPublisher();
    }
}
