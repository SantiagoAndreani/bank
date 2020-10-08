package com.bank.models.api.dolar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class JsonDolar {

    private String compra;
    private String venta;
    private String fecha;

    private double aComprar;
    private double aVender;
}
