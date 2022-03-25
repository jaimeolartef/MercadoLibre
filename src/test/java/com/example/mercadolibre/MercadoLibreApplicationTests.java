package com.example.mercadolibre;

import com.example.mercadolibre.domain.model.ResponseSatelite;
import com.example.mercadolibre.infrastructure.config.spring.MessageNotFoundException;
import com.example.mercadolibre.infrastructure.rest.spring.data.InformationData;
import com.example.mercadolibre.infrastructure.rest.spring.data.SateliteData;
import com.example.mercadolibre.infrastructure.rest.spring.resources.impl.SateliteControllerImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class MercadoLibreApplicationTests {

    @Mock
    private SateliteControllerImpl sateliteController;

    @Test
    void decrypt_message() {
        InformationData satellites = new InformationData();
        satellites.setSatellites(new ArrayList<>());

        SateliteData kenobi = new SateliteData();
        kenobi.setName("kenobi");
        kenobi.setDistance(100.0);
        kenobi.setMessage(new String[]{"este", "", "", "mensaje", ""});

        SateliteData skywalker = new SateliteData();
        skywalker.setName("skywalker");
        skywalker.setDistance(115.5);
        skywalker.setMessage(new String[]{"", "es", "", "", "secreto"});

        SateliteData sato = new SateliteData();
        sato.setName("sato");
        sato.setDistance(142.7);
        sato.setMessage(new String[]{"este", "", "un", "", ""});

        satellites.getSatellites().add(kenobi);
        satellites.getSatellites().add(skywalker);
        satellites.getSatellites().add(sato);

        Mockito.doNothing().when(sateliteController).initial();
        Mockito.when(sateliteController.decrypt(satellites.getSatellites())).thenReturn(new ResponseSatelite());
    }

    @Test
    void decrypt_message_not_found() {
        InformationData satellites = new InformationData();
        satellites.setSatellites(new ArrayList<>());

        SateliteData kenobi = new SateliteData();
        kenobi.setName("kenobi");
        kenobi.setDistance(100.0);
        kenobi.setMessage(new String[]{"este", "", "", "mensaje", ""});

        SateliteData skywalker = new SateliteData();
        skywalker.setName("skywalker");
        skywalker.setDistance(null);
        skywalker.setMessage(new String[]{"", "es", "", "", "secreto"});

        SateliteData sato = new SateliteData();
        sato.setName("sato");
        sato.setDistance(null);
        sato.setMessage(null);

        satellites.getSatellites().add(kenobi);
        satellites.getSatellites().add(skywalker);
        satellites.getSatellites().add(sato);

        Mockito.doNothing().when(sateliteController).initial();
        Mockito.when(sateliteController.decrypt(satellites.getSatellites())).thenThrow(new MessageNotFoundException(""));
    }

    @Test
    void decrypt_other_message() {
        InformationData satellites = new InformationData();
        satellites.setSatellites(new ArrayList<>());

        SateliteData kenobi = new SateliteData();
        kenobi.setName("kenobi");
        kenobi.setDistance(80.0);
        kenobi.setMessage(new String[]{"este", "", "", "mensaje", ""});

        SateliteData skywalker = new SateliteData();
        skywalker.setName("skywalker");
        skywalker.setDistance(20.0);
        skywalker.setMessage(new String[]{"", "es", "otro", "", ""});

        SateliteData sato = new SateliteData();
        sato.setName("sato");
        sato.setDistance(10.8);
        sato.setMessage(new String[]{"este", "es", "", "", "secreto"});

        satellites.getSatellites().add(kenobi);
        satellites.getSatellites().add(skywalker);
        satellites.getSatellites().add(sato);

        Mockito.doNothing().when(sateliteController).initial();
        Mockito.when(sateliteController.decrypt(satellites.getSatellites())).thenReturn(new ResponseSatelite());
    }

    @Test
    void decryptSatellite_message() {
        InformationData satellites = new InformationData();

        SateliteData kenobi = new SateliteData();
        kenobi.setName("kenobi");
        kenobi.setDistance(80.0);
        kenobi.setMessage(new String[]{"este", "", "", "mensaje", ""});

        Mockito.doNothing().when(sateliteController).initial();
        Mockito.when(sateliteController.decryptSatellite(kenobi)).thenReturn(new ResponseSatelite());
    }

    @Test
    void decryptSatellite_message_not_found() {
        InformationData satellites = new InformationData();

        SateliteData kenobi = new SateliteData();
        kenobi.setName("kenobi");
        kenobi.setDistance(80.0);
        kenobi.setMessage(null);

        Mockito.doNothing().when(sateliteController).initial();
        Mockito.when(sateliteController.decryptSatellite(kenobi)).thenThrow(new MessageNotFoundException(""));
    }

}
