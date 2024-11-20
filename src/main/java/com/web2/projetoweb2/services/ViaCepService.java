package com.web2.projetoweb2.services;

import com.web2.projetoweb2.entity.Endereco;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepService {

    public Endereco buscarEndereco(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        RestTemplate restTemplate = new RestTemplate();
        Endereco endereco = restTemplate.getForObject(url, Endereco.class);

        if (endereco == null || endereco.getCep() == null) {
            throw new RuntimeException("CEP inválido ou não encontrado.");
        }
        return endereco;
    }
}
