package com.web2.projetoweb2.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Manutencao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String descricao;
    private String orientacoes;

    @ManyToOne
    @JoinColumn(name = "idFuncionario")
    private Usuario funcionario;

    @ManyToOne
    @JoinColumn(name = "idSolicitacao")
    private Solicitacao solicitacao;

    private LocalDateTime dataHoraCriacao;
}
