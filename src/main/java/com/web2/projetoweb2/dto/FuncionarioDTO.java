package com.web2.projetoweb2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class FuncionarioDTO {
    private Long id;
    private String email;
    private String nome;
    private LocalDate dataNascimento;
    private String senha;
}
