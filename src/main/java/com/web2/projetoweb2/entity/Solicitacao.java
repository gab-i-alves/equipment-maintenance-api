package com.web2.projetoweb2.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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

    private String descricaoManutencao;
    private String orientacoesCliente;
    private LocalDateTime dataHoraManutencao;

    @ManyToOne
    @JoinColumn(name = "idFuncionarioManutencao")
    private Usuario funcionarioManutencao;

    private String descricaoEquipamento;
    private String descricaoDefeito;
    private LocalDateTime dataHoraCriacao;
    private String motivoRejeicao;
    private LocalDateTime dataHoraPagamento;

    @ManyToOne
    @JoinColumn(name = "idFuncionarioFinalizacao")
    private Usuario funcionarioFinalizacao;
    private LocalDateTime dataHoraFinalizacao;


    @JsonManagedReference
    @OneToMany(mappedBy = "solicitacao", fetch = FetchType.LAZY)
    private List<Orcamento> orcamentos;

}
