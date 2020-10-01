package com.bank.services;

import com.bank.entities.UserAccountEntity;
import com.bank.entities.UserEntity;
import com.bank.entities.UserInfoEntity;
import com.bank.entities.UserRoleEntity;
import com.bank.models.UserRole;
import com.bank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    private UserRepository userRepository;
    private UserEntity userEntity;
    private PasswordEncoderService encoderService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoderService encoderService) {
        this.userRepository = userRepository;
        this.encoderService = encoderService;
    }

    public void registerUser(UserEntity userEntity) {

            this.userEntity = new UserEntity();

            this.userEntity.setDni(userEntity.getDni());
            this.userEntity.setEmail(userEntity.getEmail());
            this.userEntity.setPassword(encoderService.bCrypt().encode(userEntity.getPassword()));

            if(this.userEntity.getEmail().contains("@anana.com"))
                this.userEntity.setRoles(List.of(new UserRoleEntity(UserRole.ADMIN_ROLE)));
            else
                this.userEntity.setRoles(List.of(new UserRoleEntity(UserRole.USER_ROLE)));

            userRepository.save(this.userEntity);
    }

    public boolean notUniqueDni(UserEntity userEntity) {
        return userRepository.existsByDni(userEntity.getDni());
    }

    public boolean notUniqueEmail(UserEntity userEntity) {
        return userRepository.existsByEmail(userEntity.getEmail());
    }

}
