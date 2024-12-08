package com.web2.projetoweb2.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class SolicitacaoHistorico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "solicitacao_id")
    private Solicitacao solicitacao;

    private String descricao;

    private LocalDateTime dataHora;

    private String estadoAntigo;
    private String estadoNovo;
    private LocalDateTime dataHoraMudanca;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Usuario funcionario;
}