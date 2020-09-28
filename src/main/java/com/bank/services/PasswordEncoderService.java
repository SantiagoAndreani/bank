package com.bank.services;

import org.springframework.context.annotation.Bean;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;

@Service
public class PasswordEncoderService {

    @Bean
    public PasswordEncoder bCrypt() {
        return new BCryptPasswordEncoder();
    }
}
