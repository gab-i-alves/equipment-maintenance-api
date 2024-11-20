package com.web2.projetoweb2.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.web2.projetoweb2.entity.Endereco;
import com.web2.projetoweb2.services.ViaCepService;

public class ViaCepController {

    @Autowired
    private ViaCepService viaCepService;

    @GetMapping("/test-viacep/{cep}")
    public ResponseEntity<Endereco> testViaCep(@PathVariable String cep) {
        Endereco endereco = viaCepService.buscarEndereco(cep);
        return ResponseEntity.ok(endereco);
    }

}
