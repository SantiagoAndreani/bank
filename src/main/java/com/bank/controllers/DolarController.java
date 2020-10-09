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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class DolarController {

    private DolarService dolarService;
    private Double cajaPeso;
    private Double cajaDolar;

    @Autowired
    public DolarController(DolarService dolarService) {
        this.dolarService = dolarService;
    }


    @GetMapping(value = "/dolar")
    public String dolarView(Model model, Authentication authentication) {

        cajaPeso = dolarService.getAmount(authentication, AccountType.CAJA_AHORRO_PESOS);
        cajaDolar = dolarService.getAmount(authentication, AccountType.CAJA_AHORRO_DOLARES);

        model.addAttribute("cajaPeso", cajaPeso);
        model.addAttribute("cajaDolar", cajaDolar);
        model.addAttribute("dolarForm", new JsonDolar());
        return "user/dolar";
    }

    @PostMapping("/compraDolar")
    public String compraDolar(@ModelAttribute("dolarForm") JsonDolar dolarForm,
                              BindingResult result,
                              RedirectAttributes redirectAttributes, Authentication authentication) {

        if(result.hasErrors())
            return "user/dolar";

        else if(dolarService.insufficientAmount(dolarForm, cajaPeso)) {
            redirectAttributes.addFlashAttribute(
                    "insufficientAmount", "No tiene el monto suficiente para la compra");
            return "redirect:/dolar";
        }
        else {
            dolarService.compraDolar(dolarForm, authentication);
            redirectAttributes.addFlashAttribute("exito", "Transferencia Exitosa !");
            return "redirect:/dolar";
        }
    }


}
