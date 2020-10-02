package com.bank.controllers;

import com.bank.entities.UserEntity;
import com.bank.entities.UserInfoEntity;
import com.bank.models.UserRole;
import com.bank.services.NewUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class HomeController {

    private NewUserService newUserService;

    @Autowired
    public HomeController(NewUserService newUserService) {
        this.newUserService = newUserService;
    }

    @GetMapping("/home")
    public String homeView(Authentication authentication) {

        if (newUserService.checkInfo(authentication))
            return "redirect:/info";

        else {
            String role = authentication.getAuthorities().toString();

            if (role.contains(String.valueOf(UserRole.ADMIN_ROLE)))
                return "admin/home";
            else if (role.contains(String.valueOf(UserRole.USER_ROLE)))
                return "user/home";
            else
                return "redirect:/index";
        }
    }

    @GetMapping("/info")
    public String infoView(UserEntity userEntity) {
        return "info";
    }

    @PostMapping("/registerInfo")
    public String registerInfo( UserEntity userEntity,@Valid UserInfoEntity userInfoEntity,
                                BindingResult result, RedirectAttributes redirectAttributes) {

//        newUserService.registerInfo(authentication, userInfoEntity);
//
//        if (result.hasErrors())
//            return "redirect:register";

        return "redirect:/home";
    }
}
