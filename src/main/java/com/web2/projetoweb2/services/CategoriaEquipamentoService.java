package com.web2.projetoweb2.services;

import com.web2.projetoweb2.entity.CategoriaEquipamento;
import com.web2.projetoweb2.repositorys.CategoriaEquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaEquipamentoService {

    @Autowired
    private CategoriaEquipamentoRepository equipamentoRepository;

    public List<CategoriaEquipamento> listarCategorias() {
        return equipamentoRepository.findAll();
    }

    public Optional<Optional<CategoriaEquipamento>> buscarPorId(Integer id) {
        return Optional.ofNullable(equipamentoRepository.findById(id));
    }

    public CategoriaEquipamento adicionarCategoria(CategoriaEquipamento categoria) {
        if (equipamentoRepository.findByDescricao(categoria.getDescricao()).isPresent()) {
            throw new IllegalArgumentException("Categoria com a mesma descrição já existe.");
        }

        return equipamentoRepository.save(categoria);
    }

    public CategoriaEquipamento atualizarCategoria(Integer id, CategoriaEquipamento categoriaAtualizada) {
        return equipamentoRepository.findById(id)
                .map(categoria -> {
                    categoria.setDescricao(categoriaAtualizada.getDescricao());
                    return equipamentoRepository.save(categoria);
                }).orElse(null);
    }
    
    @Transactional
    public void deletarCategoria(Integer id) {
        if (equipamentoRepository.existsById(id)) {
            equipamentoRepository.deleteById(id);
        }
    }
}
