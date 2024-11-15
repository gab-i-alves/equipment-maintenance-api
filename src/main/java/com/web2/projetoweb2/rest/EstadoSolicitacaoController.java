package com.web2.projetoweb2.rest;

import com.web2.projetoweb2.entity.EstadoSolicitacao;
import com.web2.projetoweb2.services.EstadoSolicitacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin
@RestController
@RequestMapping("/api/estados-solicitacao")
public class EstadoSolicitacaoController {

    @Autowired
    private EstadoSolicitacaoService estadoSolicitacaoService;

    @PostMapping
    public ResponseEntity<?> adicionarEstado(@RequestBody EstadoSolicitacao estadoSolicitacao) {
        try {
            EstadoSolicitacao novoEstado = estadoSolicitacaoService.adicionarEstado(estadoSolicitacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoEstado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Estado de solicitação com a mesma descrição já existe.");
        }
    }

    @GetMapping
    public ResponseEntity<List<EstadoSolicitacao>> listarEstados() {
        List<EstadoSolicitacao> estados = estadoSolicitacaoService.listarEstados();
        return ResponseEntity.ok(estados);
    }

    @GetMapping("/{id}")
    public Optional<ResponseEntity<EstadoSolicitacao>> buscarPorId(@PathVariable Integer id) {
        return estadoSolicitacaoService.buscarPorId(id)
                .map(ResponseEntity::ok);
    }
}
