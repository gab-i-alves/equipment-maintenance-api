package com.web2.projetoweb2.repositorys;

import com.web2.projetoweb2.entity.EstadoSolicitacao;
import com.web2.projetoweb2.entity.Solicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Integer> {
    List<Solicitacao> findByClienteId(Integer idCliente);

    List<Solicitacao> findByFuncionarioManutencaoId(Integer idFuncionarioManutencao);

    List<Solicitacao> findByEstadoSolicitacao(EstadoSolicitacao estadoSolicitacao);

    @Query("SELECT s FROM Solicitacao s WHERE s.estadoSolicitacao = :estadoSolicitacao AND s.dataHoraPagamento >= :dateInic AND s.dataHoraPagamento <= :dateFin")
    List<Solicitacao> getAllSolicitacoesPagasByRangeDate(EstadoSolicitacao estadoSolicitacao, LocalDateTime dateInic,
            LocalDateTime dateFin);

    List<Solicitacao> findByEstadoSolicitacaoDescricao(String descricao);
}
