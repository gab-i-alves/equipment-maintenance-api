package com.web2.projetoweb2.dto;

import com.web2.projetoweb2.entity.Orcamento;
import com.web2.projetoweb2.entity.Solicitacao;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;

@Data
@AllArgsConstructor
public class ResponseRelatorioDTO {
    private Integer idSolicitacao;
    private String estadoSolicitacao;
    private String categoriaEquipamento;
    private String descricaoEquipamento;
    private String descricaoDefeito;
    private LocalDateTime dataHoraCriacaoSolicitacao;
    private LocalDateTime dataHoraPagamento;
    private LocalDateTime dataHoraManutencao;
    private Orcamento orcamentoAprovado;

    public ResponseRelatorioDTO(Solicitacao solicitacao) {
        this.idSolicitacao = solicitacao.getId();
        this.estadoSolicitacao = solicitacao.getEstadoSolicitacao().getDescricao();
        this.categoriaEquipamento = solicitacao.getCategoriaEquipamento().getDescricao();
        this.descricaoEquipamento = solicitacao.getDescricaoEquipamento();
        this.descricaoDefeito = solicitacao.getDescricaoDefeito();
        this.dataHoraCriacaoSolicitacao = solicitacao.getDataHoraCriacao();
        this.dataHoraPagamento = solicitacao.getDataHoraPagamento();
        this.dataHoraManutencao = solicitacao.getDataHoraManutencao() != null ? solicitacao.getDataHoraManutencao() : null;
        this.orcamentoAprovado = solicitacao.getOrcamentos().stream()
                .filter(orcamento -> orcamento.getAprovado() != null && orcamento.getAprovado())
                .filter(orcamento -> orcamento.getDataHoraAprovacao() != null)
                .max(Comparator.comparing(Orcamento::getDataHoraAprovacao))
                .orElse(null);

    }
}
