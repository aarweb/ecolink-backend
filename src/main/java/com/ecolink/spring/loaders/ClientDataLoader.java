package com.ecolink.spring.loaders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ecolink.spring.entity.Client;
import com.ecolink.spring.entity.Ods;
import com.ecolink.spring.service.ClientService;
import com.ecolink.spring.service.OdsService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
@Order(10)
public class ClientDataLoader implements CommandLineRunner {
        @Autowired
        private OdsService odsService;
        @Autowired
        private ClientService service;

        private final PasswordEncoder passwordEncoder;

        @Transactional
        @Override
        public void run(String... args) throws Exception {
                String defaultPassword = "password";
                List<Client> clients = Arrays.asList(
                                new Client("Alice Johnson", "alice@example.com"),
                                new Client("Bob Smith", "bob@example.com"),
                                new Client("Charlie Brown", "charlie@example.com"),
                                new Client("Diana Prince", "diana@example.com"),
                                new Client("Ethan Hunt", "ethan@example.com"),
                                new Client("Fiona Gallagher", "fiona@example.com"),
                                new Client("George Miller", "george@example.com"),
                                new Client("Hannah Baker", "hannah@example.com"));
                clients.forEach(client -> {
                        if (!service.existByEmail(client.getEmail())) {
                                List<Ods> odsList = new ArrayList<>();

                                switch (client.getName()) {
                                        case "Alice Johnson":
                                            odsList = Arrays.asList(
                                                odsService.findByName("Affordable and Clean Energy"),
                                                odsService.findByName("Clean Water and Sanitation"));
                                            break;
                                        case "Bob Smith":
                                            odsList = Arrays.asList(
                                                odsService.findByName("Affordable and Clean Energy"),
                                                odsService.findByName("Climate Action"));
                                            break;
                                        case "Charlie Brown":
                                            odsList = Arrays.asList(
                                                odsService.findByName("Industry, Innovation, and Infrastructure"),
                                                odsService.findByName("Decent Work and Economic Growth"));
                                            break;
                                        case "Diana Prince":
                                            odsList = Arrays.asList(
                                                odsService.findByName("Gender Equality"),
                                                odsService.findByName("Quality Education"));
                                            break;
                                        case "Ethan Hunt":
                                            odsList = Arrays.asList(
                                                odsService.findByName("Industry, Innovation, and Infrastructure"),
                                                odsService.findByName("Peace, Justice, and Strong Institutions"));
                                            break;
                                        case "Fiona Gallagher":
                                            odsList = Arrays.asList(
                                                odsService.findByName("Sustainable Cities and Communities"),
                                                odsService.findByName("Zero Hunger"));
                                            break;
                                        case "George Miller":
                                            odsList = Arrays.asList(
                                                odsService.findByName("Good Health and Well-being"),
                                                odsService.findByName("Clean Water and Sanitation"));
                                            break;
                                        case "Hannah Baker":
                                            odsList = Arrays.asList(
                                                odsService.findByName("Responsible Consumption and Production"),
                                                odsService.findByName("Life on Land"));
                                            break;
                                    }
                                odsList.forEach(ods -> {
                                        ods.addPreference(client);
                                });
                                client.setPreferences(odsList);
                                client.setPassword(passwordEncoder.encode(defaultPassword));
                                client.setVerified(true);
                                service.save(client);
                        }
                });

        }

}
