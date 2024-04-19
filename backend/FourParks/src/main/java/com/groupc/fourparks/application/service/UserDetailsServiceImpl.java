package com.groupc.fourparks.application.service;


import com.groupc.fourparks.application.error.exception.*;
import com.groupc.fourparks.domain.dto.request.UserNewPasswordRequest;
import com.groupc.fourparks.domain.dto.request.UserRegisterRequest;
import com.groupc.fourparks.domain.dto.request.UserLoginRequest;
import com.groupc.fourparks.domain.dto.AuthResponse;
import com.groupc.fourparks.domain.dto.request.UserUnblockRequest;
import com.groupc.fourparks.domain.port.UserPort;
import com.groupc.fourparks.infraestructure.adapter.entity.EmailEntity;
import com.groupc.fourparks.infraestructure.adapter.entity.RoleEntity;
import com.groupc.fourparks.infraestructure.adapter.entity.UserEntity;
import com.groupc.fourparks.domain.port.RolePort;
import com.groupc.fourparks.infraestructure.config.jwt.JwtUtils;
import jakarta.mail.MessagingException;
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

    private final PasswordGeneratorImpl passwordGeneratorImpl;

    public UserDetailsServiceImpl(final PasswordEncoder passwordEncoder, final JwtUtils jwtUtils, final RolePort rolePort, final UserPort userPort, final EmailServiceImpl emailServiceImpl, final PasswordGeneratorImpl passwordGeneratorImpl) {
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.rolePort = rolePort;
        this.userPort = userPort;
        this.emailServiceImpl = emailServiceImpl;
        this.passwordGeneratorImpl = passwordGeneratorImpl;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userPort.findUserByEmail(username)
                .orElseThrow(() -> new UnauthorizedException("El usuario " + username + " no existe"));

        if (!userEntity.isActive()){
            throw new ForbidenException("El usuario esta inactivo");
        }

        if (userEntity.isBLocked()){
            throw new ForbidenException("El usuario esta bloqueado. Contacte un administrador");
        }

        return new User(userEntity.getEmail(), userEntity.getPassword(), getRoles(userEntity));
    }

    public AuthResponse createUser(UserRegisterRequest authCreateUserRegisterRequest) {
        String email = authCreateUserRegisterRequest.email();

        Optional<UserEntity> userEntityFound = userPort.findUserByEmail(email);
        if (userEntityFound.isPresent()) {
            throw new InternalServerErrorException("Email ya registrado");
        }

        String firstName = authCreateUserRegisterRequest.firstName();
        String secondName = authCreateUserRegisterRequest.secondName();
        String firstLastname = authCreateUserRegisterRequest.firstLastname();
        String secondLastname = authCreateUserRegisterRequest.secondLastname();

        List<String> roleRequest = authCreateUserRegisterRequest.roleRequest().roleListName();
        Set<RoleEntity> roleEntitySet = new HashSet<>(rolePort.findRolesByEnum(roleRequest));

        if (roleEntitySet.isEmpty()){
            throw new BadRequestException("Los roles enviados no existen");
        }

        String password = passwordGeneratorImpl.generateRandomPassword();
        try {
            emailServiceImpl.sendEmail(new EmailEntity(email, "Nueva contraseña", password));
        } catch (MessagingException e) {
            throw new InternalServerErrorException("Error al enviar email");
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

        return new AuthResponse(userCreated.getEmail(), "Usuario creado correctamente", null);
    }

    public AuthResponse loginUser(UserLoginRequest userLoginRequest) {
        String email = userLoginRequest.email();
        String password = userLoginRequest.password();
        Authentication authentication = this.authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.generateToken(authentication);
        return new AuthResponse(email, "Usuario loggeado con exito", accessToken);
    }

    public AuthResponse activeUser(UserLoginRequest userLoginRequest) {
        String email = userLoginRequest.email();
        String password = userLoginRequest.password();

        UserEntity userEntity = userPort.findUserByEmail(email).orElseThrow(() -> new UnauthorizedException("El usuario " + email + " no existe"));

        if (userEntity.isBLocked()){
            throw new ForbidenException("El usuario esta bloqueado. Contacte un administrador");
        }

        User user = new User(userEntity.getEmail(), userEntity.getPassword(), getRoles(userEntity));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UnauthorizedException("Contraseña incorrecta. No se puede activar el usuario.");
        }

        userEntity.setActive(true);
        userPort.save(userEntity);

        return new AuthResponse(email, "Cuenta activada con exito", null);
    }

    public AuthResponse newPassword(UserNewPasswordRequest userNewPasswordRequest) {
        String email = userNewPasswordRequest.email();
        String oldPassword = userNewPasswordRequest.oldPassword();
        String newPassword = userNewPasswordRequest.newPassword();
        String confirmPassword = userNewPasswordRequest.confirmPassword();

        if (!newPassword.equals(confirmPassword)) {
            throw new UnauthorizedException("Las contraseñas no coinciden");
        }

        UserEntity userEntity = userPort.findUserByEmail(email).orElseThrow(() -> new UnauthorizedException("El usuario " + email + " no existe"));

        if (userEntity.isBLocked()){
            throw new ForbidenException("El usuario esta bloqueado. Contacte un administrador");
        }

        User user = new User(userEntity.getEmail(), userEntity.getPassword(), getRoles(userEntity));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new UnauthorizedException("Error de credenciales al cambiar de contraseña");
        }

        userEntity.setPassword(passwordEncoder.encode(confirmPassword));
        userPort.save(userEntity);

        return new AuthResponse(email, "Contraseña modificada con exito", null);
    }

    public AuthResponse unBlock(UserUnblockRequest userUnblockRequest) {
        String email = userUnblockRequest.email();

        UserEntity userEntity = userPort.findUserByEmail(email).orElseThrow(() -> new UnauthorizedException("El usuario " + email + " no existe"));

        userEntity.setBLocked(false);
        userEntity.setLoginAttempts(0);
        userPort.save(userEntity);

        return new AuthResponse(email, "Usuario desbloqueado con exito", null);
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);
        if (userDetails == null) {
            throw new UnauthorizedException("Usuario o contraseña invalidos");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            Optional<UserEntity> userEntity = userPort.findUserByEmail(username);
            if (userEntity.isPresent()) {
                userEntity.ifPresent(entity -> entity.setLoginAttempts(entity.getLoginAttempts()+1));
                if (userEntity.get().getLoginAttempts() > 3) {
                    userEntity.get().setBLocked(true);
                    userPort.save(userEntity.get());
                    throw new TooManyRequestsException("Cuenta bloqueada. Mas de 3 intentos fallidos. Contacte con un administrador");
                }
                userPort.save(userEntity.get());
            }
            throw new UnauthorizedException("Contraseña incorrecta");
        }

        Optional<UserEntity> userEntity = userPort.findUserByEmail(username);
        if (userEntity.isPresent()) {
            userEntity.ifPresent(entity -> entity.setLoginAttempts(0));
            userPort.save(userEntity.get());
        }

        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    private List<SimpleGrantedAuthority> getRoles(UserEntity userEntity) {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRoles()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        userEntity.getRoles().stream()
                .flatMap(role -> role.getPermissionsList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));
        return authorityList;
    }
}
