package com.groupc.fourparks.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.groupc.fourparks.application.mapper.CreditCardDtoMapper;
import com.groupc.fourparks.application.mapper.UserDtoMapper;
import com.groupc.fourparks.application.mapper.UserRegisterRequestMapper;
import com.groupc.fourparks.application.service.PatternsHelpers.ModifyUserDirector;
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

@Service
@AllArgsConstructor
public class UsersManageServiceImpl implements ManagerService {

    private final UserRegisterRequestMapper userRegisterRequestMapper;
    private final UserPort userPort;
    private final UserDtoMapper userDtoMapper;
    private final RoleRepository roleRepository;
    private final CreditCardPort creditCardPort;
    private final CreditCardDtoMapper CreditCardDtoMapper;
    private final AuditoryService auditoryService;

    private final ParkingService parkingService;

    @Override
    public List<UserDto> readUsers() {
        List<UserDto> returnable = new ArrayList<>();
        List<User> receiver = userPort.findAllUsers();
        for (User user : receiver) {
            UserDto addable = userDtoMapper.toDto((user));
            addable.setCreditCard(CreditCardDtoMapper.toDto(creditCardPort.getCC(user)));
            addable.setRoleList(user.getRoleList());
            returnable.add(addable);
            
            List<String> roleListToAdd = new ArrayList<>();
            for (RoleEntity role : user.getRoles()) {
                roleListToAdd.add(role.getRoleEnum().name());
            }
            addable.setRoleList(roleListToAdd);
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
                UserDto addable = userDtoMapper.toDto((user));
                addable.setCreditCard(CreditCardDtoMapper.toDto(creditCardPort.getCC(user)));
                addable.setRoleList(user.getRoleList());
                returnable.add(addable);
                List<String> roleListToAdd = new ArrayList<>();
                for (RoleEntity role : user.getRoles()) {
                    roleListToAdd.add(role.getRoleEnum().name());
                }
                addable.setRoleList(roleListToAdd);
            }

        }
        return returnable;
    }

    @Override
    public UserDto getOneUser(String email) {
        User user = userPort.findUserByEmail(email);

        UserDto addable = userDtoMapper.toDto((user));
        addable.setCreditCard(CreditCardDtoMapper.toDto(creditCardPort.getCC(user)));
        addable.setRoleList(user.getRoleList());
        List<String> roleListToAdd = new ArrayList<>();
        for (RoleEntity role : user.getRoles()) {
            roleListToAdd.add(role.getRoleEnum().name());
        }
        addable.setRoleList(roleListToAdd);
        return addable;
    }

    @Override
    public UserDto modifyUser(UserRegisterRequest userRegisterRequest) {
        ModifyUserDirector modifyUserDirector = new ModifyUserDirector();
        User found = userPort.findUserByEmail(userRegisterRequest.getEmail());
        CreditCard creditCardSendable = new CreditCard();
        
        
        if(userRegisterRequest.getCreditCard() != null)
        {
            creditCardSendable.setCardNumber(userRegisterRequest.getCreditCard().getCardNumber());
            creditCardSendable.setCvv(userRegisterRequest.getCreditCard().getCvv());
            creditCardSendable.setExpirationDate(userRegisterRequest.getCreditCard().getExpirationDate());
            if (!creditCardSendable.getCardNumber().equals(null) && !creditCardSendable.getExpirationDate().equals(null)
            && !creditCardSendable.getCvv().equals(null)) {
        creditCardPort.save(creditCardSendable, userPort.findUserByEmail(userRegisterRequest.getEmail()));
    }
            
    
        }
        User userModified = modifyUserDirector.make(found, userRegisterRequest);
        
 
        var  sampler = auditoryService.registerAuditory(2L, userModified.getId());
        System.out.println(sampler.getActivity());
        UserDto addable = userDtoMapper.toDto((userPort.save(userModified)));
        addable.setCreditCard(CreditCardDtoMapper.toDto(creditCardPort.getCC(userPort.save(userModified))));
        addable.setRoleList(userPort.save(userModified).getRoleList());
        
        List<String> roleListToAdd = new ArrayList<>();
        for (RoleEntity role : userModified.getRoles()) {
            roleListToAdd.add(role.getRoleEnum().name());
        }
        addable.setRoleList(roleListToAdd);

    
        
        return addable;
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

    @Override
    public UserDto getOneUserId(Long id) {

        for (UserDto userDto : readUsers()) {
            if (userDto.getId() == id)
                return userDto;

        }
        return new UserDto();
    }
}
