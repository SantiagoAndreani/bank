package com.bank.models.api.btc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BuySell {

    private String buy;
    private String sell;
}
