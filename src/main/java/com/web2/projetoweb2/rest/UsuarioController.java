package com.web2.projetoweb2.rest;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web2.projetoweb2.entity.Login;
import com.web2.projetoweb2.entity.Usuario;
import com.web2.projetoweb2.services.UsuarioService;

@CrossOrigin
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Integer id) {
        Optional<Usuario> usuario = usuarioService.getUsuarioById(id);
        return usuario.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/autocadastro")
    public ResponseEntity<Usuario> autoCadastro(@RequestBody Usuario usuario) throws NoSuchAlgorithmException {
        Usuario novoUsuario = usuarioService.autoCadastro(usuario);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }
    
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        Usuario createdUsuario = usuarioService.createUsuario(usuario);
        return new ResponseEntity<>(createdUsuario, HttpStatus.CREATED);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetails) {
        Optional<Usuario> updatedUsuario = usuarioService.updateUsuario(id, usuarioDetails);

        if (updatedUsuario.isPresent()) {
            return new ResponseEntity<>(updatedUsuario.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        boolean deleted = usuarioService.deleteUsuario(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        Optional<Usuario> usuarioOpt = usuarioService.login(login.email, login.senha);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("E-mail ou senha incorretos.");
        }
    }
}