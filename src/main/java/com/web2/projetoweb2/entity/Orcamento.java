package com.web2.projetoweb2.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Orcamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double valor;

    @ManyToOne
    @JoinColumn(name = "idFuncionario")
    private Usuario funcionario;

    @ManyToOne
    @JoinColumn(name = "idSolicitacao")
    private Solicitacao solicitacao;

    private Boolean aprovado;
    private String motivoRejeicao;
    private LocalDateTime dataHoraCriacao;
}
