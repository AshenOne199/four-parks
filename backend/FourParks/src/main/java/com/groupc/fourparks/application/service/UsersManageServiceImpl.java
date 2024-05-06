package com.groupc.fourparks.application.service;

import com.groupc.fourparks.application.mapper.CreditCardDtoMapper;
import com.groupc.fourparks.application.usecase.UsersManageService;
import com.groupc.fourparks.domain.model.CreditCard;
import com.groupc.fourparks.domain.model.User;
import com.groupc.fourparks.domain.port.CreditCardPort;
import com.groupc.fourparks.domain.port.UserPort;
import com.groupc.fourparks.infraestructure.adapter.entity.RoleEntity;
import com.groupc.fourparks.infraestructure.adapter.entity.UserEntity;
import com.groupc.fourparks.infraestructure.adapter.mapper.UserDboMapper;
import com.groupc.fourparks.infraestructure.adapter.repository.RoleRepository;
import com.groupc.fourparks.infraestructure.model.request.UserToShow;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UsersManageServiceImpl implements UsersManageService {

    private final  UserPort userPort;
    private final UserDboMapper userDboMapper;
    private final RoleRepository roleRepository;
    private final CreditCardPort creditCardPort;

    private final CreditCardDtoMapper creditCardDtoMapper;


    private UserToShow userToAddListConvert(User user)
    {
        UserToShow userToAddList = new UserToShow();
        userToAddList.setCreditCard(creditCardDtoMapper.toDto(creditCardPort.getCC(user)));
        userToAddList.setId(user.getId());

        userToAddList.setEmail(user.getEmail());
        userToAddList.setFirstName(user.getFirstName());
        userToAddList.setSecondName(user.getSecondName());
        userToAddList.setFirstLastname(user.getFirstLastname());
        userToAddList.setSecondLastname(user.getSecondLastname());
        userToAddList.setLoginAttempts(user.getLoginAttempts());

        userToAddList.setAccountActive(user.isAccountActive());
        userToAddList.setAccountBlocked(user.isAccountBlocked());

        List<String> roleListToAdd = new ArrayList<>();
        for(RoleEntity role :user.getRoles())
        {
            roleListToAdd.add(role.getRoleEnum().name());
        }
        userToAddList.setRoleList(roleListToAdd);
        return userToAddList;
    }
    @Override
    public List<UserToShow> readUsers() {

        List<UserToShow> userToShows = new ArrayList<>();

        List<User> receiver = userPort.findAllUsers();


        for (User user : receiver) {



            userToShows.add((userToAddListConvert(user)));

        }

        return userToShows;
    }

    @Override
    public List<UserToShow> userByRole(Long rol) {

        List<User> allUsers = new ArrayList<>(List.of());
        List<UserEntity> usersReceiver = userPort.findAll();
        List<UserToShow> userToShows = new ArrayList<>();
        RoleEntity roleToVerify = roleRepository.getReferenceById(rol);

        for (UserEntity userEntity : usersReceiver) {

                if (userEntity.getRoles().contains(roleToVerify)) {
                    allUsers.add(userDboMapper.toDomain(userEntity));
                }

        }
        for (User user : allUsers) {


            userToShows.add((userToAddListConvert(user)));
        }

        return userToShows;
    }
    @Override
    public UserToShow getOneUser(String email) {
        User user = userPort.findUserByEmail(email);

        return userToAddListConvert(user);


    }
    private User getUserPrivClass(String email)
    {
        return userPort.findUserByEmail(email);
    }
    @Override
    public UserToShow modifyUser(User user)
    {
        User userFound = getUserPrivClass(user.getEmail());
        UserToShow userToShow = userToAddListConvert(userFound);

        userFound.setFirstName(user.getFirstName());
        userFound.setSecondName(user.getSecondName());
        userFound.setFirstLastname(user.getFirstLastname());
        userFound.setSecondLastname(user.getSecondLastname());
        userFound.setLoginAttempts(user.getLoginAttempts());
        userFound.setAccountActive(user.isAccountActive());
        userFound.setAccountBlocked(user.isAccountBlocked());
        userFound.setCreditCard(user.getCreditCard());

        if (userFound.getId()==-1L)
        {
            return userToShow;
        }
        else
        {


            CreditCard ccSample = creditCardPort.getCC(userFound);
            ccSample.setCardNumber(userFound.getCreditCard().getCardNumber());
            ccSample.setCvv(userFound.getCreditCard().getCvv());
            ccSample.setExpirationDate(userFound.getCreditCard().getExpirationDate());
            creditCardPort.save(ccSample,user);
            userToShow = userToAddListConvert(userPort.save(userFound));
        }
        return userToShow;

    }



    @Override
    public void deleteUser(String userEmail)
    {
        User userFound = getUserPrivClass(userEmail);

        /*Deleting credit cards FK to user*/
        List<CreditCard> allCCs = new ArrayList<>();
        allCCs = creditCardPort.getAllCC();

        for(CreditCard cc:allCCs)
        {
            if (Objects.equals(cc.getUserId().getId(), userFound.getId()))
            {
                creditCardPort.delete(cc);
            }
        }
        if (userFound.getId()!=-1L)
        {

            userPort.deleteUser(userFound);
        }


    }




}
