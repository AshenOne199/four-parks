package com.groupc.fourparks.application.service;

import com.groupc.fourparks.application.mapper.UserRegisterRequestMapper;
import com.groupc.fourparks.application.usecase.UsersManageService;
import com.groupc.fourparks.domain.model.Role;
import com.groupc.fourparks.domain.model.User;
import com.groupc.fourparks.domain.port.RolePort;
import com.groupc.fourparks.domain.port.UserPort;
import com.groupc.fourparks.infraestructure.adapter.entity.RoleEntity;
import com.groupc.fourparks.infraestructure.adapter.entity.UserEntity;
import com.groupc.fourparks.infraestructure.adapter.mapper.UserDboMapper;
import com.groupc.fourparks.infraestructure.adapter.repository.RoleRepository;
import com.groupc.fourparks.infraestructure.adapter.repository.UserRepository;
import com.groupc.fourparks.infraestructure.exception.BadRequestException;
import com.groupc.fourparks.infraestructure.model.request.UserRegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UsersManageServiceImpl implements UsersManageService {

    private final  UserPort userPort;
    private final UserRegisterRequestMapper userRegisterRequestMapper;
    private final UserDboMapper userDboMapper;
    private final RoleRepository roleRepository;
    private final RolePort rolePort;


    @Override
    public List<User> readUsers() {

        return userPort.findAllUsers();
    }

    @Override
    public List<User> userByRole(Long rol) {

        List<User> allUsers = new ArrayList<>(List.of());
        List<UserEntity> usersReceiver = userPort.findAll();

        RoleEntity roleToVerify = roleRepository.getReferenceById(rol);

        for (UserEntity userEntity : usersReceiver) {

                if (userEntity.getRoles().contains(roleToVerify)) {
                    allUsers.add(userDboMapper.toDomain(userEntity));
                }

        }

        return allUsers;
    }
    @Override
    public User getOneUser(String email) {
        return userPort.findUserByEmail(email);


    }
    @Override
    public User modifyUser(UserRegisterRequest userRegisterRequest)
    {
        User user = userRegisterRequestMapper.toDomain(userRegisterRequest);



        User userFound = getOneUser(user.getEmail());

        userFound.setFirstName(user.getFirstName());
        userFound.setSecondName(user.getSecondName());
        userFound.setFirstLastname(user.getFirstLastname());
        userFound.setSecondLastname(user.getSecondLastname());

        userFound.setCreditCard(user.getCreditCard());


        Set<RoleEntity> roleEntitySet = new HashSet<>(rolePort.findRolesByEnum(user.getRoleList()));
        if (roleEntitySet.isEmpty()){
            throw new BadRequestException("Los roles enviados no existen");
        }
        userFound.setRoles(roleEntitySet);

        if (userFound.getId()==-1L)
        {
            return userFound;
        }
        else
        {
            return userPort.save(userFound);
        }


    }



    @Override
    public void deleteUser(String userEmail)
    {
        User userFound = getOneUser(userEmail);


        if (userFound.getId()!=-1L)
        {

            userPort.deleteUser(userFound);
        }


    }

   /* @Override
    public User createUser(User user) {
        User sampleUser = new User();
        sampleUser.setEmail("a");
        sampleUser.setPassword("a");

        if (getOneUser(user.getEmail()).getId() == -1L)
        {


            return userPort.save(sampleUser);
        }
        else
        {

            sampleUser.setEmail("Ya hay un usuario con este correo");
            return sampleUser;
        }
    }*/


}
