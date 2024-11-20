package com.web2.projetoweb2.services;

import com.web2.projetoweb2.entity.CategoriaEquipamento;
import com.web2.projetoweb2.entity.EstadoSolicitacao;
import com.web2.projetoweb2.entity.Solicitacao;
import com.web2.projetoweb2.entity.SolicitacaoHistorico;
import com.web2.projetoweb2.entity.Usuario;
import com.web2.projetoweb2.repositorys.CategoriaEquipamentoRepository;
import com.web2.projetoweb2.repositorys.EstadoSolicitacaoRepository;
import com.web2.projetoweb2.repositorys.SolicitacaoHistoricoRepository;
import com.web2.projetoweb2.repositorys.SolicitacaoRepository;
import com.web2.projetoweb2.repositorys.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Solicitacao atualizarSolicitacao(Integer id, Solicitacao solicitacaoAtualizada, Usuario funcionario) {
        return solicitacaoRepository.findById(id).map(solicitacao -> {
            solicitacao.setEstadoSolicitacao(solicitacaoAtualizada.getEstadoSolicitacao());
            solicitacao.setCliente(solicitacaoAtualizada.getCliente());
            solicitacao.setCategoriaEquipamento(solicitacaoAtualizada.getCategoriaEquipamento());
            solicitacao.setDescricaoEquipamento(solicitacaoAtualizada.getDescricaoEquipamento());
            solicitacao.setDescricaoDefeito(solicitacaoAtualizada.getDescricaoDefeito());
            solicitacao.setMotivoRejeicao(solicitacaoAtualizada.getMotivoRejeicao());
    
            Solicitacao updatedSolicitacao = solicitacaoRepository.save(solicitacao);
    
            addHistorico(updatedSolicitacao, funcionario, "Solicitação atualizada");
    
            return updatedSolicitacao;
        }).orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));
    }

    @Autowired
    private SolicitacaoHistoricoRepository historicoRepository;

    public List<SolicitacaoHistorico> getHistoricoBySolicitacao(Integer solicitacaoId) {
        Solicitacao solicitacao = solicitacaoRepository.findById(solicitacaoId)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));
        return historicoRepository.findBySolicitacao(solicitacao);
    }

    public void addHistorico(Solicitacao solicitacao, Usuario funcionario, String descricao) {
        SolicitacaoHistorico historico = new SolicitacaoHistorico();
        historico.setSolicitacao(solicitacao);
        historico.setFuncionario(funcionario);
        historico.setDescricao(descricao);
        historico.setDataHora(LocalDateTime.now());
        historicoRepository.save(historico);
    }
    
    public boolean deleteSolicitacao(Integer id) {
        return solicitacaoRepository.findById(id).map(solicitacao -> {
            solicitacaoRepository.delete(solicitacao);
            return true;
        }).orElse(false);
    }
  
    public Solicitacao confirmarPagamento(Integer id) {
        return solicitacaoRepository.findById(id).map(solicitacao -> {
            if (!"ARRUMADA".equalsIgnoreCase(solicitacao.getEstadoSolicitacao().getDescricao())) {
                throw new RuntimeException("A solicitação não está no estado 'ARRUMADA'.");
            }
            solicitacao.setEstadoSolicitacao(estadoSolicitacaoRepository.findByDescricao("PAGA")
                .orElseThrow(() -> new RuntimeException("Estado 'PAGA' não encontrado.")));
            solicitacao.setDataHoraPagamento(LocalDateTime.now());
            return solicitacaoRepository.save(solicitacao);
        }).orElseThrow(() -> new RuntimeException("Solicitação não encontrada."));
    }
    
    public Solicitacao efetuarManutencao(Integer idSolicitacao, String descricaoManutencao, String orientacoesCliente, Usuario funcionario) {
        return solicitacaoRepository.findById(idSolicitacao).map(solicitacao -> {
            solicitacao.setDescricaoManutencao(descricaoManutencao);
            solicitacao.setOrientacoesCliente(orientacoesCliente);
            solicitacao.setDataHoraManutencao(LocalDateTime.now());
            solicitacao.setFuncionarioManutencao(funcionario);
    
            // Update the state to "ARRUMADA"
            EstadoSolicitacao estadoArrumada = estadoSolicitacaoRepository.findByDescricao("ARRUMADA")
                    .orElseThrow(() -> new RuntimeException("Estado 'ARRUMADA' não encontrado"));
            solicitacao.setEstadoSolicitacao(estadoArrumada);
    
            return solicitacaoRepository.save(solicitacao);
        }).orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));
    }
}
