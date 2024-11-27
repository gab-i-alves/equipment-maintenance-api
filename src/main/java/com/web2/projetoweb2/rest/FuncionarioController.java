package com.web2.projetoweb2.rest;

import com.web2.projetoweb2.dto.FuncionarioDTO;
import com.web2.projetoweb2.entity.Usuario;
import com.web2.projetoweb2.services.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Usuario> criarFuncionario(@RequestBody FuncionarioDTO funcionarioDTO) {
        try {
            Usuario novoFuncionario = funcionarioService.criarFuncionario(funcionarioDTO);
            return new ResponseEntity<>(novoFuncionario, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarFuncionario(
            @PathVariable Long id,
            @RequestBody FuncionarioDTO funcionarioDTO) {
        try {
            Usuario funcionarioAtualizado = funcionarioService.atualizarFuncionario(id, funcionarioDTO);
            return ResponseEntity.ok(funcionarioAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerFuncionario(
            @PathVariable Long id,
            @RequestHeader("idFuncionario") Long funcionarioLogadoId) {
        try {
            funcionarioService.removerFuncionario(id, funcionarioLogadoId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}