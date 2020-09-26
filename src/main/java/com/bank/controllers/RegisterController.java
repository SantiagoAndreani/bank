package com.bank.controllers;

import com.bank.entities.UserEntity;
import com.bank.services.PasswordEncoderService;
import com.bank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegisterController {

    private UserService userService;
    private PasswordEncoderService encoderService;

    @Autowired
    public RegisterController(UserService userService, PasswordEncoderService encoderService) {
        this.userService = userService;
        this.encoderService = encoderService;
    }

    @GetMapping("/register")
    public String registerView(UserEntity userEntity) {
        return "register";
    }

    @PostMapping("/registerUser")
    public String registerUser(@Valid UserEntity userEntity, BindingResult result){

        if (result.hasErrors())
            return "register";

        if (userService.notUniqueDni(userEntity) || userService.notUniqueEmail(userEntity))
            return "register";

         else {
            userService.registerUser(userEntity);
            return "index";
        }
    }

}