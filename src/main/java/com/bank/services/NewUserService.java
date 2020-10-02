package com.bank.services;

import com.bank.entities.UserEntity;
import com.bank.entities.UserInfoEntity;
import com.bank.entities.UserRoleEntity;
import com.bank.models.UserGender;
import com.bank.models.UserRole;
import com.bank.repositories.UserInfoRepository;
import com.bank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class NewUserService {

    private UserRepository userRepository;
    private UserEntity userEntity;
    private PasswordEncoderService encoderService;
    private UserInfoRepository infoRepository;
    private UserInfoEntity infoEntity;

    @Autowired
    public NewUserService(UserRepository userRepository,
                          PasswordEncoderService encoderService,
                          UserInfoRepository infoRepository) {
        this.userRepository = userRepository;
        this.encoderService = encoderService;
        this.infoRepository = infoRepository;
    }

    public boolean notUniqueDni(UserEntity userEntity) {
        return userRepository.existsByDni(userEntity.getDni());
    }

    public boolean notUniqueEmail(UserEntity userEntity) {
        return userRepository.existsByEmail(userEntity.getEmail());
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

    public boolean checkInfo(Authentication authentication) {

        String dni = authentication.getName();

        Optional<UserEntity> optionalUserEntity = userRepository.findByDni(dni);
        optionalUserEntity.orElseThrow(()-> new UsernameNotFoundException(dni + " NO ENCONTRADO"));
        Long idUser = optionalUserEntity.get().getId();

        return infoRepository.findById(idUser).isEmpty();
    }

    public void registerInfo(Authentication authentication, UserInfoEntity userInfoEntity) {

        Optional<UserEntity> optionalUserEntity = userRepository.findByDni(authentication.getName());
        optionalUserEntity.orElseThrow(()-> new UsernameNotFoundException("NO ENCONTRADO"));
        Long idUser = optionalUserEntity.get().getId();

        this.infoEntity = new UserInfoEntity();

        this.infoEntity.setId(idUser);
        this.infoEntity.setName(userInfoEntity.getName());
        this.infoEntity.setLastName(userInfoEntity.getLastName());
        this.infoEntity.setCelPhone(userInfoEntity.getCelPhone());

        this.infoEntity.setBirthDate(LocalDate.now());
        this.infoEntity.setProvince("alla");
        this.infoEntity.setMunicipality("aca");
        this.infoEntity.setGender(UserGender.MALE);
    }



}
