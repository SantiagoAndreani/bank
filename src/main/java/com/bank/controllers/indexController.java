package com.bank.controllers;

import com.bank.entities.UserEntity;
import com.bank.repositories.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@NoArgsConstructor
public class indexController {

    private UserRepository userRepository;

    @Autowired
    public indexController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping({"/", "/index"})
    public String indexView() {
        return "index";
    }

    @PostMapping("/loginUser")
    public String loginUser(@Valid UserEntity userEntity,
                            BindingResult result,
                            Model model) {

        if (result.hasErrors()) {
            return "index";
        }

        Optional<UserEntity> userEntityOptional = userRepository.findByDni(userEntity.getDni());

        if (userEntityOptional.isPresent())
            return "home";
        else
            return "index";

    }

}
