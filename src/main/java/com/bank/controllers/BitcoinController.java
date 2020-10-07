package com.bank.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BitcoinController {

    @GetMapping("/bitcoin")
    public String bitcoinView() {
        return "user/bitcoin";
    }
}
