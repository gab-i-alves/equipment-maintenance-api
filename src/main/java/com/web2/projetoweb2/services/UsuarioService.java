package com.web2.projetoweb2.services;

import com.web2.projetoweb2.entity.Usuario;
import com.web2.projetoweb2.repositorys.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.security.NoSuchAlgorithmException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    // private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private PasswordHashingService passwordHashingService;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Usuario createUsuario(Usuario usuario) {
        try {
            String salt = passwordHashingService.generateSalt();
            String hashedPassword = passwordHashingService.hashPassword(usuario.getSenha(), salt);
            usuario.setDataCriacao(LocalDateTime.now());
            usuario.setSenha(hashedPassword);
            usuario.setSalt(salt);
            return usuarioRepository.save(usuario);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao fazer o hash", e);
        }
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

    public Optional<Usuario> login(String email, String senha) {
        try {
            Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();
                boolean isPasswordValid = passwordHashingService.verifyPassword(senha, usuario.getSalt(), usuario.getSenha());
                if (isPasswordValid) {
                    return Optional.of(usuario);
                }
            }
            return Optional.empty();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao verificar a senha", e);
        }
    }
}
