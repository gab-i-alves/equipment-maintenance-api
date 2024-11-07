package com.web2.projetoweb2.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private Boolean ativo;
    
    @ToString.Exclude
    private String senha;

    private String salt;

    @ManyToOne
    @JoinColumn(name = "idTipo")
    private TipoPerfil tipoPerfil;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idEndereco")
    private Endereco endereco;

    private LocalDateTime dataCriacao;

}
