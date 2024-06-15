package com.orderingsystem.OrderingSystemSpringBE.security;

import com.orderingsystem.OrderingSystemSpringBE.entity.UserData;
import com.orderingsystem.OrderingSystemSpringBE.repository.UserDataRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/css/**", "/", "/auth/**", "/error").permitAll()
                .requestMatchers("/customers/**", "/products/**").hasRole("SALES")
                .requestMatchers("/orders/**").hasRole("ACCOUNTANT")
                .requestMatchers("/categories/**", "/segments/**", "/statuses/**", "/users/**").hasRole("ADMIN")
                .and()
                .formLogin()
                        .permitAll()
                .and()
                .logout()
                .and()
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    // Load initial user data if User table in DB is void
    @Bean
    public CommandLineRunner userDataLoader(UserDataRepository userDataRepository, PasswordEncoder passwordEncoder)  {

        return (args) -> {
            if (userDataRepository.findAll().isEmpty()) {
                userDataRepository.save( new UserData(1L, "tamas", "tamas987@gmail.com", passwordEncoder.encode("1234"), "ROLE_SALES", "", true) );
                userDataRepository.save( new UserData(2L, "joe", "joe876@gmail.com", passwordEncoder.encode("1234"), "ROLE_ACCOUNTANT", "", true) );
                userDataRepository.save( new UserData(3L, "admin", "admin765@gmail.com", passwordEncoder.encode("1234"), "ROLE_ADMIN", "", true) );
                userDataRepository.save( new UserData(4L, "zeno", "zeno654@gmail.com", passwordEncoder.encode("1234"), "ROLE_SALES,ROLE_ADMIN", "", true) );
                System.out.println("4 users were saved into DB");
            }
        };
    }

}
