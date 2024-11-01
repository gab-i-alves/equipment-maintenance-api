package com.web2.projetoweb2.services;

import com.web2.projetoweb2.entity.EstadoSolicitacao;
import com.web2.projetoweb2.repositorys.EstadoSolicitacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoSolicitacaoService {

    @Autowired
    private EstadoSolicitacaoRepository estadoSolicitacaoRepository;

    public EstadoSolicitacao adicionarEstado(EstadoSolicitacao estadoSolicitacao) {
        if (estadoSolicitacaoRepository.findByDescricao(estadoSolicitacao.getDescricao()).isPresent()) {
            throw new IllegalArgumentException("Estado de solicitação com a mesma descrição já existe.");
        }
        return estadoSolicitacaoRepository.save(estadoSolicitacao);
    }

    public List<EstadoSolicitacao> listarEstados() {
        return estadoSolicitacaoRepository.findAll();
    }

    public Optional<EstadoSolicitacao> buscarPorId(Integer id) {
        return estadoSolicitacaoRepository.findById(id);
    }
}
