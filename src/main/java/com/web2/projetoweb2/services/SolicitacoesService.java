package com.web2.projetoweb2.services;

import com.web2.projetoweb2.entity.CategoriaEquipamento;
import com.web2.projetoweb2.entity.EstadoSolicitacao;
import com.web2.projetoweb2.entity.Solicitacao;
import com.web2.projetoweb2.entity.Usuario;
import com.web2.projetoweb2.repositorys.CategoriaEquipamentoRepository;
import com.web2.projetoweb2.repositorys.EstadoSolicitacaoRepository;
import com.web2.projetoweb2.repositorys.SolicitacaoRepository;
import com.web2.projetoweb2.repositorys.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SolicitacoesService {
    private final SolicitacaoRepository solicitacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EstadoSolicitacaoRepository estadoSolicitacaoRepository;
    private final CategoriaEquipamentoRepository categoriaEquipamentoRepository;

    public SolicitacoesService(SolicitacaoRepository solicitacaoRepository, UsuarioRepository usuarioRepository, EstadoSolicitacaoRepository estadoSolicitacaoRepository, CategoriaEquipamentoRepository categoriaEquipamentoRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.estadoSolicitacaoRepository = estadoSolicitacaoRepository;
        this.categoriaEquipamentoRepository = categoriaEquipamentoRepository;
    }

    public List<Solicitacao> getAllSolicitacoes() {
        return solicitacaoRepository.findAll();
    }

    public Optional<Solicitacao> getSolicitacaoById(Integer id) {
        return solicitacaoRepository.findById(id);
    }

    public List<Solicitacao> getSolicitacaoByEstado(EstadoSolicitacao estadoSolicitacao) {
        return solicitacaoRepository.findByEstadoSolicitacao(estadoSolicitacao);
    }

    public List<Solicitacao> getSolicitacoesByClienteId(Integer idCliente) {
        return solicitacaoRepository.findByClienteId(idCliente);
    }

    public Solicitacao createSolicitacao(Solicitacao solicitacao) {
        Usuario cliente = usuarioRepository.findById(solicitacao.getCliente().getId());
        Optional<EstadoSolicitacao> estadoSolicitacao = estadoSolicitacaoRepository.findById(solicitacao.getEstadoSolicitacao().getId());
        Optional<CategoriaEquipamento> categoria = categoriaEquipamentoRepository.findById(solicitacao.getCategoriaEquipamento().getId());

        if (cliente != null) {
            solicitacao.setCliente(cliente);
        }
        estadoSolicitacao.ifPresent(solicitacao::setEstadoSolicitacao);
        categoria.ifPresent(solicitacao::setCategoriaEquipamento);

        return solicitacaoRepository.save(solicitacao);
    }

    public Solicitacao atualizarSolicitacao(Integer id, Solicitacao solicitacaoAtualizada) {
        return solicitacaoRepository.findById(id).map(solicitacao -> {
            solicitacao.setEstadoSolicitacao(solicitacaoAtualizada.getEstadoSolicitacao());
            solicitacao.setCliente(solicitacaoAtualizada.getCliente());
            solicitacao.setCategoriaEquipamento(solicitacaoAtualizada.getCategoriaEquipamento());
            solicitacao.setDescricaoEquipamento(solicitacaoAtualizada.getDescricaoEquipamento());
            solicitacao.setDescricaoDefeito(solicitacaoAtualizada.getDescricaoDefeito());
            solicitacao.setMotivoRejeicao(solicitacaoAtualizada.getMotivoRejeicao());
            return solicitacaoRepository.save(solicitacao);
        }).orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));
    }

    public boolean deleteSolicitacao(Integer id) {
        return solicitacaoRepository.findById(id).map(solicitacao -> {
            solicitacaoRepository.delete(solicitacao);
            return true;
        }).orElse(false);
    }
}
