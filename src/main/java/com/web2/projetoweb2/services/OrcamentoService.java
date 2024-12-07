package com.web2.projetoweb2.services;

import com.web2.projetoweb2.dto.OrcamentoDTO;
import com.web2.projetoweb2.entity.EstadoSolicitacao;
import com.web2.projetoweb2.entity.Orcamento;
import com.web2.projetoweb2.entity.Solicitacao;
import com.web2.projetoweb2.entity.Usuario;
import com.web2.projetoweb2.repositorys.OrcamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrcamentoService {

    @Autowired
    private OrcamentoRepository orcamentoRepository;

    @Autowired
    private SolicitacoesService solicitacoesService;

    @Autowired
    private EstadoSolicitacaoService estadoSolicitacaoService;

    @Autowired
    private UsuarioService usuarioService;

    public Orcamento criarOrcamento(OrcamentoDTO orcamentoDTO) {
        Orcamento orcamento = new Orcamento();
        orcamento.setValor(orcamentoDTO.getValorOrcamento());
        orcamento.setSolicitacao(orcamentoDTO.getSolicitacao());
        orcamento.setDataHoraCriacao(LocalDateTime.now());

        Optional<Usuario> funcionario = usuarioService.getUsuarioById(Integer.valueOf(String.valueOf(orcamentoDTO.getIdFuncionario())));
        Solicitacao solicitacao = solicitacoesService.getSolicitacaoById(orcamento.getSolicitacao().getId()).orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));
        EstadoSolicitacao estadoOrcada = estadoSolicitacaoService.buscarPorDescricao("ORÇADA").orElseThrow(() -> new RuntimeException("Estado 'ORÇADA' não encontrado"));

        funcionario.ifPresent(orcamento::setFuncionario);
        solicitacao.setEstadoSolicitacao(estadoOrcada);
        solicitacoesService.atualizarSolicitacao(solicitacao.getId(), solicitacao, orcamento.getFuncionario());

        return orcamentoRepository.save(orcamento);
    }

    public List<Orcamento> listarOrcamentos() {
        return orcamentoRepository.findAll();
    }

    public Optional<Orcamento> obterOrcamentoPorId(Integer id) {
        return orcamentoRepository.findById(id);
    }

    public Optional<Orcamento> obterOrcamentoPorSolicatacao(Integer solitacaoId) {
        return Optional.ofNullable(orcamentoRepository.findBySolicitacao(solitacaoId));
    }

    public Orcamento atualizarOrcamento(Integer id, Orcamento orcamentoAtualizado) {
        return orcamentoRepository.findById(id).map(orcamento -> {
            orcamento.setValor(orcamentoAtualizado.getValor());
            orcamento.setMotivoRejeicao(orcamentoAtualizado.getMotivoRejeicao());
            return orcamentoRepository.save(orcamento);
        }).orElseThrow(() -> new RuntimeException("Orçamento não encontrado"));
    }

    public Orcamento aprovarOrcamento(Integer id) {
        return orcamentoRepository.findById(id).map(orcamento -> {
            orcamento.setAprovado(true);
            orcamento.setDataHoraAprovacao(LocalDateTime.now());

            Optional<Solicitacao> solicitacao = solicitacoesService.getSolicitacaoById(orcamento.getSolicitacao().getId());
            Optional<EstadoSolicitacao> estadoSolicitacao = estadoSolicitacaoService.buscarPorDescricao("APROVADA");
            if (solicitacao.isPresent() && estadoSolicitacao.isPresent()) {
                solicitacao.get().setEstadoSolicitacao(estadoSolicitacao.get());
                orcamento.setSolicitacao(solicitacao.get());
            }

            return orcamentoRepository.save(orcamento);
        }).orElseThrow(() -> new RuntimeException("Orçamento não encontrado"));
    }

    public Orcamento rejeitarOrcamento(Integer id, String motivoRejeicao) {
        return orcamentoRepository.findById(id).map(orcamento -> {
            orcamento.setAprovado(false);
            orcamento.setRejeitado(true);
            orcamento.setMotivoRejeicao(motivoRejeicao);
            orcamento.setDataHoraRejeicao(LocalDateTime.now());

            Optional<Solicitacao> solicitacao = solicitacoesService.getSolicitacaoById(orcamento.getSolicitacao().getId());
            Optional<EstadoSolicitacao> estadoSolicitacao = estadoSolicitacaoService.buscarPorDescricao("REJEITADA");
            if (solicitacao.isPresent() && estadoSolicitacao.isPresent()) {
                solicitacao.get().setEstadoSolicitacao(estadoSolicitacao.get());
                orcamento.setSolicitacao(solicitacao.get());
            }
            return orcamentoRepository.save(orcamento);
        }).orElseThrow(() -> new RuntimeException("Orçamento não encontrado"));
    }

    public void excluirOrcamento(Integer id) {
        if (orcamentoRepository.existsById(id)) {
            orcamentoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Orçamento não encontrado");
        }
    }
}
