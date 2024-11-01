package com.web2.projetoweb2.services;

import com.web2.projetoweb2.entity.CategoriaEquipamento;
import com.web2.projetoweb2.repositorys.CategoriaEquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaEquipamentoService {

    @Autowired
    private CategoriaEquipamentoRepository categoriaEquipamentoRepository;

    public List<CategoriaEquipamento> listarCategorias() {
        return categoriaEquipamentoRepository.findAll();
    }

    public CategoriaEquipamento buscarPorId(Integer id) {
        return categoriaEquipamentoRepository.findById(id).orElse(null);
    }

    public CategoriaEquipamento adicionarCategoria(CategoriaEquipamento categoria) {
        if (categoriaEquipamentoRepository.findByDescricao(categoria.getDescricao()).isPresent()) {
            throw new IllegalArgumentException("Categoria com a mesma descrição já existe.");
        }

        return categoriaEquipamentoRepository.save(categoria);
    }

    public CategoriaEquipamento atualizarCategoria(Integer id, CategoriaEquipamento categoriaAtualizada) {
        return categoriaEquipamentoRepository.findById(id)
                .map(categoria -> {
                    categoria.setDescricao(categoriaAtualizada.getDescricao());
                    return categoriaEquipamentoRepository.save(categoria);
                }).orElse(null);
    }

    public boolean deletarCategoria(Integer id) {
        if (categoriaEquipamentoRepository.existsById(id)) {
            categoriaEquipamentoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
