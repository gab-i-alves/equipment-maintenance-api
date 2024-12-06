package com.web2.projetoweb2.rest;

import com.web2.projetoweb2.entity.CategoriaEquipamento;
import com.web2.projetoweb2.services.CategoriaEquipamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/categorias-equipamento")
public class CategoriaEquipamentoController {

    @Autowired
    private CategoriaEquipamentoService categoriaEquipamentoService;

    @GetMapping
    public ResponseEntity<List<CategoriaEquipamento>> listarCategorias() {
        List<CategoriaEquipamento> categorias = categoriaEquipamentoService.listarCategorias();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CategoriaEquipamento>> buscarCategoriaPorId(@PathVariable Integer id) {
        return categoriaEquipamentoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> adicionarCategoria(@RequestBody CategoriaEquipamento categoria) {
        try {
            CategoriaEquipamento novaCategoria = categoriaEquipamentoService.adicionarCategoria(categoria);
            return new ResponseEntity<>(novaCategoria, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao criar a categoria: " + e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCategoria(
        @PathVariable Integer id,
        @RequestBody CategoriaEquipamento categoriaAtualizada) {
        try {
            CategoriaEquipamento categoria = categoriaEquipamentoService.atualizarCategoria(id, categoriaAtualizada);
            return ResponseEntity.ok(categoria);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar a categoria: " + e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCategoria(@PathVariable Integer id) {

        try {
            categoriaEquipamentoService.deletarCategoria(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao remover categoria: " + e.getMessage());
        }
    }
}
