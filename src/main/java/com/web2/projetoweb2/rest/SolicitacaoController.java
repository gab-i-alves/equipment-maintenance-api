package com.web2.projetoweb2.rest;

import com.web2.projetoweb2.dto.ResponseRelatorioDTO;
import com.web2.projetoweb2.entity.EstadoSolicitacao;
import com.web2.projetoweb2.entity.Solicitacao;
import com.web2.projetoweb2.entity.SolicitacaoHistorico;
import com.web2.projetoweb2.entity.Usuario;
import com.web2.projetoweb2.repositorys.UsuarioRepository;
import com.web2.projetoweb2.services.EstadoSolicitacaoService;
import com.web2.projetoweb2.services.SolicitacoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/solicitacoes")
public class SolicitacaoController {

    @Autowired
    private SolicitacoesService solicitacaoService;
    @Autowired
    private EstadoSolicitacaoService estadoSolicitacaoService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<Solicitacao>> getAllSolicitacoes() {
        List<Solicitacao> solicitacoes = solicitacaoService.getAllSolicitacoes();
        return new ResponseEntity<>(solicitacoes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitacao> getSolicitacaoById(@PathVariable Integer id) {
        Optional<Solicitacao> solicitacao = solicitacaoService.getSolicitacaoById(id);
        return solicitacao.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Autowired
    private SolicitacoesService solicitacoesService;

    @GetMapping("/{id}/historico")
    public ResponseEntity<?> getSolicitacaoHistorico(@PathVariable Integer id) {
        try {
            List<SolicitacaoHistorico> historico = solicitacoesService.getHistoricoBySolicitacao(id);
            return ResponseEntity.ok(historico);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/manutencao")
    public ResponseEntity<Solicitacao> efetuarManutencao(
            @PathVariable Integer id,
            @RequestBody Map<String, String> requestBody,
            @RequestHeader("idFuncionario") Integer idFuncionario) {
        String descricaoManutencao = requestBody.get("descricaoManutencao");
        String orientacoesCliente = requestBody.get("orientacoesCliente");
        Usuario funcionario = usuarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        Solicitacao solicitacaoAtualizada = solicitacoesService.efetuarManutencao(id, descricaoManutencao,
                orientacoesCliente, funcionario);
        return new ResponseEntity<>(solicitacaoAtualizada, HttpStatus.OK);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> getSolicitacaoByEstadoAbertas(@PathVariable String estado) {
        Optional<EstadoSolicitacao> estadoSolicitacao = estadoSolicitacaoService
                .buscarPorDescricao(estado.toUpperCase());
        if (estadoSolicitacao.isPresent()) {
            List<Solicitacao> solicitacoes = solicitacaoService.getSolicitacaoByEstado(estadoSolicitacao.get());
            return new ResponseEntity<>(solicitacoes, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Este tipo de estado não existe! Verique a escrita e tente novamente!");
        }
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Solicitacao>> getSolicitacoesByClienteId(@PathVariable Integer idCliente) {
        List<Solicitacao> solicitacoes = solicitacaoService.getSolicitacoesByClienteId(idCliente);

        if (solicitacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(solicitacoes);
        }
    }

    @GetMapping("/funcionario/{idFuncionario}")
    public ResponseEntity<List<Solicitacao>> getSolicitacoesByFuncionarioId(@PathVariable Integer idFuncionario) {
        List<Solicitacao> solicitacoes = solicitacaoService.getSolicitacoesPorFuncionarioId(idFuncionario);

        if (solicitacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(solicitacoes);
        }
    }

    @PostMapping
    public ResponseEntity<Solicitacao> createSolicitacao(@RequestBody Solicitacao solicitacao) {
        Solicitacao createdSolicitacao = solicitacaoService.createSolicitacao(solicitacao);
        return new ResponseEntity<>(createdSolicitacao, HttpStatus.CREATED);
    }

    // @PutMapping("/update/{id}")
    // public ResponseEntity<Solicitacao> updateSolicitacao(@PathVariable Integer
    // id, @RequestBody Solicitacao solicitacaoDetails) {
    // Optional<Solicitacao> updatedSolicitacao =
    // solicitacaoService.updateSolicitacao(id, solicitacaoDetails);
    // return updatedSolicitacao.map(value -> new ResponseEntity<>(value,
    // HttpStatus.OK))
    // .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    // }

    @PutMapping("/pagar/{id}")
    public ResponseEntity<?> confirmarPagamento(@PathVariable Integer id) {
        try {
            Solicitacao solicitacaoAtualizada = solicitacaoService.confirmarPagamento(id);
            return new ResponseEntity<>(solicitacaoAtualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSolicitacao(@PathVariable Integer id) {
        boolean deleted = solicitacaoService.deleteSolicitacao(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("relatorios/pagas")
    public ResponseEntity<List<ResponseRelatorioDTO>> getAllSolicitacoesPagasByRangeDate(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateInic,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        List<ResponseRelatorioDTO> solicitacoes = solicitacaoService.getRelatorioSolicitacoes(dateInic, dateFin);
        if (solicitacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(solicitacoes);
        }
    }
}
