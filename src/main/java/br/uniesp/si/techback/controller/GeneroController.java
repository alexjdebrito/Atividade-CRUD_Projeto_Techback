package br.uniesp.si.techback.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/generos")
@Slf4j
public class GeneroController {

    @PostMapping
    public ResponseEntity<String> criar(@RequestBody String genero) {
        log.info("Recebida requisição para criar novo gênero: {}", genero);
        return ResponseEntity.ok("ENDPOINT CRIAR para inclusão DE GENERO via post");
    }
}

