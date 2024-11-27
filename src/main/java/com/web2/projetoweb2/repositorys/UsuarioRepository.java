package com.web2.projetoweb2.repositorys;

import com.web2.projetoweb2.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import com.web2.projetoweb2.entity.TipoPerfil;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findById(Long id);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByTipoPerfil(TipoPerfil tipoPerfil);

    // Deletar usuário
    void deleteById(Integer id);
    
    // RF001 - Autocadastro: CPF e email são únicos
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}