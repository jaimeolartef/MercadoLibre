package com.example.mercadolibre.infrastructure.rest.spring.resources;

import com.example.mercadolibre.domain.model.ResponseSatelite;
import com.example.mercadolibre.infrastructure.config.spring.MessageNotFoundException;
import com.example.mercadolibre.infrastructure.rest.spring.data.SateliteData;
import org.springframework.stereotype.Component;

import java.util.List;

public interface SateliteController {

    ResponseSatelite decrypt(List<SateliteData> satelitesData) throws MessageNotFoundException;

    ResponseSatelite decryptSatellite(SateliteData sateliteData) throws MessageNotFoundException;

}
