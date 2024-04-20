package com.groupc.fourparks.application.service;

import java.time.LocalDate;
import java.util.*;

import com.groupc.fourparks.domain.port.CreditCardPort;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
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

import com.groupc.fourparks.application.mapper.*;
import com.groupc.fourparks.infraestructure.model.dto.UserDto;
import com.groupc.fourparks.infraestructure.exception.*;
import com.groupc.fourparks.infraestructure.model.request.UserNewPasswordRequest;
import com.groupc.fourparks.infraestructure.model.request.UserRegisterRequest;
import com.groupc.fourparks.infraestructure.model.request.UserLoginRequest;
import com.groupc.fourparks.infraestructure.model.dto.LoginDto;
import com.groupc.fourparks.domain.port.UserPort;
import com.groupc.fourparks.infraestructure.model.dto.EmailDto;
import com.groupc.fourparks.infraestructure.adapter.entity.RoleEntity;
import com.groupc.fourparks.domain.port.RolePort;
import com.groupc.fourparks.infraestructure.config.jwt.JwtUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    private final RolePort rolePort;

    private final UserPort userPort;

    private final CreditCardPort creditCardPort;

    private final EmailServiceImpl emailServiceImpl;

    private final CreditCardServiceImpl creditCardServiceImpl;

    private final PasswordGeneratorImpl passwordGeneratorImpl;

    private final UserRegisterRequestMapper userRegisterRequestMapper;

    private final UserDtoMapper userDtoMapper;

    private final UserLoginRequestMapper userLoginRequestMapper;

    private final LoginDtoMapper loginDtoMapper;

    private final CreditCardDtoMapper creditCardDtoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userToLoad = userPort.findUserByEmail(username);
        if (!userToLoad.isAccountActive()){
            throw new ForbiddenException("El usuario esta inactivo");
        }
        if (userToLoad.isAccountBlocked()){
            throw new ForbiddenException("El usuario esta bloqueado. Contacte un administrador");
        }

        return new User(userToLoad.getEmail(), userToLoad.getPassword(), getRoles(userToLoad));
    }

    public UserDto createUser(UserRegisterRequest userRegisterRequest) {
        var userToCreate = userRegisterRequestMapper.toDomain(userRegisterRequest);

        String email = userToCreate.getEmail();

        var user = userPort.findUserByEmailOptional(email);
        if (user.isPresent()) {
            throw new InternalServerErrorException("Email ya registrado");
        }

        Set<RoleEntity> roleEntitySet = new HashSet<>(rolePort.findRolesByEnum(userToCreate.getRoleList()));
        if (roleEntitySet.isEmpty()){
            throw new BadRequestException("Los roles enviados no existen");
        }

        var creditCard = creditCardDtoMapper.toDto(userToCreate.getCreditCard());

        if (!creditCardServiceImpl.validateCreditCard(creditCard)){
            throw new BadRequestException("La tarjeta de credito no es valida");
        }

        var creditCardToSave = creditCardDtoMapper.toDomain(creditCard);

        String password = passwordGeneratorImpl.generateRandomPassword();
        try {
            emailServiceImpl.sendEmail(new EmailDto(email, "Nueva contraseña", password));
        } catch (MessagingException e) {
            throw new InternalServerErrorException("Error al enviar email");
        }

        userToCreate.setPassword(passwordEncoder.encode(password));
        userToCreate.setCreatedAt(LocalDate.now());
        userToCreate.setUpdatedAt(LocalDate.now());
        userToCreate.setRoles(roleEntitySet);

        var userCreated = userPort.save(userToCreate);
        creditCardPort.save(creditCardToSave, userCreated);

        ArrayList<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userCreated.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));
        userCreated.getRoles()
                .stream()
                .flatMap(role -> role.getPermissionsList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        return userDtoMapper.toDto(userCreated);
    }

    public LoginDto loginUser(UserLoginRequest userLoginRequest) {

        var userToLogin = userLoginRequestMapper.toDomain(userLoginRequest);
        String email = userToLogin.getEmail();
        String password = userToLogin.getPassword();
        Authentication authentication = this.authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.generateToken(authentication);

        var login = loginDtoMapper.toDto(userToLogin);
        login.setJwt(accessToken);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        login.setIp(request.getRemoteAddr());

        return login;
    }

    public UserDto activeUser(UserLoginRequest userLoginRequest) {
        var userToActive = userLoginRequestMapper.toDomain(userLoginRequest);
        String email = userToActive.getEmail();
        String password = userToActive.getPassword();

        var user = userPort.findUserByEmail(email);

        if (user.isAccountBlocked()){
            throw new ForbiddenException("El usuario esta bloqueado. Contacte un administrador");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UnauthorizedException("Contraseña incorrecta. No se puede activar el usuario.");
        }

        user.setAccountActive(true);
        var userSaved = userPort.save(user);

        return userDtoMapper.toDto(userSaved);
    }

    public UserDto newPassword(UserNewPasswordRequest userNewPasswordRequest) {
        String email = userNewPasswordRequest.getEmail();
        String oldPassword = userNewPasswordRequest.getOldPassword();
        String newPassword = userNewPasswordRequest.getNewPassword();
        String confirmPassword = userNewPasswordRequest.getConfirmPassword();

        if (!newPassword.equals(confirmPassword)) {
            throw new UnauthorizedException("Las contraseñas no coinciden");
        }

        var user = userPort.findUserByEmail(email);

        if (user.isAccountBlocked()){
            throw new ForbiddenException("El usuario esta bloqueado. Contacte un administrador");
        }

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new UnauthorizedException("Error de credenciales al cambiar de contraseña");
        }

        user.setPassword(passwordEncoder.encode(confirmPassword));
        var userSaved = userPort.save(user);

        return userDtoMapper.toDto(userSaved);
    }

    public UserDto unlock(UserLoginRequest userLoginRequest) {
        var userToUnlock = userLoginRequestMapper.toDomain(userLoginRequest);
        String email = userToUnlock.getEmail();

        var user = userPort.findUserByEmail(email);

        user.setAccountBlocked(false);
        user.setLoginAttempts(0);
        var userSaved = userPort.save(user);

        return userDtoMapper.toDto(userSaved);
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);
        if (userDetails == null) {
            throw new UnauthorizedException("Usuario o contraseña invalidos");
        }

        var userFound = userPort.findUserByEmail(username);
        if (userFound.isAccountBlocked()){
            throw new ForbiddenException("El usuario esta bloqueado. Contacte un administrador");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            userFound.setLoginAttempts(userFound.getLoginAttempts()+1);
            if (userFound.getLoginAttempts() > 3) {
                userFound.setAccountBlocked(true);
                userPort.save(userFound);
                throw new TooManyRequestsException("Cuenta bloqueada. Mas de 3 intentos fallidos. Contacte con un administrador");
            }
            userPort.save(userFound);
            throw new UnauthorizedException("Contraseña incorrecta");
        }

        userFound.setLoginAttempts(0);
        userPort.save(userFound);

        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    private List<SimpleGrantedAuthority> getRoles(com.groupc.fourparks.domain.model.User user) {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        user.getRoles()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        user.getRoles().stream()
                .flatMap(role -> role.getPermissionsList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));
        return authorityList;
    }
}
