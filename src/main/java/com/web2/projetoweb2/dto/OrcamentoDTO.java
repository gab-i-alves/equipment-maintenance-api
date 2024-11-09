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
        this.aprovado = orcamento.getAprovado();
        this.valorOrcamento = orcamento.getValor();
        this.rejeitado = orcamento.getRejeitado();
        this.motivoRejeicao = orcamento.getMotivoRejeicao();
        this.dataHoraCriacao = orcamento.getDataHoraCriacao();
        this.dataHoraAprovacao = orcamento.getDataHoraAprovacao();
        this.dataHoraRejeicao = orcamento.getDataHoraRejeicao();
        this.idFuncionario = orcamento.getFuncionario().getId();
        this.nomeFuncionario = orcamento.getFuncionario().getNome();
        this.solicitacao = orcamento.getSolicitacao();
    }
}
