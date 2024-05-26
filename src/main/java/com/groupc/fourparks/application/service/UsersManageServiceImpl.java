package com.groupc.fourparks.application.service;

import com.groupc.fourparks.application.mapper.UserRegisterRequestMapper;
import com.groupc.fourparks.application.mapper.CreditCardDtoMapper;
import com.groupc.fourparks.application.usecase.AuditoryService;
import com.groupc.fourparks.application.usecase.ManagerService;
import com.groupc.fourparks.application.usecase.ParkingService;
import com.groupc.fourparks.domain.model.CreditCard;
import com.groupc.fourparks.domain.model.User;
import com.groupc.fourparks.domain.port.CreditCardPort;
import com.groupc.fourparks.domain.port.UserPort;
import com.groupc.fourparks.infraestructure.adapter.entity.RoleEntity;
import com.groupc.fourparks.infraestructure.adapter.repository.RoleRepository;
import com.groupc.fourparks.infraestructure.model.dto.ParkingDto;
import com.groupc.fourparks.infraestructure.model.dto.UserDto;
import com.groupc.fourparks.infraestructure.model.request.UserRegisterRequest;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UsersManageServiceImpl implements ManagerService {

    private final UserRegisterRequestMapper userRegisterRequestMapper;
    private final UserPort userPort;
    private final RoleRepository roleRepository;
    private final CreditCardPort creditCardPort;
    private final CreditCardDtoMapper creditCardDtoMapper;
    private final AuditoryService auditoryService;

    private final ParkingService parkingService;

    @Override
    public List<UserDto> readUsers() {
        List<UserDto> returnable = new ArrayList<>();
        List<User> receiver = userPort.findAllUsers();
        for (User user : receiver) {
            returnable.add((userToAddListConvert(user)));
        }
        return returnable;
    }

    @Override
    public List<UserDto> userByRole(Long rol) {
        List<UserDto> returnable = new ArrayList<>();
        List<User> receiver = userPort.findAllUsers();
        RoleEntity roleToVerify = roleRepository.getReferenceById(rol);
        for (User user : receiver) {
            if (user.getRoles().contains(roleToVerify)) {
                returnable.add((userToAddListConvert(user)));
            }
        }
        return returnable;
    }

    @Override
    public UserDto getOneUser(String email) {
        User user = userPort.findUserByEmail(email);
        return userToAddListConvert(user);
    }

    @Override
    public UserDto modifyUser(UserRegisterRequest userRegisterRequest) {
        User userFound = userPort.findUserByEmail(userRegisterRequest.getEmail());
        userFound.setFirstName(userRegisterRequest.getFirstName());
        userFound.setSecondName(userRegisterRequest.getSecondName());
        userFound.setFirstLastname(userRegisterRequest.getFirstLastname());
        userFound.setSecondLastname(userRegisterRequest.getSecondLastname());

        if (userFound.getRoles().stream().anyMatch(rol -> rol.getRoleEnum().name().equals("USUARIO"))) {
            userFound.setCreditCard(userRegisterRequestMapper.toDomain(userRegisterRequest).getCreditCard());
            CreditCard ccSample = creditCardPort.getCC(userFound);
            ccSample.setCardNumber(userFound.getCreditCard().getCardNumber());
            ccSample.setCvv(userFound.getCreditCard().getCvv());
            ccSample.setExpirationDate(userFound.getCreditCard().getExpirationDate());
            creditCardPort.save(ccSample, userFound);
        }

        auditoryService.registerAuditory(2L, userFound.getId());
        return userToAddListConvert(userPort.save(userFound));
    }

    @Override
    public void deleteUser(String userEmail) {
        User userFound = userPort.findUserByEmail(userEmail);
        List<CreditCard> allCCs = creditCardPort.getAllCC();
        for (CreditCard cc : allCCs) {
            if (Objects.equals(cc.getUserId().getId(), userFound.getId())) {
                creditCardPort.delete(cc);
            }
        }
        userPort.deleteUser(userFound);
    }

    @Override
    public List<UserDto> getFreeAdmins() {
        List<UserDto> receiver = new ArrayList<>();
        List<UserDto> removable = new ArrayList<>();
        List<UserDto> returnable = new ArrayList<>();

        List<ParkingDto> parkingsDtos = new ArrayList<>();
        receiver = userByRole(2L);

        parkingsDtos = parkingService.getParkings();

        for (UserDto userDto : receiver) {
            for (ParkingDto parkingDto : parkingsDtos) {
                if (parkingDto.getAdmin() != null) {
                    if (Objects.equals(parkingDto.getAdmin().getEmail(), userDto.getEmail())) {
                        removable.add(userDto);

                    }
                }

            }
        }

        for (UserDto userDto : receiver) {
            if (!removable.contains(userDto)) {
                returnable.add(userDto);
            }
        }

        return returnable;
    }

    private UserDto userToAddListConvert(User user) {
        UserDto userToAddList = new UserDto();
        userToAddList.setId(user.getId());

        userToAddList.setCreditCard(creditCardDtoMapper.toDto(creditCardPort.getCC(user)));
        userToAddList.setEmail(user.getEmail());
        userToAddList.setFirstName(user.getFirstName());
        userToAddList.setSecondName(user.getSecondName());
        userToAddList.setFirstLastname(user.getFirstLastname());
        userToAddList.setSecondLastname(user.getSecondLastname());
        userToAddList.setLoginAttempts(user.getLoginAttempts());
        userToAddList.setAccountActive(user.getAccountActive());
        userToAddList.setAccountBlocked(user.getAccountBlocked());

        List<String> roleListToAdd = new ArrayList<>();
        for (RoleEntity role : user.getRoles()) {
            roleListToAdd.add(role.getRoleEnum().name());
        }
        userToAddList.setRoleList(roleListToAdd);
        return userToAddList;
    }

    @Override
    public UserDto getOneUserId(Long id) {

        for (UserDto userDto : readUsers()) {
            if (userDto.getId() == id)
                return userDto;

        }
        return new UserDto();
    }
}
