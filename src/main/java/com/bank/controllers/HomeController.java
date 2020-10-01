package com.bank.controllers;

import com.bank.entities.UserEntity;
import com.bank.models.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String homeView(UserEntity userEntity, Authentication authentication) {

        String role = authentication.getAuthorities().toString();

        if (role.contains(String.valueOf(UserRole.ADMIN_ROLE)))
            return "admin/home";
        else if (role.contains(String.valueOf(UserRole.USER_ROLE)))
            return "user/home";
        else
            return "redirect:/index";
    }
}
