package com.app.aluguel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class AluguelApplication {
    public static void main(String[] args) {
        SpringApplication.run(AluguelApplication.class, args);
    }

    // Endpoints REST de diagnóstico
    @RestController
    static class ApiController {
        @GetMapping("/ping")
        public String ping() { return "pong"; }
        @GetMapping("/api/hello")
        public String hello() { return "Servidor Spring Boot conectado ao PostgreSQL!"; }
        @GetMapping("/ui/diag")
        public String uiDiag() { return "ui-ok"; }
    }

    // Redireciona a raiz para /ui/diag (garante rota acessível)
    @Controller
    static class RootController {
        @GetMapping("/")
        public String root() { return "redirect:/ui/diag"; }
    }
}