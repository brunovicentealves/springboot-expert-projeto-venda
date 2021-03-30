package io.github.brunovicentealves.service;

import io.github.brunovicentealves.model.domain.entity.Cliente;
import io.github.brunovicentealves.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private ClienteRepository  clienteRepository;
    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente salvar(Cliente cliente){

        return  clienteRepository.save(cliente);
    }



}
