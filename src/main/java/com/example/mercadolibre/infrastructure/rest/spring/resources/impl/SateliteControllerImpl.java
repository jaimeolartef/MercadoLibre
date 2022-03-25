package com.example.mercadolibre.infrastructure.rest.spring.resources.impl;

import com.example.mercadolibre.domain.model.Position;
import com.example.mercadolibre.domain.model.ResponseSatelite;
import com.example.mercadolibre.infrastructure.config.spring.MessageNotFoundException;
import com.example.mercadolibre.infrastructure.rest.spring.data.SateliteData;
import com.example.mercadolibre.infrastructure.rest.spring.resources.SateliteController;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class SateliteControllerImpl implements SateliteController {

    private HashMap<String, Position> localicationSalite;
    private List<Double> distances;
    private List<String> phrases;
    private ResponseSatelite responseSatelite;

    @PostConstruct
    public void initial() {
        localicationSalite = new HashMap<>();
        localicationSalite.put("kenobi", new Position(-500.0, -200.0));
        localicationSalite.put("skywalker", new Position(100.0, -100.0));
        localicationSalite.put("sato", new Position(500.0, 100.0));
    }

    @Override
    public ResponseSatelite decrypt(List<SateliteData> satelitesData) throws MessageNotFoundException {
        responseSatelite = new ResponseSatelite();
        distances = new ArrayList<>();
        phrases = new ArrayList<>();

        for (SateliteData rta : satelitesData) {
            if (rta == null || rta.getDistance() == null || rta.getMessage() == null) {
                throw new MessageNotFoundException("No se pudo realizar el desfridado del mensaje");
            }
            distances.add(rta.getDistance());
            phrases.add(getMessage(rta.getMessage()));
        }

        responseSatelite.setPosition(getLocation(distances));
        responseSatelite.setMessage(assembleMessage(phrases));

        return responseSatelite;
    }

    @Override
    public ResponseSatelite decryptSatellite(SateliteData sateliteData) throws MessageNotFoundException {
        responseSatelite = new ResponseSatelite();
        distances = new ArrayList<>();
        phrases = new ArrayList<>();

        distances.add(sateliteData.getDistance());
        phrases.add(sateliteData.getName());

        responseSatelite.setPosition(getLocation(distances));
        responseSatelite.setMessage(assembleMessage(phrases));

        return responseSatelite;
    }

    private Position getLocation(List<Double> distances) throws MessageNotFoundException {

        Position locaticationMessage = Position.builder()
                .x(0.0)
                .y(0.0)
                .build();
        for (Double distance : distances) {
            for (Position item : localicationSalite.values()) {
                locaticationMessage.setX(locaticationMessage.getX() - (item.getX() - distance));
                locaticationMessage.setY(locaticationMessage.getY() - (item.getY() - distance));
            }
        }
        return locaticationMessage;
    }

    private String getMessage(String[] messages) throws MessageNotFoundException {

        String message = StringUtils.EMPTY;
        for (String msg : messages) {
            message = StringUtils.isEmpty(message) ? message.concat(validateString(msg)) : message.concat(",").concat(validateString(msg));
        }

        return message;
    }

    private String assembleMessage(List<String> phrases) throws MessageNotFoundException {
        String assembleMessage = StringUtils.EMPTY;
        List<String> words = new ArrayList<>();

        int countWord = phrases.get(0).split(",").length;
        for (int i = 0; i < countWord; i++) {
            for (int j = 0; j < phrases.size(); j++) {
                String[] list = phrases.get(j).split(",");
                if (StringUtils.isNotBlank(list[i]) && !words.contains(list[i])){
                    words.add(list[i]);
                }
            }
        }

        for (String word : words) {
            assembleMessage = StringUtils.isEmpty(assembleMessage) ?
                    assembleMessage.concat(word) : assembleMessage.concat(" ").concat(word);
        }

        return assembleMessage;
    }

    private String validateString(String message) throws MessageNotFoundException {

        if (StringUtils.isEmpty(message)) {
            return " ";
        } else if (Pattern.matches("[!\"#$%&'()*+, -./:;<=>?@[ ]^_‘{|}~]", message)) {
            throw new MessageNotFoundException("No se pudo realizar el desfridado del mensaje");
        }

        return message;
    }

}
