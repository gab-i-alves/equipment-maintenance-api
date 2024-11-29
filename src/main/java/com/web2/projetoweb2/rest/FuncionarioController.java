package com.web2.projetoweb2.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web2.projetoweb2.dto.FuncionarioDTO;
import com.web2.projetoweb2.entity.Usuario;
import com.web2.projetoweb2.services.FuncionarioService;

@CrossOrigin
@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarFuncionarios() {
        List<Usuario> funcionarios = funcionarioService.listarFuncionarios();
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarFuncionario(@PathVariable Long id) {
        return funcionarioService.buscarFuncionarioPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> criarFuncionario(@RequestBody FuncionarioDTO funcionarioDTO) {
        try {
            Usuario novoFuncionario = funcionarioService.criarFuncionario(funcionarioDTO);
            return new ResponseEntity<>(novoFuncionario, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao criar o funcionário: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarFuncionario(
            @PathVariable Long id,
            @RequestBody FuncionarioDTO funcionarioDTO) {
        try {
            Usuario funcionarioAtualizado = funcionarioService.atualizarFuncionario(id, funcionarioDTO);
            return ResponseEntity.ok(funcionarioAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar o funcionário: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerFuncionario(
            @PathVariable Long id,
            @RequestHeader("idFuncionario") Long funcionarioLogadoId) {
        try {
            funcionarioService.removerFuncionario(id, funcionarioLogadoId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao remover o funcionário: " + e.getMessage());
        }
    }
}