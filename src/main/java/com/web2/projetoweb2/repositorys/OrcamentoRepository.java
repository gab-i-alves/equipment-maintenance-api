package com.web2.projetoweb2.repositorys;
import com.web2.projetoweb2.entity.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, Integer> {

    @Query("SELECT o FROM Orcamento o WHERE o.solicitacao.id = :solicitacaoId")
    Orcamento findBySolicitacao(@Param("solicitacaoId") Integer solicitacaoId);
}
