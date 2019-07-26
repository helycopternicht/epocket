package com.helycopternicht.epocket.client;

import com.helycopternicht.epocket.client.services.UserEmulatorProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class ClientApplication implements CommandLineRunner {

    @Autowired
    private UserEmulatorProcessor processor;

    @Autowired
    ConfigurableApplicationContext app;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        processor.process();
        System.exit(SpringApplication.exit(app));
    }
}
