package com.app.aluguel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiagnosticsController {
    @GetMapping("/ping")
    public String ping() { return "pong"; }

    @GetMapping("/api/hello")
    public String hello() { return "Servidor Spring Boot conectado ao PostgreSQL!"; }

    @GetMapping("/ui/diag")
    public String uiDiag() { return "ui-ok"; }
}
