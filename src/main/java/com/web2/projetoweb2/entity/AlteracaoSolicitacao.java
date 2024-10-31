package com.web2.projetoweb2.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class AlteracaoSolicitacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idSolicitacao")
    private Solicitacao solicitacao;

    @ManyToOne
    @JoinColumn(name = "idFuncionario")
    private Usuario funcionario;

    @ManyToOne
    @JoinColumn(name = "deEstado")
    private EstadoSolicitacao deEstado;

    @ManyToOne
    @JoinColumn(name = "paraEstado")
    private EstadoSolicitacao paraEstado;

    private LocalDateTime dataHoraEfetuada;


}
