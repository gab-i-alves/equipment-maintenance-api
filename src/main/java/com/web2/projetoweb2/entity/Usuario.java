package com.web2.projetoweb2.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String telefone;
    private Boolean ativo;

    // RF001 - Autocadastro: CPF e email são únicos
    @Column(unique = true)
    private String email;

    // RF001 - Autocadastro: CPF e email são únicos
    @Column(unique = true)
    private String cpf;
    
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
