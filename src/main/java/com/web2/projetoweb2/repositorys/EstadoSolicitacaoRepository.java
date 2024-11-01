package com.web2.projetoweb2.repositorys;

import com.web2.projetoweb2.entity.EstadoSolicitacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstadoSolicitacaoRepository extends JpaRepository<EstadoSolicitacao, Integer> {
    Optional<EstadoSolicitacao> findByDescricao(String descricao);
}
