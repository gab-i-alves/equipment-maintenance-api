package com.web2.projetoweb2.rest;

import com.web2.projetoweb2.entity.CategoriaEquipamento;
import com.web2.projetoweb2.services.CategoriaEquipamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<CategoriaEquipamento> buscarCategoriaPorId(@PathVariable Integer id) {
        CategoriaEquipamento categoria = categoriaEquipamentoService.buscarPorId(id);
        return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> adicionarCategoria(@RequestBody CategoriaEquipamento categoria) {
        try {
            CategoriaEquipamento novaCategoria = categoriaEquipamentoService.adicionarCategoria(categoria);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Categoria com a mesma descrição já existe.");
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<CategoriaEquipamento> atualizarCategoria(@PathVariable Integer id, @RequestBody CategoriaEquipamento categoriaAtualizada) {
        CategoriaEquipamento categoria = categoriaEquipamentoService.atualizarCategoria(id, categoriaAtualizada);
        return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Integer id) {
        boolean deletada = categoriaEquipamentoService.deletarCategoria(id);
        return deletada ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
