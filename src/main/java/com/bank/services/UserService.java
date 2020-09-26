package com.bank.services;

import com.bank.entities.UserEntity;
import com.bank.entities.UserRoleEntity;
import com.bank.models.UserRole;
import com.bank.repositories.UserRepository;
import org.hibernate.mapping.Collection;
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

}
