package com.web2.projetoweb2.services;

import com.web2.projetoweb2.entity.Usuario;
import com.web2.projetoweb2.repositorys.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Usuario createUsuario(Usuario usuario) {
        usuario.setDataCriacao(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> updateUsuario(Long id, Usuario usuarioAtualizado) {
        return usuarioRepository.findById(Math.toIntExact(id)).map(usuarioExistente -> {
            usuarioExistente.setNome(usuarioAtualizado.getNome());
            usuarioExistente.setEmail(usuarioAtualizado.getEmail());
            usuarioExistente.setSenha(usuarioAtualizado.getSenha());
            usuarioExistente.setAtivo(usuarioAtualizado.getAtivo());
            usuarioExistente.setTipoPerfil(usuarioAtualizado.getTipoPerfil());
            usuarioExistente.setEndereco(usuarioAtualizado.getEndereco());
            return usuarioRepository.save(usuarioExistente);
        });
    }


    public boolean deleteUsuario(Integer id) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuarioRepository.delete(usuario);
            return true;
        }).orElse(false);
    }
}
