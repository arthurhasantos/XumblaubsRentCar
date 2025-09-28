package com.xumblaubs.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(0)
public class TestDataInitializer implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 TestDataInitializer executado!");
        System.out.println("📋 Argumentos recebidos: " + java.util.Arrays.toString(args));
        System.out.println("⏰ Timestamp: " + java.time.LocalDateTime.now());
    }
}
