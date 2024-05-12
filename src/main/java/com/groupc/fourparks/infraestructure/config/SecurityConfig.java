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
                    http.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/api/v1/auth/sign-up").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/api/v1/auth/log-in").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/api/v1/auth/new-password").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/api/v1/auth/unlock").permitAll();

                    // parking endpoints
                    http.requestMatchers(HttpMethod.POST, "/api/v1/parkings/parking/new").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/api/v1/parkings/parking/name/{name}").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/api/v1/parkings/all").permitAll();
                    http.requestMatchers(HttpMethod.DELETE, "/api/v1/parkings/parking/delete/name/{name}").permitAll();
                    http.requestMatchers(HttpMethod.PUT, "/api/v1/parkings/parking/update").permitAll();

                    // rate endpoints
                    http.requestMatchers(HttpMethod.POST, "/api/v1/rates/rate/new").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/api/v1/rates/rate/id/{id}").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/api/v1/rates/parking/id/{id}").permitAll();
                    http.requestMatchers(HttpMethod.DELETE, "/api/v1/rates/rate/delete/{id}").permitAll();
                    http.requestMatchers(HttpMethod.PUT, "/api/v1/rates/rate/update").permitAll();

                    // slot endpoints
                    http.requestMatchers(HttpMethod.POST, "/api/v1/slots/slot/new").permitAll();

                    // manager endpoints
                    http.requestMatchers(HttpMethod.GET, "/api/v1/users/all").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/api/v1/users/role/{role}").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/api/v1/users/user/email/{email}").permitAll();
                    http.requestMatchers(HttpMethod.DELETE, "/api/v1/users/user/delete/email/{email}").permitAll();
                    http.requestMatchers(HttpMethod.PUT, "/api/v1/users/user/update").permitAll();
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
