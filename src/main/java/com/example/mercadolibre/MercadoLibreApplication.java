package com.example.mercadolibre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class MercadoLibreApplication {

    public static void main(String[] args) {
        SpringApplication.run(MercadoLibreApplication.class, args);
    }

}
