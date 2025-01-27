package com.ecolink.spring.loaders;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.ecolink.spring.entity.Startup;
import com.ecolink.spring.service.OdsService;
import com.ecolink.spring.service.StartupService;

@Component
@Order(2)
public class StartupDataLoader implements CommandLineRunner {

    @Autowired
    private StartupService service;

    @Autowired
    private OdsService odsService;

    @Override
    public void run(String... args) throws Exception {
        List<Startup> startups = Arrays.asList(
                new Startup("VhAT", Arrays.asList(
                        odsService.findByName("Industria, innovación e infraestructura"),
                        odsService.findByName("Producción y consumo responsables"),
                        odsService.findByName("Alianzas para lograr los objetivos")), "contact@vhat.com"),
                new Startup("GamingBuddy", Arrays.asList(
                        odsService.findByName("Educación de calidad"),
                        odsService.findByName("Reducción de las desigualdades"),
                        odsService.findByName("Trabajo decente y crecimiento económico")), "info@gamingbuddy.com"),
                new Startup("Nørs", Arrays.asList(
                        odsService.findByName("Salud y bienestar"),
                        odsService.findByName("Igualdad de género"),
                        odsService.findByName("Reducción de las desigualdades")), "hello@nors.com"),
                new Startup("AndLight", Arrays.asList(
                        odsService.findByName("Producción y consumo responsables"),
                        odsService.findByName("Industria, innovación e infraestructura"),
                        odsService.findByName("Acción por el clima")), "contact@andlight.com"),
                new Startup("Influencer Marketing Hub", Arrays.asList(
                        odsService.findByName("Trabajo decente y crecimiento económico"),
                        odsService.findByName("Reducción de las desigualdades"),
                        odsService.findByName("Alianzas para lograr los objetivos")), "info@imhub.com"),
                new Startup("Too Good To Go", Arrays.asList(
                        odsService.findByName("Hambre cero"),
                        odsService.findByName("Producción y consumo responsables"),
                        odsService.findByName("Acción por el clima")), "contact@toogoodtogo.com"),
                new Startup("Doublepoint", Arrays.asList(
                        odsService.findByName("Industria, innovación e infraestructura"),
                        odsService.findByName("Producción y consumo responsables"),
                        odsService.findByName("Trabajo decente y crecimiento económico")), "info@doublepoint.com"));

        startups.forEach(startup -> {
            if (!service.existsByName(startup.getName())) {
                service.save(startup);
            }
        });
    }

}
