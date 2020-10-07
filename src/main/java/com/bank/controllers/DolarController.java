package com.bank.controllers;

import com.bank.entities.UserAccountEntity;
import com.bank.entities.UserEntity;
import com.bank.models.AccountType;
import com.bank.services.DolarService;
import com.bank.services.NewUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DolarController {

    private DolarService dolarService;

    @Autowired
    public DolarController(DolarService dolarService) {
        this.dolarService = dolarService;
    }


    @GetMapping("/dolar")
    public String dolarView(Model model, Authentication authentication) {

        Double cajaPeso = dolarService.getAmount(authentication, AccountType.CAJA_AHORRO_PESOS);
        Double cajaDolar = dolarService.getAmount(authentication, AccountType.CAJA_AHORRO_DOLARES);

        model.addAttribute("cajaPeso", cajaPeso);
        model.addAttribute("cajaDolar", cajaDolar);
        return "user/dolar";
    }

    @PostMapping("/compraDolar")
    public String compraDolar() {

        dolarService.compraDolar();

        return "user/dolar";
    }


}
