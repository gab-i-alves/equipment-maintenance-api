package com.web2.projetoweb2.repositorys;

import com.web2.projetoweb2.entity.Solicitacao;
import com.web2.projetoweb2.entity.SolicitacaoHistorico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitacaoHistoricoRepository extends JpaRepository<SolicitacaoHistorico, Long> {
    List<SolicitacaoHistorico> findBySolicitacaoOrderByDataHoraDesc(Solicitacao solicitacao);
    List<SolicitacaoHistorico> findBySolicitacao(Solicitacao solicitacao);
}
