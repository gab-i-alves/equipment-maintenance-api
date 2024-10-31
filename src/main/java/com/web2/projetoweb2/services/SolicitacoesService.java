package com.web2.projetoweb2.services;

import com.web2.projetoweb2.entity.Solicitacao;
import com.web2.projetoweb2.repositorys.SolicitacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SolicitacoesService {
    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    public List<Solicitacao> getAllSolicitacoes() {
        return solicitacaoRepository.findAll();
    }

    public Optional<Solicitacao> getSolicitacaoById(Integer id) {
        return solicitacaoRepository.findById(id);
    }

    public Solicitacao createSolicitacao(Solicitacao solicitacao) {
        return solicitacaoRepository.save(solicitacao);
    }

    public boolean deleteSolicitacao(Integer id) {
        return solicitacaoRepository.findById(id).map(solicitacao -> {
            solicitacaoRepository.delete(solicitacao);
            return true;
        }).orElse(false);
    }
}
