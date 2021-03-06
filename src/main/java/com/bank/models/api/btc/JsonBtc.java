package com.bank.models.api.btc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class JsonBtc {

    private String USD;
    private BuySell buySell;

    private double aComprar;
    private double aVender;

}
