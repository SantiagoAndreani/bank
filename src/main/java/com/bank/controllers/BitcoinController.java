package com.bank.controllers;

import com.bank.models.AccountType;
import com.bank.models.api.btc.JsonBtc;
import com.bank.models.api.dolar.JsonDolar;
import com.bank.services.BtcService;
import com.bank.services.DolarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BitcoinController {

    private BtcService btcService;
    private DolarService dolarService;
    private Double cajaPeso;
    private Double cajaBtc;

    @Autowired
    public BitcoinController(BtcService btcService, DolarService dolarService) {
        this.btcService = btcService;
        this.dolarService = dolarService;
    }

    @GetMapping("/bitcoin")
    public String bitcoinView(Model model, Authentication authentication) {

        cajaPeso = dolarService.getAmount(authentication, AccountType.CAJA_AHORRO_PESOS);
        cajaBtc = dolarService.getAmount(authentication, AccountType.BILLETERA_BITCOIN);

        model.addAttribute("cajaPeso", cajaPeso);
        model.addAttribute("cajaBtc", cajaBtc);
        model.addAttribute("btcForm", new JsonBtc());

        return "user/bitcoin";
    }

}
