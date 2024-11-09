package com.web2.projetoweb2.repositorys;

import com.web2.projetoweb2.entity.EstadoSolicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EstadoSolicitacaoRepository extends JpaRepository<EstadoSolicitacao, Integer> {
    Optional<EstadoSolicitacao> findByDescricao(String descricao);

    @Query("SELECT s FROM EstadoSolicitacao s WHERE LOWER(s.descricao) = LOWER(:descricao)")
    Optional<EstadoSolicitacao> findByDescricaoIgnoreCase(@Param("descricao") String descricao);

//    Optional<EstadoSolicitacao> findByDescricao_DescricaoIgnoreCase(String descricao);
}
