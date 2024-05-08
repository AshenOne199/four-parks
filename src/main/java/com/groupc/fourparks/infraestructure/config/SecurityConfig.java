package com.groupc.fourparks.infraestructure.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JwtUtils jwtUtils;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    // public endpoints
                    http.requestMatchers(HttpMethod.POST, "/api/v1/auth/sign-up").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/api/v1/auth/log-in").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/api/v1/auth/new-password").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/api/v1/auth/test-post-body").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/api/v1/auth/test-post").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/api/v1/auth/test").permitAll();

                    // private endpoints
                    http.requestMatchers(HttpMethod.POST, "/api/v1/auth/unlock").hasRole("GERENTE");
                    http.requestMatchers(HttpMethod.GET, "/api/v1/users/allUsers").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/api/v1/users/userByRole/{role}").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/api/v1/users/Users").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/api/v1/users/getOneUser/{email}").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/api/v1/users/deleteUser/{email}").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/api/v1/users/modifyUser").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/api/v1/users/createUser").permitAll();

                    http.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
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
