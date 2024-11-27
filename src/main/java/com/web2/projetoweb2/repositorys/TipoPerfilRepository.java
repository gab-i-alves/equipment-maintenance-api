package com.web2.projetoweb2.repositorys;

import com.web2.projetoweb2.entity.TipoPerfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoPerfilRepository extends JpaRepository<TipoPerfil, Integer> {
    Optional<TipoPerfil> findByDescricao(String descricao);
}