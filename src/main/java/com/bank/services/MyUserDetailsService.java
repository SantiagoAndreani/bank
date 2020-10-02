package com.bank.services;

import com.bank.entities.UserEntity;
import com.bank.models.MyUserDetails;
import com.bank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {

        Optional<UserEntity> userEntityOptional = userRepository.findByDni(dni);
        userEntityOptional.orElseThrow(()-> new UsernameNotFoundException(dni + " NO ENCONTRADO"));
        return new MyUserDetails(userEntityOptional.get());
    }
}
