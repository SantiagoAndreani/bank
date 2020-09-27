package com.bank.services;

import com.bank.entities.UserEntity;
import com.bank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(UserEntity userEntity) {
            userRepository.save(userEntity);
    }

    public boolean notUniqueDni(UserEntity userEntity) {
        return userRepository.existsByDni(userEntity.getDni());
    }

    public boolean notUniqueEmail(UserEntity userEntity) {
        return userRepository.existsByEmail(userEntity.getEmail());
    }

}
