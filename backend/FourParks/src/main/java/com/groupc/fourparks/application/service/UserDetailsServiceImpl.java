package com.groupc.fourparks.application.service;


import com.groupc.fourparks.domain.dto.request.UserRegisterRequest;
import com.groupc.fourparks.domain.dto.request.UserLoginRequest;
import com.groupc.fourparks.domain.dto.AuthResponse;
import com.groupc.fourparks.domain.port.UserPort;
import com.groupc.fourparks.infraestructure.adapter.entity.EmailEntity;
import com.groupc.fourparks.infraestructure.adapter.entity.RoleEntity;
import com.groupc.fourparks.infraestructure.adapter.entity.UserEntity;
import com.groupc.fourparks.domain.port.RolePort;
import com.groupc.fourparks.infraestructure.config.jwt.JwtUtils;
import jakarta.mail.MessagingException;
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

import java.time.LocalDate;
import java.util.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    private final RolePort rolePort;

    private final UserPort userPort;

    private final EmailServiceImpl emailServiceImpl;

    public UserDetailsServiceImpl(final PasswordEncoder passwordEncoder, final JwtUtils jwtUtils, final RolePort rolePort, final UserPort userPort, final EmailServiceImpl emailServiceImpl) {
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.rolePort = rolePort;
        this.userPort = userPort;
        this.emailServiceImpl = emailServiceImpl;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userPort.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe"));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRoles()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        userEntity.getRoles().stream()
                .flatMap(role -> role.getPermissionsList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        return new User(userEntity.getEmail(), userEntity.getPassword(), authorityList);
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

    public AuthResponse loginUser(UserLoginRequest userLoginRequest) {
        String email = userLoginRequest.email();
        String password = userLoginRequest.password();
        Authentication authentication = this.authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.generateToken(authentication);
        return new AuthResponse(email, "Usuario loggeado con exito", accessToken, true);
    }

    public AuthResponse createUser(UserRegisterRequest authCreateUserRegisterRequest) {
        String email = authCreateUserRegisterRequest.email();
        String password = encrypt(authCreateUserRegisterRequest.email());
        try {
            emailServiceImpl.sendEmail(new EmailEntity(email, "Nueva contraseña", password));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        String firstName = authCreateUserRegisterRequest.firstName();
        String secondName = authCreateUserRegisterRequest.secondName();
        String firstLastname = authCreateUserRegisterRequest.firstLastname();
        String secondLastname = authCreateUserRegisterRequest.secondLastname();

        List<String> roleRequest = authCreateUserRegisterRequest.roleRequest().roleListName();
        Set<RoleEntity> roleEntitySet = new HashSet<>(rolePort.findRolesByEnum(roleRequest));

        if (roleEntitySet.isEmpty()){
            throw new IllegalArgumentException("Los roles enviados en el request no existen");
        }

        UserEntity userEntity = UserEntity.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .firstName(firstName)
                .secondName(secondName)
                .firstLastname(firstLastname)
                .secondLastname(secondLastname)
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .roles(roleEntitySet)
                .build();

        UserEntity userCreated = userPort.save(userEntity);

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

    public static String encrypt(String plaintext) {
        return plaintext.chars()
                .map(c -> Character.isLetter(c) ? (char) ('a' + (c - 'a' + 7) % 26) : c)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
