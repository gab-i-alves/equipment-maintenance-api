package com.web2.projetoweb2.dto;

import com.web2.projetoweb2.entity.Orcamento;
import com.web2.projetoweb2.entity.Solicitacao;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    private Date dataHoraCriacaoSolicitacao;
    private Date dataHoraPagamento;
    private Date dataHoraManutencao;
    private Double valorOrcamentoAprovado;

    public ResponseRelatorioDTO(Solicitacao solicitacao) {
        this.idSolicitacao = solicitacao.getId();
        this.estadoSolicitacao = solicitacao.getEstadoSolicitacao().getDescricao();
        this.categoriaEquipamento = solicitacao.getCategoriaEquipamento().getDescricao();
        this.descricaoEquipamento = solicitacao.getDescricaoEquipamento();
        this.descricaoDefeito = solicitacao.getDescricaoDefeito();

        this.dataHoraCriacaoSolicitacao = convertToDate(solicitacao.getDataHoraCriacao());
        this.dataHoraPagamento = convertToDate(solicitacao.getDataHoraPagamento());
        this.dataHoraManutencao = convertToDate(solicitacao.getDataHoraManutencao());

        Orcamento orcamentoAprovado = solicitacao.getOrcamentos().stream()
                .filter(orcamento -> orcamento.getAprovado() != null && orcamento.getAprovado())
                .filter(orcamento -> orcamento.getDataHoraAprovacao() != null)
                .max(Comparator.comparing(Orcamento::getDataHoraAprovacao))
                .orElse(null);

        if (orcamentoAprovado != null) {
            this.valorOrcamentoAprovado =  orcamentoAprovado.getValor();
        } else {
            this.valorOrcamentoAprovado = 0.0;
        }
    }

    private Date convertToDate(LocalDateTime localDateTime) {
        if (localDateTime != null) {
            LocalDate localDate = localDateTime.toLocalDate();
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        return null;
    }
}
