package com.web2.projetoweb2.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Redirecionamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idSolicitacao")
    private Solicitacao solicitacao;

    @ManyToOne
    @JoinColumn(name = "idFuncionarioOrigem")
    private Usuario funcionarioOrigem;

    @ManyToOne
    @JoinColumn(name = "idFuncionarioDestino")
    private Usuario funcionarioDestino;

    private LocalDateTime dataHoraCriacao;
}
