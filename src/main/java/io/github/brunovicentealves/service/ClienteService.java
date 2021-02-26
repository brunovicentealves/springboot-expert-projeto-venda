package io.github.brunovicentealves.service;

import io.github.brunovicentealves.model.domain.entity.Cliente;
import io.github.brunovicentealves.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    public ClienteRepository clienteRepository ;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void salvarCliente(Cliente cliente){
            validarCliente(cliente);

            ClienteRepository clienteRespository = new ClienteRepository();
            clienteRespository.persistir(cliente);
    }

    public void validarCliente(Cliente cliente){

        // aplica validações

    }
}
