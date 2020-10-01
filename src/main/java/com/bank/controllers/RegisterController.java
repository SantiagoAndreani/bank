package com.bank.controllers;

import com.bank.entities.UserEntity;
import com.bank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class RegisterController {

    private UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerView(UserEntity userEntity) {
        return "register";
    }

    @PostMapping("/registerUser")
    public String registerUser(@Valid UserEntity userEntity,
                               BindingResult result,
                               RedirectAttributes redirectAttributes){

        if (result.hasErrors())
            return "register";

        if (userService.notUniqueDni(userEntity)) {
            redirectAttributes.addFlashAttribute("notUniqueDni", "DNI de cliente existente");
            return "redirect:/register";
        }
        if (userService.notUniqueEmail(userEntity)) {
            redirectAttributes.addFlashAttribute("notUniqueEmail", "Email de cliente existente");
            return "redirect:/register";
        }
        else {
            userService.registerUser(userEntity);
            redirectAttributes.addFlashAttribute("success", "Cuenta creada exitosamente");
            return "redirect:/index";
        }
    }

}