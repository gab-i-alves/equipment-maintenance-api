package com.web2.projetoweb2.dto;
import com.web2.projetoweb2.entity.Orcamento;
import com.web2.projetoweb2.entity.Solicitacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrcamentoDTO {
    private Integer idOrcamento;
    private Double valorOrcamento;
    private boolean aprovado;
    private boolean rejeitado;
    private String motivoRejeicao;
    private LocalDateTime dataHoraCriacao;
    private LocalDateTime dataHoraAprovacao;
    private LocalDateTime dataHoraRejeicao;
    private Long idFuncionario;
    private String nomeFuncionario;
    private Solicitacao solicitacao;


    public OrcamentoDTO(Orcamento orcamento) {
        this.idOrcamento = orcamento.getId();
        this.aprovado = orcamento.getAprovado() != null ? orcamento.getAprovado() : false;
        this.valorOrcamento = orcamento.getValor();
        this.rejeitado = orcamento.getRejeitado() != null ? orcamento.getRejeitado() : false;
        this.motivoRejeicao = orcamento.getMotivoRejeicao();
        this.dataHoraCriacao = orcamento.getDataHoraCriacao();
        this.dataHoraAprovacao = orcamento.getDataHoraAprovacao();
        this.dataHoraRejeicao = orcamento.getDataHoraRejeicao();
        this.idFuncionario = orcamento.getFuncionario() != null ? orcamento.getFuncionario().getId() : 0;
        this.nomeFuncionario = orcamento.getFuncionario() != null ? orcamento.getFuncionario().getNome() : "";
        this.solicitacao = orcamento.getSolicitacao();
    }
}
