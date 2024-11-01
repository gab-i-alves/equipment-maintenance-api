package com.web2.projetoweb2.repositorys;

import com.web2.projetoweb2.entity.CategoriaEquipamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaEquipamentoRepository extends JpaRepository<CategoriaEquipamento, Integer> {
    Optional<CategoriaEquipamento> findByDescricao(String descricao);
}
