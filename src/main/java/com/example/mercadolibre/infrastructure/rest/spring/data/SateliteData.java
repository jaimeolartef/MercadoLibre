package com.example.mercadolibre.infrastructure.rest.spring.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SateliteData {

    private String name;
    private Double distance;
    private String[] message;
}
