package com.web2.projetoweb2.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Solicitacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "idEstadoSolicitacao")
    private EstadoSolicitacao estadoSolicitacao;

    @ManyToOne
    @JoinColumn(name = "idCategoriaEquipamento")
    private CategoriaEquipamento categoriaEquipamento;

    private String descricaoEquipamento;
    private String descricaoDefeito;
    private LocalDateTime dataHoraCriacao;
    private String motivoRejeicao;

}
