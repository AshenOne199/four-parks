package com.groupc.fourparks.api.login.model.service;


import com.groupc.fourparks.api.login.controller.dto.AuthCreateUserRequest;
import com.groupc.fourparks.api.login.controller.dto.AuthLoginRequest;
import com.groupc.fourparks.api.login.controller.dto.AuthResponse;
import com.groupc.fourparks.api.login.model.entities.RoleEntity;
import com.groupc.fourparks.api.login.model.entities.UserEntity;
import com.groupc.fourparks.api.login.model.repository.RoleRepository;
import com.groupc.fourparks.api.login.model.repository.UserRepository;
import com.groupc.fourparks.api.login.utils.JwtUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final JwtUtils jwtUtils;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    public UserDetailsServiceImpl(final JwtUtils jwtUtils, final UserRepository userRepository, final PasswordEncoder passwordEncoder, final RoleRepository roleRepository) {
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserEntityByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe"));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRoles()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        userEntity.getRoles().stream()
                .flatMap(role -> role.getPermissionsList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        return new User(userEntity.getEmail(), userEntity.getPassword(), authorityList);
    }

    public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {
        String email = authLoginRequest.email();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.generateToken(authentication);
        return new AuthResponse(email, "Usuario loggeado con exito", accessToken, true);
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Usuario o contraseña invalidos");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Contraseña incorrecta");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    public AuthResponse createUser(AuthCreateUserRequest authCreateUserRequest) {
        String username = authCreateUserRequest.email();
        String password = authCreateUserRequest.password();
        List<String> roleRequest = authCreateUserRequest.roleRequest().roleListName();

        Set<RoleEntity> roleEntitySet = new HashSet<>(roleRepository.findRoleEntitiesByRoleEnumIn(roleRequest));

        if (roleEntitySet.isEmpty()){
            throw new IllegalArgumentException("Los roles enviados en el request no existen");
        }

        UserEntity userEntity = UserEntity.builder()
                .email(username)
                .password(passwordEncoder.encode(password))
                .roles(roleEntitySet)
                .build();

        UserEntity userCreated = userRepository.save(userEntity);

        ArrayList<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userCreated.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));
        userCreated.getRoles()
                .stream()
                .flatMap(role -> role.getPermissionsList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        Authentication authentication = new UsernamePasswordAuthenticationToken(userCreated.getEmail(), userCreated.getPassword(), authorityList);
        String accessToken = jwtUtils.generateToken(authentication);
        return new AuthResponse(userCreated.getEmail(), "Usuario creado correctamente", accessToken, true);
    }

}
