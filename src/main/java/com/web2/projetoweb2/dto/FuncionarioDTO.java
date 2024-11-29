package com.web2.projetoweb2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FuncionarioDTO {
    private Long id;
    private String email;
    private String nome;
    private String dataNascimento;
    private String senha;
}
