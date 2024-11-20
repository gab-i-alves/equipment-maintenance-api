package com.web2.projetoweb2.repositorys;

import com.web2.projetoweb2.entity.EstadoSolicitacao;
import com.web2.projetoweb2.entity.Solicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Integer> {
    List<Solicitacao> findByClienteId(Integer idCliente);
    List<Solicitacao> findByEstadoSolicitacao(EstadoSolicitacao estadoSolicitacao);
    List<Solicitacao> findByEstadoSolicitacaoDescricao(String descricao);
}
