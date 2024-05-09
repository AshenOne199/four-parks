package com.groupc.fourparks.infraestructure.config;

import com.groupc.fourparks.domain.model.User;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.groupc.fourparks.application.service.UserDetailsServiceImpl;
import com.groupc.fourparks.infraestructure.config.jwt.JwtTokenValidator;
import com.groupc.fourparks.infraestructure.config.jwt.JwtUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtUtils jwtUtils;

    public SecurityConfig(final JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    // public endpoints
                    http.requestMatchers(HttpMethod.POST, "/api/v1/auth/sign-up").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/api/v1/auth/log-in").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/api/v1/auth/new-password").permitAll();

                            //Users endpoints
                    http.requestMatchers(HttpMethod.GET, "/api/v1/users/allUsers").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/api/v1/users/userByRole/{role}").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/api/v1/users/Users").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/api/v1/users/getOneUser/{email}").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/api/v1/users/deleteUser/{email}").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/api/v1/users/modifyUser").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/api/v1/users/createUser").permitAll();

                    //parking endpoints
                    http.requestMatchers(HttpMethod.GET, "/api/v1/parking/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/api/v1/parking/**").permitAll();
                    http.requestMatchers(HttpMethod.DELETE, "/api/v1/parking/**").permitAll();
                    http.requestMatchers(HttpMethod.PUT, "/api/v1/parking/**").permitAll();


                    // private endpoints
                    http.requestMatchers(HttpMethod.POST, "/api/v1/auth/unlock").hasRole("ADMINISTRADOR");
                })
                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
