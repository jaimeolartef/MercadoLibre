package com.example.mercadolibre.infrastructure.api.rest;

import com.example.mercadolibre.domain.model.ResponseSatelite;
import com.example.mercadolibre.infrastructure.rest.spring.data.InformationData;
import com.example.mercadolibre.infrastructure.rest.spring.data.SateliteData;
import com.example.mercadolibre.infrastructure.rest.spring.resources.SateliteController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class SateliteApiRest {

    private final SateliteController sateliteController;

    @PostMapping("topsecret/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseSatelite> decryptMessage(@RequestBody InformationData satellites) {
        try {
            return new ResponseEntity<ResponseSatelite>(sateliteController.decrypt(satellites.getSatellites()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseSatelite(), HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping("topsecret_split/{satellite_name}")
    public ResponseEntity<ResponseSatelite> decryptMessage(@PathVariable(name = "satellite_name") String satelliteName, @RequestBody SateliteData sateliteData) {
        try {
            sateliteData.setName(satelliteName);
            return new ResponseEntity<ResponseSatelite>(sateliteController.decryptSatellite(sateliteData), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseSatelite(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("topsecret_split/")
    public ResponseEntity<ResponseSatelite> decrypt(@RequestBody InformationData satellites) {
        try {
            return new ResponseEntity<ResponseSatelite>(sateliteController.decrypt(satellites.getSatellites()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ResponseSatelite>(new ResponseSatelite(null, "No hay suficiente información."), HttpStatus.NOT_FOUND);
        }
    }
}
