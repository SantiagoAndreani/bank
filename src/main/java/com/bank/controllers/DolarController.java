package com.bank.controllers;

import com.bank.models.AccountType;
import com.bank.models.api.dolar.JsonDolar;
import com.bank.services.DolarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class DolarController {

    private DolarService dolarService;

    @Autowired
    public DolarController(DolarService dolarService) {
        this.dolarService = dolarService;
    }


    @GetMapping(value = "/dolar")
    public String dolarView(Model model, Authentication authentication) {

        Double cajaPeso = dolarService.getAmount(authentication, AccountType.CAJA_AHORRO_PESOS);
        Double cajaDolar = dolarService.getAmount(authentication, AccountType.CAJA_AHORRO_DOLARES);

        model.addAttribute("cajaPeso", cajaPeso);
        model.addAttribute("cajaDolar", cajaDolar);
        model.addAttribute("aComprar", new JsonDolar());
        return "user/dolar";
    }

    @PostMapping("/compraDolar")
    public String compraDolar(@ModelAttribute("aComprar") JsonDolar dolar, BindingResult result) {

        if(result.hasErrors())
            return "dolar";

        dolarService.compraDolar(dolar);

        return "user/dolar";
    }


}
