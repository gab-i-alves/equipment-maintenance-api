package com.web2.projetoweb2.repositorys;

import com.web2.projetoweb2.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findById(Long id);

    Optional<Usuario> findByEmail(String email);
}
