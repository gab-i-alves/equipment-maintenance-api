package com.web2.projetoweb2.services;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web2.projetoweb2.entity.Endereco;
import com.web2.projetoweb2.entity.Usuario;
import com.web2.projetoweb2.repositorys.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordHashingService passwordHashingService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ViaCepService viaCepService;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Usuario autoCadastro(Usuario usuario) throws NoSuchAlgorithmException {
        // RF001 - Autocadastro: CPF e email são únicos
        if (usuarioRepository.existsByCpf(usuario.getCpf())) {
            throw new RuntimeException("CPF já cadastrado!");
        }
        // RF001 - Autocadastro: CPF e email são únicos
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Email já cadastrado!");
        }

        // RF001 - Autocadastro: senha aleatória de 4 números
        String senhaGerada = gerarSenhaAleatoria(); // Aqui são os 4 dígitos
        String salt = passwordHashingService.generateSalt();
        String hashedPassword = passwordHashingService.hashPassword(senhaGerada, salt);

        usuario.setSenha(hashedPassword);
        usuario.setSalt(salt);
        usuario.setAtivo(true);
        usuario.setDataCriacao(LocalDateTime.now());

        // RF001 - Autocadastro: dadosViaCep
        String cep = usuario.getEndereco().getCep();
        Endereco enderecoCompleto = viaCepService.buscarEndereco(cep);
        enderecoCompleto.setNumero(usuario.getEndereco().getNumero());
        enderecoCompleto.setComplemento(usuario.getEndereco().getComplemento());
        usuario.setEndereco(enderecoCompleto);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        // RF001 - Autocadastro: email
        emailService.enviarEmail(
            usuario.getEmail(),
            "Bem-vindo ao Sistema!",
            "Olá, " + usuario.getNome() + "!\n\n" +
            "Seu cadastro foi realizado com sucesso.\n" +
            "Sua senha de acesso é: " + senhaGerada + "\n\n" +
            "Atenciosamente,\nEquipe de Suporte"
        );

        return usuarioSalvo;
    }

    private String gerarSenhaAleatoria() {
        return String.format("%04d", new Random().nextInt(10000)); // Gera 4 dígitos numéricos
    }

    public Usuario createUsuario(Usuario usuario) {
        try { //A senha deverias ser enviada por e-mail para o usuário e não vinda do corpo da requisição 
            String salt = passwordHashingService.generateSalt();
            String hashedPassword = passwordHashingService.hashPassword("SenhaÉEnviadaPorEmail", salt);
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

    @Transactional
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
