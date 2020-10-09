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
                              BindingResult result, RedirectAttributes attributes,
                              Authentication authentication) {

        if(result.hasErrors())
            return "user/dolar";

        else if(dolarService.compraInsuficiente(dolarForm, cajaPeso)) {
            attributes.addFlashAttribute(
                    "compraInsuficiente", "No tiene el monto suficiente para la compra");
            return "redirect:/dolar";
        }
        else {
            dolarService.compraDolar(dolarForm, authentication);
            attributes.addFlashAttribute("exitoCompra", "Compra exitosa !");
            return "redirect:/dolar";
        }
    }

    @PostMapping("/ventaDolar")
    public String ventaDolar (@ModelAttribute("dolarForm") JsonDolar dolarForm,
                              BindingResult result, RedirectAttributes attributes,
                              Authentication authentication) {
        if(result.hasErrors())
            return "user/dolar";

        else if (dolarService.ventaInsuficiente(dolarForm, cajaDolar)) {
            attributes.addFlashAttribute(
                    "ventaInsuficiente", "No tiene el monto suficiente para la venta");
            return "redirect:/dolar";
        }
        else {
            dolarService.ventaDolar(dolarForm, authentication);
            attributes.addFlashAttribute("exitoVenta", "Venta exitosa !");
            return  "redirect:/dolar";
        }

    }


}
