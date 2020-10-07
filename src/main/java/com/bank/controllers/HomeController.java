package com.bank.controllers;

import com.bank.entities.UserEntity;
import com.bank.entities.UserInfoEntity;
import com.bank.models.UserRole;
import com.bank.services.NewUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String homeView(Authentication authentication, Model model) {

        UserEntity userEntity = newUserService.getUserEntity(authentication);

        if (newUserService.checkInfo(authentication))
            return "redirect:/info";

        else {
            String role = authentication.getAuthorities().toString();

            if (role.contains(String.valueOf(UserRole.ADMIN_ROLE))) {
                model.addAttribute("info", userEntity.getInfo());
                model.addAttribute("account", userEntity.getAccounts());
                return "admin/home";
            }
            else if (role.contains(String.valueOf(UserRole.USER_ROLE))) {
                model.addAttribute("info", userEntity.getInfo());
                model.addAttribute("accounts", userEntity.getAccounts());
                return "user/home";
            }
            else
                return "redirect:/index";
        }
    }

    @GetMapping("/info")
    public String infoView(UserInfoEntity infoEntity) {
        return "info";
    }

    @PostMapping("/registerInfo")
    public String registerInfo(Authentication authentication, @Valid UserInfoEntity infoEntity,
                               BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors())
            return "info";

        if(newUserService.futureAge(infoEntity)) {
            redirectAttributes.addFlashAttribute("futureAge", "No se permiten nacimientos futuros");
            return "redirect:/info";
        }
        if(newUserService.underAge(infoEntity)) {
            redirectAttributes.addFlashAttribute("underAge", "No se permiten menores");
            return "redirect:/info";
        }
        if (newUserService.notUniqueCelPhone(infoEntity)) {
            redirectAttributes.addFlashAttribute("notUniqueCelPhone", "Celular de cliente existente");
            return "redirect:/info";
        }
        else
            newUserService.registerInfo(authentication, infoEntity);
            return "redirect:/home";
    }
}
